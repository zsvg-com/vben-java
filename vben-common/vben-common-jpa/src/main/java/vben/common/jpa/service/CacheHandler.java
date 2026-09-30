package vben.common.jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.redis.utils.RedisUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CacheHandler {

    private final JdbcHelper jdbcHelper;

    public String getAids(String userId) {
        String aids = RedisUtils.getCacheObject("aids:" + userId);
        if(aids == null){
            aids = findAids(userId+"");
            RedisUtils.setCacheObject("aids:" + userId, aids);
        }
        return aids;
    }

    //获取系统参与者ID可用集
    public String findAids(String id) {
        String tierSql = "select tier from sys_user where id = ?";
        String tier = jdbcHelper.findString(tierSql, id);

        StringBuilder aids = new StringBuilder();
        //1. conds拼接父级id
        if (StrUtils.isNotBlank(tier)) {
            String[] pidArr = tier.split("_");
            for (int i = pidArr.length - 1; i >= 0; i--) {
                if (!"".equals(pidArr[i])) {
                    aids.append("'").append(pidArr[i]).append("',");
                }
            }
        } else {
            aids = new StringBuilder("'" + id + "',");
        }
        //2. conds拼接岗位id
        List<String> postList = findPostList(id);
        for (String str : postList) {
            aids.append("'").append(str).append("',");
        }
        aids = new StringBuilder(aids.substring(0, aids.length() - 1));//优化
        //3. conds拼接群组id
        List<String> groupList = findGroupList(aids.toString());
        for (String str : groupList) {
            aids.append(",'").append(str).append("'");
        }
        return aids.toString();
    }

    //获取岗位id集合
    private List<String> findPostList(String uid) {
        String sql = "select pid as id from sys_post_actor where aid=?";
        return jdbcHelper.findSlist(sql, uid);
    }

    //获取群组id集合
    private List<String> findGroupList(String aids) {
        String sql = "select DISTINCT gid as id from sys_group_actor where aid in (" + aids + ")";
        return jdbcHelper.findSlist(sql);
    }

}
