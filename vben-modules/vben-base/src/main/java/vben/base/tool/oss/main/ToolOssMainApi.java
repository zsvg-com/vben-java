package vben.base.tool.oss.main;

import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vben.base.tool.oss.root.Zfile;
import vben.base.tool.oss.vo.SysOssUploadVo;
import vben.common.core.domain.R;
import vben.common.core.utils.ObjectUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * 文件管理
 */
@RestController
@RequestMapping("tool/oss/main")
@RequiredArgsConstructor
public class ToolOssMainApi {

    private final ToolOssMainService service;

    /**
     * 文件分页查询
     * @param name 文件名称
     * @return 文件分页数据
     */
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("t.name,t.id,t.type,t.crtim", "tool_oss_main");
        sqler.addInnerJoin("f.fsize,f.service,f.path", "tool_oss_file f", "f.id=t.filid");
        sqler.addLeftJoin("u.name cruna", "sys_org_user u", "u.id=t.cruid");
        sqler.addLike("t.name", name);
        sqler.addOrder("t.crtim desc");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 文件下载
     * @param table
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("download")
    public void download(String table, String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        service.downloadFile(request,response,table,id);
    }

    /**
     * 文件展示
     * @param token
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @SaIgnore
    @GetMapping("show")
    public void show(String token, String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        if(StrUtils.isBlank(token)){
//            return;
//        }
//        String uuid = jwtHandler.getClaims(token.substring(7)).getId();
//        Zuser zuser = (Zuser) redisHandler.get("online-key:"+uuid);
//        if(zuser!=null){
//            service.downloadFile(request,response,null,id);
//        }
        service.downloadFile(request,response,null,id);
    }

    /**
     * 上传OSS对象存储
     *
     * @param file 文件
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<SysOssUploadVo> upload(@RequestPart("file") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        if (ObjectUtils.isNull(file)) {
            return R.fail("上传文件不能为空");
        }
        Zfile zfile = service.uploadFile(file);
        SysOssUploadVo uploadVo = new SysOssUploadVo();
//        uploadVo.setUrl(oss.getUrl());
        uploadVo.setFileName(zfile.getName());
        uploadVo.setOssId(zfile.getFilid());
        return R.ok(uploadVo);
    }

//    @PostMapping(value="upload",produces = "text/html;charset=UTF-8")
//    public String upload(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
//        Zfile zfile = service.uploadFile(file);
//        return "{\"id\":\"" + zfile.getId() + "\",\"path\":\"" + zfile.getPath() + "\",\"filid\":\"" + zfile.getFilid() + "\",\"name\":\"" + zfile.getName() + "\",\"size\":\"" +  zfile.getSize() + "\"}";
//    }

    /**
     * 文件删除
     * @param ids
     * @return
     */
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable String[] ids) {
        return R.ok(service.delete(ids));
    }

//    @Autowired
//    private JwtHandler jwtHandler;

}
