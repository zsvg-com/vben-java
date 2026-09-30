package vben.base.pub.menu;

import lombok.Data;
import vben.common.core.utils.StrUtils;

/**
 * 路由显示信息
 *
 * @author ruoyi
 */

@Data
public class MetaVo {

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    /**
     * 缓存标记
     */
    private Boolean catag;

    /**
     * 内链地址（http(s)://开头）
     */
    private String link;


    public MetaVo() {

    }

    public MetaVo(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public MetaVo(String title, String icon, Boolean catag) {
        this.title = title;
        this.icon = icon;
        this.catag = catag;
    }

    public MetaVo(String title, String icon, String link) {
        this.title = title;
        this.icon = icon;
        this.link = link;
    }

    public MetaVo(String title, String icon, Boolean catag, String link) {
        this.title = title;
        this.icon = icon;
        this.catag = catag;
        if (StrUtils.isUrl(link)) {
            this.link = link;
        }
    }

}
