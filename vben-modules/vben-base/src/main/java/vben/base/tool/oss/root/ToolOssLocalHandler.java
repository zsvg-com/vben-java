package vben.base.tool.oss.root;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vben.base.tool.oss.file.ToolOssFile;
import vben.common.core.utils.DateUtils;
import vben.common.core.utils.IdUtils;

import java.io.*;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Locale;

@Component
public class ToolOssLocalHandler {

    @Value("${app.attpath:#{systemProperties['user.dir']}/zsvg/atts}")
    private String ATT_PATH;

    public ToolOssFile upload(MultipartFile file, String md5) throws IOException, NoSuchAlgorithmException {
        ToolOssFile ossFile = new ToolOssFile();
        String fileName = file.getOriginalFilename();
        Long fileSize = file.getSize();
        if (!"".equals(fileName)) {
            String sid = IdUtils.getSnowflakeNextIdStr();
            String newName = sid;
            if(fileName.contains(".")){
                newName+="."+fileName.substring(fileName.lastIndexOf(".")+1);
            }
            String dirX = Integer.toHexString(newName.hashCode() & 0xf);
            String savePath = DateUtils.format(new Date(), "yyyy/MM/dd") + "/" + dirX;
            File targetFolder = new File(ATT_PATH + "/" + savePath);
            File targetFile = new File(ATT_PATH + "/" + savePath, newName);
            if (!targetFolder.exists()) {
                targetFolder.mkdirs();
            }
            file.transferTo(targetFile);
            ossFile.setId(sid);
            ossFile.setFsize(fileSize);
            ossFile.setMd5(md5);
            ossFile.setPath(savePath + "/" + newName);
            ossFile.setService("local");
        }
        return ossFile;
    }

    public void download(HttpServletRequest request, HttpServletResponse response,
                         String fileName, String path) throws Exception
    {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String downLoadPath = ATT_PATH + "/" + path;

        long fileLength = new File(downLoadPath).length();

        response.setContentType("application/octet-stream");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        Locale localLanguage = request.getLocale();
        if (fileName.length() > 150)
        {
            fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
        }
        response.addHeader("Access-Control-Expose-Headers", "Content-disposition,download-filename");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setHeader("Content-Length", String.valueOf(fileLength));
        response.setHeader("download-filename", fileName);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(downLoadPath));
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
        {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }

}
