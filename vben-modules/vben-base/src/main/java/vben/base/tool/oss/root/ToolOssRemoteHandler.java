package vben.base.tool.oss.root;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vben.base.tool.oss.file.ToolOssFile;
import vben.common.core.exception.ServiceException;
import vben.common.core.exception.base.BaseException;
import vben.common.core.utils.StrUtils;
import vben.common.core.utils.file.FileUtils;
import vben.common.core.utils.IdUtils;
import vben.common.oss.core.OssClient;
import vben.common.oss.entity.UploadResult;
import vben.common.oss.factory.OssFactory;

import java.io.IOException;

@Component
public class ToolOssRemoteHandler {

    public ToolOssFile upload(MultipartFile file, String md5) {
        String originalfileName = file.getOriginalFilename();
        String suffix = StrUtils.sub(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
        } catch (IOException e) {
            throw new BaseException(e.getMessage());
        }
        // 保存文件信息
        ToolOssFile ossFile = new ToolOssFile();
        ossFile.setId(IdUtils.getSnowflakeNextIdStr());
        ossFile.setFsize(file.getSize());
        ossFile.setMd5(md5);
        ossFile.setPath(uploadResult.getUrl());
        ossFile.setService(storage.getConfigKey());
        return ossFile;
    }

    public void download(HttpServletRequest request, HttpServletResponse response,
                         String fileName, String path) throws IOException {
//        response.reset();
        FileUtils.setAttachmentResponseHeader(response, fileName);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");
        long data;
        try {
            data = HttpUtil.download(path, response.getOutputStream(), false);
        } catch (HttpException e) {
            if (e.getMessage().contains("403")) {
                throw new ServiceException("无读取权限, 请在对应的OSS开启'公有读'权限!");
            } else {
                throw new ServiceException(e.getMessage());
            }
        }
        response.setContentLength(Convert.toInt(data));
    }

}
