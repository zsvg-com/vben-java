package vben.base.sys.org.user;

import cn.hutool.crypto.digest.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.sys.org.root.Org;
import vben.base.sys.org.root.OrgDao;
import vben.base.sys.org.user.bo.IdAvtagBo;
import vben.base.sys.org.user.vo.RegisterVo;
import vben.common.core.exception.base.BaseException;
import vben.common.core.utils.DateUtils;
import vben.common.core.utils.IdUtils;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysOrgUserService {

    public String findPassword(String id) {
        return userDao.findPassword(id);
    }

    public void resetPassword(String id, String password) {
        userDao.resetPassword(id, password);
    }

    public void changeAvatg(IdAvtagBo bo) {
        userDao.changeAvatg(bo);
    }

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public void register(RegisterVo authVo) {
        String phone = authVo.getPhone();
        String password = authVo.getPassword();
        String nickName = authVo.getNickName();
        String msg = "此手机号码已注册过，请勿重复注册";
        // 验证手机号是否注册过
        if (userDao.existsByMonum(phone)) {
            throw new BaseException(msg);
        }
//        String salt = RandomUtil.randomString(4);
//        String chatNo = IdUtils.simpleUUID();
        SysOrgUser user = new SysOrgUser()
            .setId(IdUtils.getSnowflakeNextIdStr())
            .setName(nickName)
            .setUsnam(nickName)
//                .setChnum(chatNo)
//                .setGender(GenderEnum.MALE)
//                .setPortrait(ApiConstant.DEFAULT_PORTRAIT)
            .setMonum(phone)
//                .setPacod(password)
            .setPacod(BCrypt.hashpw(password))
            .setAvtag(true)
            .setCrtim(DateUtils.date());
        try {
            userDao.insert(user);
            Org org = new Org(user.getId(), user.getName(), 2);
            orgDao.insert(org);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new BaseException(msg);
        }
    }

    public void insert(SysOrgUser main) {
        if (main.getId() == null) {
            main.setId("u" + IdUtils.getSnowflakeNextIdStr());
        }
        main.setCruid(LoginHelper.getUserId());
        if (StrUtils.isEmpty(main.getTier())) {
            if (main.getDepid() == null) {
                main.setTier("_" + main.getId() + "_");
            } else {
                String tier = jdbcHelper.findString("select tier from sys_org_dept where id=?", main.getDepid());
                main.setTier(tier + main.getId() + "_");
            }
        }
        main.setPacod(BCrypt.hashpw(main.getPacod()));
        Org org = new Org(main.getId(), main.getName(), 2);
        orgDao.insert(org);
        userDao.insert(main);
    }

    public void update(SysOrgUser main) {
        main.setUptim(new Date());
        main.setUpuid(LoginHelper.getUserId());
        Org org = new Org(main.getId(), main.getName(), 2);
        if (main.getDepid() == null) {
            main.setTier("_" + main.getId() + "_");
        } else {
            String tier = jdbcHelper.findString("select tier from sys_org_dept where id=?", main.getDepid());
            main.setTier(tier + main.getId() + "_");
        }
        userDao.update(main);
        orgDao.update(org);
    }

    public int delete(String[] ids) {
        for (String id : ids) {
            if (!"u1".equals(id)) {
                userDao.deleteById(id);
                orgDao.deleteById(id);
            }
        }
        return ids.length;
    }

    @Transactional(readOnly = true)
    public SysOrgUser findById(String id) {
        return userDao.findById(id);
    }

    private final JdbcHelper jdbcHelper;

    private final SysOrgUserDao userDao;

    private final OrgDao orgDao;

    public void updateAvatar(String id, String avatar) {
        userDao.updateAvatar(id, avatar);
    }
}
