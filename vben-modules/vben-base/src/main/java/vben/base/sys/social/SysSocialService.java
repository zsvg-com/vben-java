package vben.base.sys.social;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.core.utils.MapstructUtils;

import java.util.List;

/**
 * 社会化关系Service业务层处理
 *
 * @author thiszhc
 * @date 2023-06-12
 */
@RequiredArgsConstructor
@Service
public class SysSocialService implements ISysSocialService {

    private final SysSocialDao dao;


    /**
     * 查询社会化关系
     */
    @Override
    public SysSocialVo queryById(String id) {
        SysSocial sysSocial = dao.findById(id);
        SysSocialVo convert = MapstructUtils.convert(sysSocial, SysSocialVo.class);
        return convert;
    }

    /**
     * 授权列表
     */
    @Override
    public List<SysSocialVo> queryList(SysSocialBo bo) {
//        LambdaQueryWrapper<SysSocial> lqw = new LambdaQueryWrapper<SysSocial>()
//            .eq(ObjectUtils.isNotNull(bo.getUserId()), SysSocial::getUserId, bo.getUserId())
//            .eq(StrUtils.isNotBlank(bo.getAuthId()), SysSocial::getAuthId, bo.getAuthId())
//            .eq(StrUtils.isNotBlank(bo.getSource()), SysSocial::getSource, bo.getSource());
//        return baseMapper.selectVoList(lqw);
       return dao.findList();
    }

    @Override
    public List<SysSocialVo> queryListByUserId(Long userId) {
//        return baseMapper.selectVoList(new LambdaQueryWrapper<SysSocial>().eq(SysSocial::getUserId, userId));
        return dao.findList();
    }


    /**
     * 新增社会化关系
     */
    @Override
    public Boolean insertByBo(SysSocialBo bo) {
        SysSocial add = MapstructUtils.convert(bo, SysSocial.class);
        validEntityBeforeSave(add);
        dao.insert(add) ;
        if (add != null) {
            bo.setId(add.getId());
        } else {
            return false;
        }
        return true;
    }

    /**
     * 更新社会化关系
     */
    @Override
    public Boolean updateByBo(SysSocialBo bo) {
        SysSocial update = MapstructUtils.convert(bo, SysSocial.class);
        validEntityBeforeSave(update);
        dao.update(update);
        return true;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysSocial entity) {
        //TODO 做一些数据校验,如唯一约束
    }


    /**
     * 删除社会化关系
     */
    @Override
    public Boolean deleteWithValidById(Long id) {
        dao.deleteById(id);
        return true;
    }


    /**
     * 根据 authId 查询用户信息
     *
     * @param authId 认证id
     * @return 授权信息
     */
    @Override
    public List<SysSocialVo> selectByAuthId(String authId) {
//        SysSocial sysSocial = dao.findById(authId);
//        SysSocialVo convert = MapstructUtils.convert(sysSocial, SysSocialVo.class);
        return dao.findList();
    }

}
