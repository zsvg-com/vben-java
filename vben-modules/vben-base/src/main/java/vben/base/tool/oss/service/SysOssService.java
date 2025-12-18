package vben.base.tool.oss.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vben.base.tool.oss.SysOss;
import vben.base.tool.oss.SysOssExt;
import vben.base.tool.oss.dao.SysOssDao;
import vben.base.tool.oss.vo.SysOssVo;
import vben.common.core.constant.CacheNames;
import vben.common.core.domain.dto.OssDTO;
import vben.common.core.exception.ServiceException;
import vben.common.core.service.OssService;
import vben.common.core.utils.*;
import vben.common.core.utils.file.FileUtils;
import vben.common.json.utils.JsonUtils;
import vben.common.oss.core.OssClient;
import vben.common.oss.entity.UploadResult;
import vben.common.oss.enums.AccessPolicyType;
import vben.common.oss.factory.OssFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SysOssService implements OssService {



    private final SysOssDao dao;


    /**
     * 查询OSS对象存储列表
     *
     * @param bo        OSS对象存储分页查询对象
     * @param pageQuery 分页查询实体类
     * @return 结果
     */
//    @Override
//    public TableDataInfo<SysOssVo> queryPageList(SysOssBo bo, PageQuery pageQuery) {
//        LambdaQueryWrapper<SysOss> lqw = buildQueryWrapper(bo);
//        Page<SysOssVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
//        List<SysOssVo> filterResult = StreamUtils.toList(result.getRecords(), this::matchingUrl);
//        result.setRecords(filterResult);
//        return TableDataInfo.build(result);
//    }

    /**
     * 根据一组 ossIds 获取对应的 SysOssVo 列表
     *
     * @param ossIds 一组文件在数据库中的唯一标识集合
     * @return 包含 SysOssVo 对象的列表
     */

    public List<SysOssVo> listByIds(Collection<Long> ossIds) {
        List<SysOssVo> list = new ArrayList<>();
        SysOssService ossService = SpringUtils.getAopProxy(this);
        for (Long id : ossIds) {
            SysOssVo vo = ossService.getById(id);
            if (ObjectUtils.isNotNull(vo)) {
                try {
                    list.add(this.matchingUrl(vo));
                } catch (Exception ignored) {
                    // 如果oss异常无法连接则将数据直接返回
                    list.add(vo);
                }
            }
        }
        return list;
    }

    /**
     * 根据一组 ossIds 获取对应文件的 URL 列表
     *
     * @param ossIds 以逗号分隔的 ossId 字符串
     * @return 以逗号分隔的文件 URL 字符串
     */
    @Override
    public String selectUrlByIds(String ossIds) {
        List<String> list = new ArrayList<>();
        SysOssService ossService = SpringUtils.getAopProxy(this);
        for (Long id : StrUtils.splitTo(ossIds, Convert::toLong)) {
            SysOssVo vo = ossService.getById(id);
            if (ObjectUtils.isNotNull(vo)) {
                try {
                    list.add(this.matchingUrl(vo).getUrl());
                } catch (Exception ignored) {
                    // 如果oss异常无法连接则将数据直接返回
                    list.add(vo.getUrl());
                }
            }
        }
        return StrUtils.join(",",list);//检查下是否正常，有泛型
    }

    public List<OssDTO> selectByIds(String ossIds) {
        List<OssDTO> list = new ArrayList<>();
        for (Long id : StrUtils.splitTo(ossIds, Convert::toLong)) {
            SysOssVo vo = SpringUtils.getAopProxy(this).getById(id);
            if (ObjectUtils.isNotNull(vo)) {
                try {
                    vo.setUrl(this.matchingUrl(vo).getUrl());
                    list.add(BeanUtil.toBean(vo, OssDTO.class));
                } catch (Exception ignored) {
                    // 如果oss异常无法连接则将数据直接返回
                    list.add(BeanUtil.toBean(vo, OssDTO.class));
                }
            }
        }
        return list;
    }


    //    private LambdaQueryWrapper<SysOss> buildQueryWrapper(SysOssBo bo) {
//        Map<String, Object> params = bo.getParams();
//        LambdaQueryWrapper<SysOss> lqw = Wrappers.lambdaQuery();
//        lqw.like(StrUtils.isNotBlank(bo.getFileName()), SysOss::getFileName, bo.getFileName());
//        lqw.like(StrUtils.isNotBlank(bo.getOriginalName()), SysOss::getOriginalName, bo.getOriginalName());
//        lqw.eq(StrUtils.isNotBlank(bo.getFileSuffix()), SysOss::getFileSuffix, bo.getFileSuffix());
//        lqw.eq(StrUtils.isNotBlank(bo.getUrl()), SysOss::getUrl, bo.getUrl());
//        lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
//            SysOss::getCreateTime, params.get("beginCreateTime"), params.get("endCreateTime"));
//        lqw.eq(ObjectUtils.isNotNull(bo.getCreateBy()), SysOss::getCreateBy, bo.getCreateBy());
//        lqw.eq(StrUtils.isNotBlank(bo.getService()), SysOss::getService, bo.getService());
//        lqw.orderByAsc(SysOss::getOssId);
//        return lqw;
//        }

    /**
     * 根据 ossId 从缓存或数据库中获取 SysOssVo 对象
     *
     * @param ossId 文件在数据库中的唯一标识
     * @return SysOssVo 对象，包含文件信息
     */
    @Cacheable(cacheNames = CacheNames.SYS_OSS, key = "#ossId")
    public SysOssVo getById(Long ossId) {
        SysOssVo vo = MapstructUtils.convert(dao.findById(ossId), SysOssVo.class);
        return vo;
    }

    /**
     * 文件下载方法，支持一次性下载完整文件
     *
     * @param ossId    OSS对象ID
     * @param response HttpServletResponse对象，用于设置响应头和向客户端发送文件内容
     */
    public void download(Long ossId, HttpServletResponse response) throws IOException {
        SysOssVo sysOss = SpringUtils.getAopProxy(this).getById(ossId);
        if (ObjectUtils.isNull(sysOss)) {
            throw new ServiceException("文件数据不存在!");
        }
        FileUtils.setAttachmentResponseHeader(response, sysOss.getOriginalName());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");
        OssClient storage = OssFactory.instance(sysOss.getService());
        storage.download(sysOss.getFileName(), response.getOutputStream(), response::setContentLengthLong);
    }

    /**
     * 上传 MultipartFile 到对象存储服务，并保存文件信息到数据库
     *
     * @param file 要上传的 MultipartFile 对象
     * @return 上传成功后的 SysOssVo 对象，包含文件信息
     * @throws ServiceException 如果上传过程中发生异常，则抛出 ServiceException 异常
     */
    public SysOssVo upload(MultipartFile file) {
        String originalfileName = file.getOriginalFilename();
        String suffix = StrUtils.sub(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
        SysOssExt ext1 = new SysOssExt();
        ext1.setFileSize(file.getSize());
        ext1.setContentType(file.getContentType());
        // 保存文件信息
        return buildResultEntity(originalfileName, suffix, storage.getConfigKey(), uploadResult, ext1);
    }

    /**
     * 上传文件到对象存储服务，并保存文件信息到数据库
     *
     * @param file 要上传的文件对象
     * @return 上传成功后的 SysOssVo 对象，包含文件信息
     */
    public SysOssVo upload(File file) {
        String originalfileName = file.getName();
        String suffix = StrUtils.sub(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult = storage.uploadSuffix(file, suffix);
        SysOssExt ext1 = new SysOssExt();
        ext1.setFileSize(file.length());
        // 保存文件信息
        return buildResultEntity(originalfileName, suffix, storage.getConfigKey(), uploadResult, ext1);
    }

//    @NotNull
    private SysOssVo buildResultEntity(String originalfileName, String suffix, String configKey, UploadResult uploadResult, SysOssExt ext1) {
        SysOss oss = new SysOss();
        oss.setOssId(IdUtils.getSnowflakeNextId());
        oss.setUrl(uploadResult.getUrl());
        oss.setFileSuffix(suffix);
        oss.setFileName(uploadResult.getFilename());
        oss.setOriginalName(originalfileName);
        oss.setService(configKey);
        oss.setExt1(JsonUtils.toJsonString(ext1));
        dao.insert(oss);
        SysOssVo sysOssVo = MapstructUtils.convert(oss, SysOssVo.class);
        return this.matchingUrl(sysOssVo);
    }

    /**
     * 删除OSS对象存储
     *
     * @param ids     OSS对象ID串
     * @param isValid 判断是否需要校验
     * @return 结果
     */
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
//        if (isValid) {
//            // 做一些业务上的校验,判断是否需要校验
//        }
//        List<SysOss> list = baseMapper.selectByIds(ids);
//        for (SysOss sysOss : list) {
//            OssClient storage = OssFactory.instance(sysOss.getService());
//            storage.delete(sysOss.getUrl());
//        }
//        return baseMapper.deleteByIds(ids) > 0;
        return true;
    }

    /**
     * 桶类型为 private 的URL 修改为临时URL时长为120s
     *
     * @param oss OSS对象
     * @return oss 匹配Url的OSS对象
     */
    private SysOssVo matchingUrl(SysOssVo oss) {
        OssClient storage = OssFactory.instance(oss.getService());
        // 仅修改桶类型为 private 的URL，临时URL时长为120s
        if (AccessPolicyType.PRIVATE == storage.getAccessPolicy()) {
            oss.setUrl(storage.getPrivateUrl(oss.getFileName(), Duration.ofSeconds(120)));
        }
        return oss;
    }
}
