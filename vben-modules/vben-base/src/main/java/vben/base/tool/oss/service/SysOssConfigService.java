package vben.base.tool.oss.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.tool.oss.SysOssConfig;
import vben.base.tool.oss.bo.SysOssConfigBo;
import vben.base.tool.oss.dao.SysOssConfigDao;
import vben.base.tool.oss.vo.SysOssConfigVo;
import vben.common.core.constant.CacheNames;
import vben.common.core.utils.MapstructUtils;
import vben.common.json.utils.JsonUtils;
import vben.common.oss.constant.OssConstant;
import vben.common.redis.utils.CacheUtils;
import vben.common.redis.utils.RedisUtils;

import java.util.Collection;
import java.util.List;

/**
 * 对象存储配置Service业务层处理
 *
 * @author Lion Li
 * @author 孤舟烟雨
 * @date 2021-08-13
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysOssConfigService  {

    private final SysOssConfigDao dao;


    /**
     * 项目启动时，初始化参数到缓存，加载配置类
     */
    public void init() {
        List<SysOssConfig> list = dao.findAll();
        // 加载OSS初始化配置
        for (SysOssConfig config : list) {
            String configKey = config.getConfigKey();
            if ("0".equals(config.getStatus())) {
                RedisUtils.setCacheObject(OssConstant.DEFAULT_CONFIG_KEY, configKey);
            }
            CacheUtils.put(CacheNames.SYS_OSS_CONFIG, config.getConfigKey(), JsonUtils.toJsonString(config));
        }
    }

    public SysOssConfigVo queryById(Long ossConfigId) {
        SysOssConfigVo vo = MapstructUtils.convert(dao.findById(ossConfigId), SysOssConfigVo.class);
        return vo;
    }

//    public TableDataInfo<SysOssConfigVo> queryPageList(SysOssConfigBo bo, PageQuery pageQuery) {
//        LambdaQueryWrapper<SysOssConfig> lqw = buildQueryWrapper(bo);
//        Page<SysOssConfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
//        return TableDataInfo.build(result);
//    }
//
//
//    private LambdaQueryWrapper<SysOssConfig> buildQueryWrapper(SysOssConfigBo bo) {
//        LambdaQueryWrapper<SysOssConfig> lqw = Wrappers.lambdaQuery();
//        lqw.eq(StrUtils.isNotBlank(bo.getConfigKey()), SysOssConfig::getConfigKey, bo.getConfigKey());
//        lqw.like(StrUtils.isNotBlank(bo.getBucketName()), SysOssConfig::getBucketName, bo.getBucketName());
//        lqw.eq(StrUtils.isNotBlank(bo.getStatus()), SysOssConfig::getStatus, bo.getStatus());
//        lqw.orderByAsc(SysOssConfig::getOssConfigId);
//        return lqw;
//    }

    public Boolean insertByBo(SysOssConfigBo bo) {
        SysOssConfig config = MapstructUtils.convert(bo, SysOssConfig.class);
//        validEntityBeforeSave(config);
        dao.insert(config);
        CacheUtils.put(CacheNames.SYS_OSS_CONFIG, config.getConfigKey(), JsonUtils.toJsonString(config));
        return true;
    }

    public Boolean updateByBo(SysOssConfigBo bo) {
        SysOssConfig config = MapstructUtils.convert(bo, SysOssConfig.class);
        dao.update(config);
//        validEntityBeforeSave(config);
//        LambdaUpdateWrapper<SysOssConfig> luw = new LambdaUpdateWrapper<>();
//        luw.set(ObjectUtils.isNull(config.getPrefix()), SysOssConfig::getPrefix, "");
//        luw.set(ObjectUtils.isNull(config.getRegion()), SysOssConfig::getRegion, "");
//        luw.set(ObjectUtils.isNull(config.getExt1()), SysOssConfig::getExt1, "");
//        luw.set(ObjectUtils.isNull(config.getRemark()), SysOssConfig::getRemark, "");
//        luw.eq(SysOssConfig::getOssConfigId, config.getOssConfigId());
        CacheUtils.put(CacheNames.SYS_OSS_CONFIG, config.getConfigKey(), JsonUtils.toJsonString(config));
        return true;
    }

    /**
     * 保存前的数据校验
     */
//    private void validEntityBeforeSave(SysOssConfig entity) {
//        if (StrUtils.isNotEmpty(entity.getConfigKey())
//            && !checkConfigKeyUnique(entity)) {
//            throw new ServiceException("操作配置'{}'失败, 配置key已存在!", entity.getConfigKey());
//        }
//    }

//    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {

//        if (isValid) {
//            if (CollUtil.containsAny(ids, OssConstant.SYSTEM_DATA_IDS)) {
//                throw new ServiceException("系统内置, 不可删除!");
//            }
//        }
//        List<SysOssConfig> list = CollUtil.newArrayList();
//        for (Long configId : ids) {
//            SysOssConfig config = baseMapper.selectById(configId);
//            list.add(config);
//        }
//        boolean flag = baseMapper.deleteByIds(ids) > 0;
//        if (flag) {
//            list.forEach(sysOssConfig ->
//                CacheUtils.evict(CacheNames.SYS_OSS_CONFIG, sysOssConfig.getConfigKey()));
//        }
        return true;
    }

    /**
     * 判断configKey是否唯一
     */
    private boolean checkConfigKeyUnique(SysOssConfig sysOssConfig) {
//        long ossConfigId = ObjectUtils.notNull(sysOssConfig.getOssConfigId(), -1L);
//        SysOssConfig info = baseMapper.selectOne(new LambdaQueryWrapper<SysOssConfig>()
//            .select(SysOssConfig::getOssConfigId, SysOssConfig::getConfigKey)
//            .eq(SysOssConfig::getConfigKey, sysOssConfig.getConfigKey()));
//        if (ObjectUtils.isNotNull(info) && info.getOssConfigId() != ossConfigId) {
//            return false;
//        }
        return true;
    }

    /**
     * 启用禁用状态
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateOssConfigStatus(SysOssConfigBo bo) {
//        SysOssConfig sysOssConfig = MapstructUtils.convert(bo, SysOssConfig.class);
//        int row = baseMapper.update(null, new LambdaUpdateWrapper<SysOssConfig>()
//            .set(SysOssConfig::getStatus, "1"));
//        row += baseMapper.updateById(sysOssConfig);
//        if (row > 0) {
//            RedisUtils.setCacheObject(OssConstant.DEFAULT_CONFIG_KEY, sysOssConfig.getConfigKey());
//        }
        return 1;
    }

}
