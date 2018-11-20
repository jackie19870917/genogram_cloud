package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsCharityPayIn;
import com.genogram.entityvo.DonorVo;
import com.genogram.mapper.FanNewsCharityPayInMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IFanNewsCharityPayInService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊网-慈善公益-捐款人 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsCharityPayInServiceImpl extends ServiceImpl<FanNewsCharityPayInMapper, FanNewsCharityPayIn> implements IFanNewsCharityPayInService {

    @Autowired
    private FanNewsCharityPayInMapper fanNewsCharityPayInMapper;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Override
    public Page<DonorVo> getDonorVoPage(Page<FanNewsCharityPayIn> mapPage, Map map) {

        List<FanNewsCharityPayIn> fanNewsCharityPayInList = fanNewsCharityPayInMapper.getDonorVoPage(mapPage, map);

        if (fanNewsCharityPayInList.size()==0) {
            return null;
        }

        List list = new ArrayList();
        for (FanNewsCharityPayIn fanNewsCharityPayIn : fanNewsCharityPayInList) {
            list.add(fanNewsCharityPayIn.getPayUsrId());
        }

        Wrapper<AllUserLogin> entity = new EntityWrapper<AllUserLogin>();
        entity.in("id", list);

        List<AllUserLogin> allUserLoginList = allUserLoginService.selectList(entity);

        Page<DonorVo> page = new Page<>(mapPage.getCurrent(), mapPage.getSize());
        list = getList(fanNewsCharityPayInList, allUserLoginList);
        page.setRecords(list);
        page.setTotal(mapPage.getTotal());

        return page;
    }

    private List getList(List<FanNewsCharityPayIn> fanNewsCharityPayInList, List<AllUserLogin> allUserLoginList) {
        List list = new ArrayList();
        for (FanNewsCharityPayIn fanNewsCharityPayIn : fanNewsCharityPayInList) {
            for (AllUserLogin allUserLogin : allUserLoginList) {
                if (allUserLogin.getId().equals(fanNewsCharityPayIn.getPayUsrId())) {
                    DonorVo donorVo = new DonorVo();
                    donorVo.setAllUserLogin(allUserLogin);
                    donorVo.setFanNewsCharityPayIn(fanNewsCharityPayIn);
                    list.add(donorVo);
                }
            }
        }
        return list;
    }

    @Override
    public Page<DonorVo> getDonorVoPageByTime(Integer showId, List status, Integer pageNo, Integer pageSize) {

        Wrapper<FanNewsCharityPayIn> fanNewsCharityPayInWrapper = new EntityWrapper<FanNewsCharityPayIn>();

        fanNewsCharityPayInWrapper.eq("show_id", showId);
        fanNewsCharityPayInWrapper.in("status", status);
        fanNewsCharityPayInWrapper.orderBy("create_time", false);
        fanNewsCharityPayInWrapper.groupBy("pay_usr_id");

        Page<FanNewsCharityPayIn> fanNewsCharityPayInPage = this.selectPage(new Page<FanNewsCharityPayIn>(pageNo, pageSize), fanNewsCharityPayInWrapper);

        List<FanNewsCharityPayIn> fanNewsCharityPayInList = fanNewsCharityPayInPage.getRecords();

        if (fanNewsCharityPayInList.size()==0) {
            return null;
        }

        List list = new ArrayList();
        for (FanNewsCharityPayIn fanNewsCharityPayIn : fanNewsCharityPayInList) {
            list.add(fanNewsCharityPayIn.getPayUsrId());
        }

        Wrapper<AllUserLogin> entity = new EntityWrapper<AllUserLogin>();
        entity.in("id", list);

        List<AllUserLogin> allUserLoginList = allUserLoginService.selectList(entity);

        list = getList(fanNewsCharityPayInList, allUserLoginList);

        Page<DonorVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(list);
        mapPage.setTotal(fanNewsCharityPayInPage.getTotal());

        return mapPage;
    }

    @Override
    public Boolean insertFanNewsCharityPayIn(FanNewsCharityPayIn fanNewsCharityPayIn) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        if (StringUtils.isEmpty(this.selectOne(fanNewsCharityPayIn))) {
            fanNewsCharityPayIn.setCreateTime(timeStamp);
            fanNewsCharityPayIn.setCreateUser(fanNewsCharityPayIn.getPayUsrId());
            fanNewsCharityPayIn.setUpdateUser(fanNewsCharityPayIn.getPayUsrId());
        }

        fanNewsCharityPayIn.setUpdateTime(timeStamp);

        return this.insertOrUpdate(fanNewsCharityPayIn);
    }

    @Override
    public FanNewsCharityPayIn selectOne(FanNewsCharityPayIn fanNewsCharityPayIn) {

        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("order_id", fanNewsCharityPayIn.getOrderId());

        FanNewsCharityPayIn fanNewsCharityPayIn1 = this.selectOne(wrapper);

        if (StringUtils.isEmpty(fanNewsCharityPayIn1)) {
            return null;
        }

        return fanNewsCharityPayIn1;
    }

}
