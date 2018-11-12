package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.AllUserReg;
import com.genogram.entity.FanNewsCharityPayIn;
import com.genogram.entityvo.DonorVo;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.mapper.AllUserRegMapper;
import com.genogram.mapper.FanNewsCharityPayInMapper;
import com.genogram.service.IFanNewsCharityPayInService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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
    private AllUserLoginMapper allUserLoginMapper;

    @Override
    public Page<DonorVo> getDonorVoPage(Page<FanNewsCharityPayIn> mapPage, Map map) {

        List<FanNewsCharityPayIn> fanNewsCharityPayInList = fanNewsCharityPayInMapper.getDonorVoPage(mapPage, map);

        List list = new ArrayList();
        for (FanNewsCharityPayIn fanNewsCharityPayIn : fanNewsCharityPayInList) {
            list.add(fanNewsCharityPayIn.getPayUsrId());
        }

        Wrapper<AllUserLogin> entity = new EntityWrapper<AllUserLogin>();
        entity.in("id", list);

        List<AllUserLogin> allUserLoginList = allUserLoginMapper.selectList(entity);

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

        List list = new ArrayList();
        for (FanNewsCharityPayIn fanNewsCharityPayIn : fanNewsCharityPayInList) {
            list.add(fanNewsCharityPayIn.getPayUsrId());
        }

        Wrapper<AllUserLogin> entity = new EntityWrapper<AllUserLogin>();
        entity.in("id", list);

        List<AllUserLogin> allUserLoginList = allUserLoginMapper.selectList(entity);

        list = getList(fanNewsCharityPayInList, allUserLoginList);

        Page<DonorVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(list);
        mapPage.setTotal(fanNewsCharityPayInPage.getTotal());

        return mapPage;
    }

    @Override
    public Boolean insertFanNewsCharityPayIn(FanNewsCharityPayIn fanNewsCharityPayIn) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        fanNewsCharityPayIn.setCreateTime(timeStamp);
        fanNewsCharityPayIn.setUpdateTime(timeStamp);

        return this.insert(fanNewsCharityPayIn);
    }
}
