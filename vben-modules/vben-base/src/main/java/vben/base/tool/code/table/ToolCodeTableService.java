package vben.base.tool.code.table;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.tool.code.field.ToolCodeField;
import vben.base.tool.code.field.ToolCodeFieldDao;
import vben.base.tool.code.velocity.GenCode;
import vben.base.tool.code.velocity.VelocityInitializer;
import vben.base.tool.code.velocity.VelocityUtils;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
@Slf4j
@RequiredArgsConstructor
public class ToolCodeTableService  {

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    public ToolCodeTable select(Long id) {
        return dao.findById(id);
    }

    public Long insert(ToolCodeTable main) {
        main.setId(IdUtils.getSnowflakeNextId());
        dao.insert(main);
        for (ToolCodeField field : main.getFields()) {
            field.setTabid(main.getId());
            fieldDao.insert(field);
        }
        return main.getId();
    }

    public Long update(ToolCodeTable main) {
        dao.update(main);
        fieldDao.deleteByTabid(main.getId());
        for (ToolCodeField field : main.getFields()) {
            field.setTabid(main.getId());
            fieldDao.insert(field);
        }
        return main.getId();
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            fieldDao.deleteByTabid(id);
            dao.deleteById(id);
        }
        return ids.length;
    }


    public List<GenCode> previewCode(Long id)
    {
        ToolCodeTable table = select(id);
        List<GenCode> genCodes=new ArrayList<>();
//        Map<String, String> dataMap = new LinkedHashMap<>();
        // 查询表信息
        // 设置主子表信息
//        setSubTable(table);
//        // 设置主键列信息
//        setPkColumn(table);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table);
        for (String template : templates)
        {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            GenCode genCode=new GenCode();
            String[] arr=template.split("/");
            genCode.setTitle(arr[arr.length-1].replace(".vm",""));
            genCode.setContent( sw.toString());
            genCodes.add(genCode);
        }
        return genCodes;
    }

    public byte[] downloadCode(Long id)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(id, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 查询表信息并生成代码
     */
    private void generatorCode(Long id, ZipOutputStream zip)
    {
        // 查询表信息
        ToolCodeTable table = select(id);
        // 设置主子表信息
//        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
//        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        List<String> templates = VelocityUtils.getTemplateList(table);
        for (String template : templates)
        {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            try
            {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            }
            catch (IOException e)
            {
                log.error("渲染模板失败，表名：" + table.getName(), e);
            }
        }
    }

    //----------bean注入------------
    private final ToolCodeTableDao dao;

    private final ToolCodeFieldDao fieldDao;

}

