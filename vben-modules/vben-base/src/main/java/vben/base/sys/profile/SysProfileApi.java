package vben.base.sys.profile;

import cn.hutool.crypto.digest.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vben.base.sys.user.SysUser;
import vben.base.sys.user.SysUserService;
import vben.base.tool.oss.main.ToolOssMainService;
import vben.base.tool.oss.root.Zfile;
import vben.base.tool.oss.service.SysOssService;
import vben.common.core.domain.R;
import vben.common.core.utils.StrUtils;
import vben.common.core.utils.file.FileUtils;
import vben.common.core.utils.file.MimeTypeUtils;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;
import vben.common.satoken.utils.LoginHelper;
import vben.common.web.core.BaseController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 个人信息 业务处理
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileApi extends BaseController {

    private final SysUserService userService;

    private final SysOssService ossService;

    private final ToolOssMainService toolOssMainService;

    /**
     * 个人信息
     */
    @GetMapping
    public R<ProfileVo> profile() {
        SysUser user = userService.findById(LoginHelper.getUserId());
        ProfileUserVo vo=new ProfileUserVo();
        vo.setUserId(user.getId());
        vo.setEmail(user.getEmail());
        vo.setGender(user.getGender());
        vo.setDeptId(user.getDepid());
        vo.setUserName(user.getUsername());
        vo.setUserType(user.getType()+"");
        vo.setNickName(user.getName());
        vo.setPhonenumber(user.getMonum());
        vo.setDeptName(user.getDepna());
        vo.setLoginIp(user.getLoip());
        vo.setLoginDate(user.getLotim());
        vo.setAvatar(user.getAvatar());


//        String postGroup = userService.selectUserPostGroup(user.getUserId());
        // 单独做一个vo专门给个人中心用 避免数据被脱敏
        ProfileVo profileVo = new ProfileVo(vo, null, null);
        return R.ok(profileVo);
    }

    /**
     * 修改用户信息
     */
    @PutMapping
    public R<Void> updateProfile(@Validated @RequestBody SysUserProfileBo profile) {
        SysUser user = userService.findById(LoginHelper.getUserId());
        user.setEmail(profile.getEmail());
        user.setName(profile.getNickName());
        user.setGender(profile.getGender());
        user.setMonum(profile.getPhonenumber());
        userService.update(user);
        return R.ok();
//        String username = LoginHelper.getUsername();
//        if (StrUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
//            return R.fail("修改用户'" + username + "'失败，手机号码已存在");
//        }
//        if (StrUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
//            return R.fail("修改用户'" + username + "'失败，邮箱账号已存在");
//        }
//        int rows = DataPermissionHelper.ignore(() -> userService.updateUserProfile(user));
//        if (rows > 0) {
//            return R.ok();
//        }
//        return R.fail("修改个人信息异常，请联系管理员");
    }
//
    /**
     * 重置密码
     *
     * @param bo 新旧密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    //@ApiEncrypt
    @PutMapping("/updatePwd")
    public R<Void> updatePwd(@Validated @RequestBody SysUserPasswordBo bo) {
        String userId = LoginHelper.getUserId();
        String password = userService.findPassword(userId);

        if (!BCrypt.checkpw(bo.getOldPassword(), password)) {
            return R.fail("修改密码失败，旧密码错误");
        }
        if (BCrypt.checkpw(bo.getNewPassword(), password)) {
            return R.fail("新密码不能与旧密码相同");
        }
        userService.resetPassword(userId, BCrypt.hashpw(bo.getNewPassword()));
//        return R.fail("修改密码异常，请联系管理员");
        return R.ok();
    }

    /**
     * 头像上传
     *
     * @param avatarfile 用户头像
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<AvatarVo> avatar(@RequestPart("avatarfile") MultipartFile avatarfile) throws IOException, NoSuchAlgorithmException {
        if (!avatarfile.isEmpty()) {
            String extension = FileUtils.extName(avatarfile.getOriginalFilename());
            if (!StrUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return R.fail("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
            }
            //方式1
            Zfile zfile = toolOssMainService.uploadFile(avatarfile);
//            String url="http://localhost:5666/api/tool/oss/main/show?id="+zfile.getId();
            String url="http://8.153.168.178:8080/tool/oss/main/show?id="+zfile.getId();

            //方式2
//            SysOssVo oss = ossService.upload(avatarfile);
//            String url = oss.getUrl();

            userService.updateAvatar(LoginHelper.getUserId(), url);
            return R.ok(new AvatarVo(url));
        }
        return R.fail("上传图片异常，请联系管理员");
    }

    /**
     * 用户头像信息
     *
     * @param imgUrl 头像地址
     */
    public record AvatarVo(String imgUrl) {}


    /**
     * 用户个人信息
     *
     * @param user      用户信息
     * @param roleGroup 用户所属角色组
     * @param postGroup 用户所属岗位组
     */
    public record ProfileVo(ProfileUserVo user, String roleGroup, String postGroup) {
    }

}
