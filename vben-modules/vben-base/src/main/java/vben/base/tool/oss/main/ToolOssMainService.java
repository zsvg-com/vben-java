package vben.base.tool.oss.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vben.base.tool.oss.file.ToolOssFile;
import vben.base.tool.oss.file.ToolOssFileDao;
import vben.base.tool.oss.root.ToolOssLocalHandler;
import vben.base.tool.oss.root.ToolOssRemoteHandler;
import vben.base.tool.oss.root.Zfile;
import vben.common.core.utils.IdUtils;
import vben.common.core.utils.StrUtils;
import vben.common.core.utils.file.FileUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ToolOssMainService {

    public Zfile uploadFile(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String md5 = getMd5(file);
        ToolOssFile dbFile = fileDao.findByMd5(md5);
        Zfile zfile = new Zfile();
        if (dbFile != null) {//相同文件已上传过
            ToolOssMain main = new ToolOssMain();
            main.setId(IdUtils.getSnowflakeNextIdStr());
            main.setFilid(dbFile.getId());
            main.setName(file.getOriginalFilename());
            main.setCruid(LoginHelper.getUserId());
            if (main.getName().contains(".")) {
                main.setType(main.getName().substring(main.getName().lastIndexOf(".") + 1));
            }
            mainDao.insert(main);
            zfile.setId(main.getId());
            zfile.setName(main.getName());
            zfile.setSize(FileUtils.getFileSize(dbFile.getFsize()));
            zfile.setPath(dbFile.getPath());
            zfile.setFilid(dbFile.getId());
        } else {
            ToolOssFile newFile = localHandler.upload(file,md5);
//            AssOssFile newFile = remoteHandler.upload(file,md5);
            fileDao.insert(newFile);
            ToolOssMain main = new ToolOssMain();
            main.setId(newFile.getId());
            main.setFilid(newFile.getId());
            main.setName(file.getOriginalFilename());
            if (main.getName().contains(".")) {
                main.setType(main.getName().substring(main.getName().lastIndexOf(".") + 1));
            }
            main.setCruid(LoginHelper.getUserId());
            mainDao.insert(main);
            zfile.setId(main.getId());
            zfile.setName(main.getName());
            zfile.setSize(FileUtils.getFileSize(newFile.getFsize()));
            zfile.setPath(newFile.getPath());
            zfile.setFilid(newFile.getId());
        }
        return zfile;
    }


    public void downloadFile(HttpServletRequest request, HttpServletResponse response,
                             String table, String id) throws Exception {
        String sql = "select t.path,t.name,f.service from " + table + " t " +
                "inner join tool_oss_file f on f.id=t.filid " +
                "where t.id=?";
        Map<String, Object> map = null;
        if (StrUtils.isNotBlank(table)) {
            map = jdbcHelper.findMap(sql, id);
        }
        if (map == null) {
            sql = "select f.path,t.name,f.service from tool_oss_main t " +
                    "inner join tool_oss_file f on f.id=t.filid " +
                    "where t.id=?";
            map = jdbcHelper.findMap(sql, id);
        }
        if(map!=null){
            if("local".equals(map.get("service"))){
                localHandler.download(request,response,(String) map.get("name"),(String) map.get("path"));
            }else{
                remoteHandler.download(request, response, (String) map.get("name"), (String) map.get("path"));
            }
        }
    }

    public int delete(String[] ids) {
        for (String id : ids) {
            mainDao.deleteById(id);
        }
        return ids.length;
    }

    public ToolOssMain getInfo(String id){
        return mainDao.findById(id);
    }

    public List<ToolOssMain> getInfos(String ids){
        String[] arr= ids.split(",");
        List<String> list=Arrays.asList(arr);
        return mainDao.findAllById(list);
    }

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    private final JdbcHelper jdbcHelper;

    private final ToolOssLocalHandler localHandler;

    private final ToolOssRemoteHandler remoteHandler;

    private final ToolOssFileDao fileDao;

    private final ToolOssMainDao mainDao;

    private String getMd5(MultipartFile file) {
        try {
            //获取文件的byte信息
            byte[] uploadBytes = file.getBytes();
            // 拿到一个MD5转换器
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            //转换为16进制
            return new BigInteger(1, digest).toString(16);
//            MessageDigest messageDigest = MessageDigest.getInstance("md5");
//            return Base64.getEncoder().encodeToString(messageDigest.digest(file.getBytes()));
        } catch (IOException | NoSuchAlgorithmException e) {
            log.error("get file md5 error!!!", e);
        }
        return null;
    }

}
