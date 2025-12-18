package vben.base.sys.social;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SysSocialDao {

    public SysSocial findById(String id) {
        String sql = "select * from sys_social where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysSocial.class), id);
    }

    public List<SysSocialVo> findList(){
        String sql = "select * from sys_social";
        List<SysSocialVo> list = jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(SysSocialVo.class));
        return list;
    }

    public SysSocial findByAuthId(String authId) {
        String sql = "select * from sys_social where auth_id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysSocial.class), authId);
    }

    public void insert(SysSocial main) {
        Isqler sqler = new Isqler("sys_social");
        if(main.getId() == null) {
            main.setId(IdUtils.getSnowflakeNextId());
        }
        main.setCreateTime(new Date());
        sqler.add("id", main.getId());
        sqler.add("create_time", main.getCreateTime());
        sqler.add("user_id", main.getUserId());
        sqler.add("auth_id", main.getAuthId());
        sqler.add("source", main.getSource());
        sqler.add("access_token", main.getAccessToken());
        sqler.add("expire_in", main.getExpireIn());
        sqler.add("refresh_token", main.getRefreshToken());
        sqler.add("open_id", main.getOpenId());
        sqler.add("user_name", main.getUserName());
        sqler.add("nick_name", main.getNickName());
        sqler.add("email", main.getEmail());
        sqler.add("avatar", main.getAvatar());
        sqler.add("access_code", main.getAccessCode());
        sqler.add("union_id", main.getUnionId());
        sqler.add("scope", main.getScope());
        sqler.add("token_type", main.getTokenType());
        sqler.add("id_token", main.getIdToken());
        sqler.add("mac_algorithm", main.getMacAlgorithm());
        sqler.add("mac_key", main.getMacKey());
        sqler.add("code", main.getCode());
        sqler.add("oauth_token", main.getOauthToken());
        sqler.add("oauth_token_secret", main.getOauthTokenSecret());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(SysSocial main) {
        Usqler sqler = new Usqler("sys_social");
        sqler.addWhere("id=?", main.getId());
        sqler.add("user_id", main.getUserId());
        sqler.add("auth_id", main.getAuthId());
        sqler.add("source", main.getSource());
        sqler.add("access_token", main.getAccessToken());
        sqler.add("expire_in", main.getExpireIn());
        sqler.add("refresh_token", main.getRefreshToken());
        sqler.add("open_id", main.getOpenId());
        sqler.add("user_name", main.getUserName());
        sqler.add("nick_name", main.getNickName());
        sqler.add("email", main.getEmail());
        sqler.add("avatar", main.getAvatar());
        sqler.add("access_code", main.getAccessCode());
        sqler.add("union_id", main.getUnionId());
        sqler.add("scope", main.getScope());
        sqler.add("token_type", main.getTokenType());
        sqler.add("id_token", main.getIdToken());
        sqler.add("mac_algorithm", main.getMacAlgorithm());
        sqler.add("mac_key", main.getMacKey());
        sqler.add("code", main.getCode());
        sqler.add("oauth_token", main.getOauthToken());
        sqler.add("oauth_token_secret", main.getOauthTokenSecret());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from sys_social where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
