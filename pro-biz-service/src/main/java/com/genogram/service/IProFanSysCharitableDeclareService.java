package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanSysCharitableDeclare;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xiaohei
 * @since 2018-11-29
 */
public interface IProFanSysCharitableDeclareService extends IService<FanSysCharitableDeclare> {
    /**
     * 联谊会慈善帮扶后台分页查询
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FanSysCharitableDeclare> getCharitableDeclarePage(Wrapper<FanSysCharitableDeclare> entity, Integer pageNo, Integer pageSize);

    /**
     * 联谊会慈善帮扶申报详情
     *
     * @param id
     * @return
     */
    FanSysCharitableDeclare getFamilyStructureDetails(Integer id);

    /**
     * 省级慈善帮扶申报详情 同意按钮
     *
     * @param userLoginInfoByToken
     * @param fanSysCharitableDeclare
     * @param ratify
     * @return
     */
    Boolean familyStructureDetailsConsent(AllUserLogin userLoginInfoByToken, FanSysCharitableDeclare fanSysCharitableDeclare, Integer ratify);

    /**
     * 省级慈善帮扶申报详情 退回按钮
     *
     * @param userLoginInfoByToken
     * @param fanSysCharitableDeclare
     * @param ratify
     * @return
     */
    Boolean familyStructureDetailsDisagree(AllUserLogin userLoginInfoByToken, FanSysCharitableDeclare fanSysCharitableDeclare, Integer ratify);

    /**
     * 省级慈善帮扶总金额
     * @param map
     * @return
     */
    BigDecimal familyStructureMoney(Map map);

    /**
     * 省级慈善帮扶经办人输入金额
     *
     * @param userLoginInfoByToken
     * @param id
     * @param responsiblePersonMoney
     * @return
     */
    Boolean familyStructureResponsiblePerson(AllUserLogin userLoginInfoByToken, Integer id, BigDecimal responsiblePersonMoney);

}
