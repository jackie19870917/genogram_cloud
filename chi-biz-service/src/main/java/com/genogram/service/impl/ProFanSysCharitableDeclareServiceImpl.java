package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanSysCharitableDeclare;
import com.genogram.mapper.FanSysCharitableDeclareMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IProFanSysCharitableDeclareService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaohei
 * @since 2018-11-29
 */
@Service
public class ProFanSysCharitableDeclareServiceImpl extends ServiceImpl<FanSysCharitableDeclareMapper, FanSysCharitableDeclare> implements IProFanSysCharitableDeclareService {

    @Autowired
    private IUploadFileService iuploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private FanSysCharitableDeclareMapper fanSysCharitableDeclareMapper;


    /**
     * 分页查询
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<FanSysCharitableDeclare> getCharitableDeclarePage(Wrapper<FanSysCharitableDeclare> entity, Integer pageNo, Integer pageSize) {
        //分页查询文章主表
        Page<FanSysCharitableDeclare> fanNewsFamilyRecord = this.selectPage(new Page<FanSysCharitableDeclare>(pageNo, pageSize), entity);
        return fanNewsFamilyRecord;
    }

    /**
     * 联谊会慈善帮扶申报详情
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 16:16
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public FanSysCharitableDeclare getFamilyStructureDetails(Integer id) {
        FanSysCharitableDeclare fanSysCharitableDeclare = this.selectById(id);
        return fanSysCharitableDeclare;
    }

    /**
     * 省级慈善帮扶申报详情 同意按钮
     *
     * @Author: yuzhou
     * @Date: 2018-12-13
     * @Time: 10:26
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean familyStructureDetailsConsent(AllUserLogin userLoginInfoByToken, FanSysCharitableDeclare fanSysCharitableDeclare, Integer ratify) {
        //获取姓名
        AllUserLogin allUserLogin = allUserLoginService.selectById(userLoginInfoByToken.getId());
        //ratify区分三级别审核 1:审查人,2:审核人,3:审批人
        Integer status02 = 2;
        Integer status03 = 3;
        if (ratify == 1) {
            fanSysCharitableDeclare.setExaminant(allUserLogin.getNickName());
            //审查人是否同意  1:同意 2:退回
            fanSysCharitableDeclare.setIsExaminant(1);

        } else if (ratify.equals(status02)) {
            fanSysCharitableDeclare.setVerifier(allUserLogin.getNickName());
            //审核人是否同意  1:同意 2:退回
            fanSysCharitableDeclare.setIsVerifier(1);
        } else if (ratify.equals(status03)) {
            fanSysCharitableDeclare.setApprover(allUserLogin.getNickName());
            //审批人是否同意  1:同意 2:退回
            fanSysCharitableDeclare.setIsApprover(1);
            //状态(0:审核通过;1:审核中;2:草稿3:审核不通过)
            fanSysCharitableDeclare.setStatus(0);
        }

        //修改人
        fanSysCharitableDeclare.setUpdateUser(userLoginInfoByToken.getId());
        //修改时间
        fanSysCharitableDeclare.setUpdateTime(DateUtil.getCurrentTimeStamp());

        //插入意见
        boolean result = this.updateAllColumnById(fanSysCharitableDeclare);
        return result;
    }

    /**
     * 省级慈善帮扶申报详情 退回按钮
     *
     * @Author: yuzhou
     * @Date: 2018-12-13
     * @Time: 11:49
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean familyStructureDetailsDisagree(AllUserLogin userLoginInfoByToken, FanSysCharitableDeclare fanSysCharitableDeclare, Integer ratify) {
        //获取姓名
        AllUserLogin allUserLogin = allUserLoginService.selectById(userLoginInfoByToken.getId());
        Integer status02 = 2;
        Integer status03 = 3;
        //ratify区分三级别审核 1:审查人,2:审核人,3:审批人
        if (ratify == 1) {
            fanSysCharitableDeclare.setExaminant(allUserLogin.getNickName());
            //审查人是否同意  1:同意 2:退回
            fanSysCharitableDeclare.setIsExaminant(2);
        } else if (ratify.equals(status02)) {
            fanSysCharitableDeclare.setVerifier(allUserLogin.getNickName());
            //审核人是否同意  1:同意 2:退回
            fanSysCharitableDeclare.setIsVerifier(2);
        } else if (ratify.equals(status03)) {
            fanSysCharitableDeclare.setApprover(allUserLogin.getNickName());
            //审批人是否同意  1:同意 2:退回
            fanSysCharitableDeclare.setIsApprover(2);
        }

        //修改人
        fanSysCharitableDeclare.setUpdateUser(userLoginInfoByToken.getId());
        //修改时间
        fanSysCharitableDeclare.setUpdateTime(DateUtil.getCurrentTimeStamp());

        //状态(0:审核通过;1:审核中;2:草稿3:审核不通过)
        fanSysCharitableDeclare.setStatus(3);
        //插入意见
        boolean result = this.updateAllColumnById(fanSysCharitableDeclare);
        return result;
    }

    /**
     * 省级慈善帮扶总金额
     *
     * @Author: yuzhou
     * @Date: 2018-12-13
     * @Time: 14:06
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public BigDecimal familyStructureMoney(Map map) {
        BigDecimal money = fanSysCharitableDeclareMapper.familyStructureMoney(map);
        return money;
    }

    /**
     * 省级慈善帮扶经办人输入金额
     *
     * @Author: yuzhou
     * @Date: 2018-12-13
     * @Time: 16:36
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean familyStructureResponsiblePerson(AllUserLogin userLoginInfoByToken, Integer id, BigDecimal responsiblePersonMoney) {
        FanSysCharitableDeclare fanSysCharitableDeclare = this.selectById(id);
        if (StringsUtils.isEmpty(fanSysCharitableDeclare)) {
            return null;
        }
        //获取经办人姓名
        AllUserLogin allUserLogin = allUserLoginService.selectById(userLoginInfoByToken.getId());

        //存储信息
        fanSysCharitableDeclare.setResponsiblePerson(allUserLogin.getNickName());
        fanSysCharitableDeclare.setResponsiblePersonMoney(responsiblePersonMoney);

        //修改人
        fanSysCharitableDeclare.setUpdateUser(userLoginInfoByToken.getId());
        //修改时间
        fanSysCharitableDeclare.setUpdateTime(DateUtil.getCurrentTimeStamp());

        //存储数据
        boolean result = this.updateAllColumnById(fanSysCharitableDeclare);
        return result;
    }


}
