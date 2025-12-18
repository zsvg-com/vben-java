package vben.base.tool.code.table;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vben.base.tool.code.velocity.GenCode;
import vben.common.core.domain.R;
import vben.common.jdbc.sqler.Sqler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 代码生成
 */
@RestController
@RequestMapping("tool/code/table")
@RequiredArgsConstructor
public class ToolCodeTableApi {

    private String table = "tool_code_table";

    /**
     * 查询代码生成主表分页
     * @param name
     * @return
     */
    @GetMapping
    public R get(String name) {
        Sqler sqler = new Sqler(table);
        sqler.addLike("t.name" , name);
        sqler.addSelect("t.notes,t.ornum,t.crtim");
        sqler.addOrder("t.ornum");
        sqler.addDescOrder("t.crtim");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 下载代码生成结果ZIP
     * @param response
     * @param id
     * @throws IOException
     */
    @GetMapping("/zip")
    public void getZip(HttpServletResponse response, Long id) throws IOException {
        byte[] data = service.downloadCode(id);
        genCode(response, data);
    }

    /**
     * 预览代码生成结果
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/show")
    public R getShow(Long id) throws IOException {
        List<GenCode> genCodes = service.previewCode(id);
        return R.ok(genCodes);
    }

    /**
     * 查询代码生成主表详情
     * @param id
     * @return
     */
    @GetMapping("info/{id}")
    public R getOne(@PathVariable Long id) {
        ToolCodeTable main = service.select(id);
        return R.ok(main);
    }

    /**
     * 新增代码生成主表
     * @param main
     */
    @PostMapping
    public R<Long> post(@RequestBody ToolCodeTable main)  {
        return R.ok(null,service.insert(main));
    }

    /**
     * 修改代码生成主表
     */
    @PutMapping
    public R<Long> put(@RequestBody ToolCodeTable main) {
        return R.ok(null,service.update(main));
    }

    /**
     * 删除代码生成主表
     */
    @DeleteMapping("{ids}")
    public R delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

    @Autowired
    private ToolCodeTableService service;

    //生成zip文件
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        String time= sdf.format(new Date());
        String fileName="vben-"+time+".zip";
        response.addHeader("Access-Control-Allow-Origin" , "*");
        response.addHeader("Access-Control-Expose-Headers" , "Content-Disposition,download-filename");
        response.setHeader("Content-disposition" , "attachment; filename="+fileName);
        response.setHeader("download-filename" , fileName);
        response.addHeader("Content-Length" , String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

}
