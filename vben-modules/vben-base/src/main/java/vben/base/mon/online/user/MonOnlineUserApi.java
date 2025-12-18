package vben.base.mon.online.user;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.constant.CacheConstants;
import vben.common.core.domain.R;
import vben.common.core.domain.dto.UserOnlineDTO;
import vben.common.core.utils.StrUtils;
import vben.common.core.utils.StreamUtils;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;
import vben.common.redis.utils.RedisUtils;

import java.util.*;

/**
 * 在线用户管理
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mon/online/user")
public class MonOnlineUserApi {

    /**
     * 在线用户查询
     *
     * @param ipaddr   IP地址
     * @param userName 用户名
     */
    @SaCheckPermission("mon:online:query")
    @GetMapping("/list")
    public R<List<MonOnlineUser>> list(String ipaddr, String userName) {
        // 获取所有未过期的 token
        Collection<String> keys = RedisUtils.keys(CacheConstants.ONLINE_TOKEN_KEY + "*");
        List<UserOnlineDTO> userOnlineDTOList = new ArrayList<>();
        for (String key : keys) {
            String token = StrUtils.subAfter(key, ":",true);
            // 如果已经过期则跳过
            if (StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) < -1) {
                continue;
            }
            userOnlineDTOList.add(RedisUtils.getCacheObject(CacheConstants.ONLINE_TOKEN_KEY + token));
        }
        if (StrUtils.isNotEmpty(ipaddr) && StrUtils.isNotEmpty(userName)) {
            userOnlineDTOList = StreamUtils.filter(userOnlineDTOList, userOnline ->
                StrUtils.equals(ipaddr, userOnline.getIpaddr()) &&
                    StrUtils.equals(userName, userOnline.getUserName())
            );
        } else if (StrUtils.isNotEmpty(ipaddr)) {
            userOnlineDTOList = StreamUtils.filter(userOnlineDTOList, userOnline ->
                StrUtils.equals(ipaddr, userOnline.getIpaddr())
            );
        } else if (StrUtils.isNotEmpty(userName)) {
            userOnlineDTOList = StreamUtils.filter(userOnlineDTOList, userOnline ->
                StrUtils.equals(userName, userOnline.getUserName())
            );
        }
        Collections.reverse(userOnlineDTOList);
        userOnlineDTOList.removeAll(Collections.singleton(null));
        List<MonOnlineUser> userOnlineList =new ArrayList<>();
        for (UserOnlineDTO userOnlineDTO : userOnlineDTOList) {
            MonOnlineUser monOnlineUser = new MonOnlineUser();
            monOnlineUser.setTokenId(userOnlineDTO.getTokenId());
            monOnlineUser.setDepna(userOnlineDTO.getDeptName());
            monOnlineUser.setUsena(userOnlineDTO.getUserName());
            monOnlineUser.setClientKey(userOnlineDTO.getClientKey());
            monOnlineUser.setDetyp(userOnlineDTO.getDeviceType());
            monOnlineUser.setIp(userOnlineDTO.getIpaddr());
            monOnlineUser.setLoginLocation(userOnlineDTO.getLoginLocation());
            monOnlineUser.setBrowser(userOnlineDTO.getBrowser());
            monOnlineUser.setOs(userOnlineDTO.getOs());
            monOnlineUser.setLoginTime(userOnlineDTO.getLoginTime());
            userOnlineList.add(monOnlineUser);
        }
//        List<MonOnlineUser> userOnlineList = BeanUtil.copyToList(userOnlineDTOList, MonOnlineUser.class);
        userOnlineList.sort(Comparator.comparingLong(MonOnlineUser::getLoginTime).reversed());
        return R.ok(userOnlineList);
    }

//    /**
//     * 强退用户
//     *
//     * @param tokenId token值
//     */
//    @SaCheckPermission("monitor:online:forceLogout")
//    @Log(title = "在线用户", businessType = BusinessType.FORCE)
//    @RepeatSubmit()
//    @DeleteMapping("/{tokenId}")
//    public R<Void> forceLogout(@PathVariable String tokenId) {
//        try {
//            StpUtil.kickoutByTokenValue(tokenId);
//        } catch (NotLoginException ignored) {
//        }
//        return R.ok();
//    }
//
//    /**
//     * 获取当前用户登录在线设备
//     */
//    @GetMapping()
//    public TableDataInfo<SysUserOnline> getInfo() {
//        // 获取指定账号 id 的 token 集合
//        List<String> tokenIds = StpUtil.getTokenValueListByLoginId(StpUtil.getLoginIdAsString());
//        List<UserOnlineDTO> userOnlineDTOList = tokenIds.stream()
//            .filter(token -> StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) >= -1)
//            .map(token -> (UserOnlineDTO) RedisUtils.getCacheObject(CacheConstants.ONLINE_TOKEN_KEY + token))
//            .collect(Collectors.toList());
//        //复制和处理 SysUserOnline 对象列表
//        Collections.reverse(userOnlineDTOList);
//        userOnlineDTOList.removeAll(Collections.singleton(null));
//        List<SysUserOnline> userOnlineList = BeanUtil.copyToList(userOnlineDTOList, SysUserOnline.class);
//        return TableDataInfo.build(userOnlineList);
//    }
//

    /**
     * 强退当前在线设备
     *
     * @param tokenId token值
     */
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @SaCheckPermission("mon:online:delete")
    @DeleteMapping("/kick/{tokenId}")
    public R<Void> remove(@PathVariable("tokenId") String tokenId) {
        try {
            // 获取指定账号 id 的 token 集合
            List<String> keys = StpUtil.getTokenValueListByLoginId(StpUtil.getLoginIdAsString());
            keys.stream()
                .filter(key -> key.equals(tokenId))
                .findFirst()
                .ifPresent(key -> StpUtil.kickoutByTokenValue(tokenId));
        } catch (NotLoginException ignored) {
        }
        return R.ok();
    }

}
