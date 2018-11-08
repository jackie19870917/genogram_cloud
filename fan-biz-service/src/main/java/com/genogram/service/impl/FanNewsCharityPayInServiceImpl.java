package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.AllUserReg;
import com.genogram.entity.FanNewsCharityPayIn;
import com.genogram.entityvo.DonorVo;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.mapper.AllUserRegMapper;
import com.genogram.mapper.FanNewsCharityPayInMapper;
import com.genogram.service.IFanNewsCharityPayInService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private AllUserLoginMapper AllUserLoginMapper;

    @Override
    public List<DonorVo> getDonorVoPage(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        Map map = new HashMap();
        map.put("showId", showId);
        map.put("status", status);
        map.put("pageNo", pageNo-1);
        map.put("pageSize", pageSize);

        List<FanNewsCharityPayIn> fanNewsCharityPayInList = fanNewsCharityPayInMapper.getDonorVoPage(map);

        List list = new ArrayList();
        for (FanNewsCharityPayIn fanNewsCharityPayIn : fanNewsCharityPayInList) {
            list.add(fanNewsCharityPayIn.getPayUsrId());
        }

        Wrapper<AllUserLogin> entity = new EntityWrapper<AllUserLogin>();
        entity.in("id", list);

        List<AllUserLogin> allUserLoginList = AllUserLoginMapper.selectList(entity);

        for (FanNewsCharityPayIn fanNewsCharityPayIn : fanNewsCharityPayInList) {
            for (AllUserLogin allUserLogin : allUserLoginList) {
                if (allUserLogin.getId() == fanNewsCharityPayIn.getPayUsrId()) {
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
    public List<DonorVo> getDonorVoPageByTime(Integer showId, Integer status, Integer pageNo, Integer pageSize) {

        Wrapper<FanNewsCharityPayIn> fanNewsCharityPayInWrapper = new EntityWrapper<FanNewsCharityPayIn>();

        fanNewsCharityPayInWrapper.eq("show_id", showId);
        fanNewsCharityPayInWrapper.eq("status", status);
        fanNewsCharityPayInWrapper.orderBy("create_time", false);

        List<FanNewsCharityPayIn> fanNewsCharityPayInList = fanNewsCharityPayInMapper.selectPage(new Page<FanNewsCharityPayIn>(pageNo, pageSize), fanNewsCharityPayInWrapper);

        List list = new ArrayList();
        for (FanNewsCharityPayIn fanNewsCharityPayIn : fanNewsCharityPayInList) {
            list.add(fanNewsCharityPayIn.getPayUsrId());
        }

        Wrapper<AllUserLogin> entity = new EntityWrapper<AllUserLogin>();
        entity.in("id", list);

        List<AllUserLogin> allUserLoginList = AllUserLoginMapper.selectList(entity);

        for (FanNewsCharityPayIn fanNewsCharityPayIn : fanNewsCharityPayInList) {
            for (AllUserLogin allUserLogin : allUserLoginList) {
                if (allUserLogin.getId() == fanNewsCharityPayIn.getPayUsrId()) {
                    DonorVo donorVo = new DonorVo();
                    donorVo.setAllUserLogin(allUserLogin);
                    donorVo.setFanNewsCharityPayIn(fanNewsCharityPayIn);
                    list.add(donorVo);
                }
            }
        }

        return list;
    }
}
