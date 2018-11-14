package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ProNewsCharityPayIn;
import com.genogram.entityvo.DonorVo;
import com.genogram.mapper.ProNewsCharityPayInMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IProNewsCharityPayInService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 省级-慈善公益-捐款人 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProNewsCharityPayInServiceImpl extends ServiceImpl<ProNewsCharityPayInMapper, ProNewsCharityPayIn> implements IProNewsCharityPayInService {

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private ProNewsCharityPayInMapper proNewsCharityPayInMapper;

    @Override
    public Page<DonorVo> getDonorVoPage(Page<ProNewsCharityPayIn> mapPage, Map map) {

        List<ProNewsCharityPayIn> proNewsCharityPayInList = proNewsCharityPayInMapper.getDonorVoPage(mapPage, map);

        if (proNewsCharityPayInList.size()==0) {
            return null;
        }

        List list = new ArrayList();
        for (ProNewsCharityPayIn proNewsCharityPayIn : proNewsCharityPayInList) {
            list.add(proNewsCharityPayIn.getPayUsrId());
        }

        Wrapper<AllUserLogin> entity = new EntityWrapper<AllUserLogin>();
        entity.in("id", list);

        List<AllUserLogin> allUserLoginList = allUserLoginService.selectList(entity);

        Page<DonorVo> page = new Page<>(mapPage.getCurrent(), mapPage.getSize());
        list = getList(proNewsCharityPayInList, allUserLoginList);
        page.setRecords(list);
        page.setTotal(mapPage.getTotal());

        return page;
    }

    @Override
    public Page<DonorVo> getDonorVoPageByTime(Integer showId, List status, Integer pageNo, Integer pageSize) {

        Wrapper<ProNewsCharityPayIn> entityWrapper = new EntityWrapper<ProNewsCharityPayIn>();

        entityWrapper.eq("show_id", showId);
        entityWrapper.in("status", status);
        entityWrapper.orderBy("create_time", false);
        entityWrapper.groupBy("pay_usr_id");

        Page<ProNewsCharityPayIn> proNewsCharityPayInPage = this.selectPage(new Page<ProNewsCharityPayIn>(pageNo, pageSize), entityWrapper);

        List<ProNewsCharityPayIn> proNewsCharityPayInList = proNewsCharityPayInPage.getRecords();

        if (proNewsCharityPayInList.size()==0) {
            return null;
        }

        List list = new ArrayList();
        for (ProNewsCharityPayIn proNewsCharityPayIn : proNewsCharityPayInList) {
            list.add(proNewsCharityPayIn.getPayUsrId());
        }

        Wrapper<AllUserLogin> entity = new EntityWrapper<AllUserLogin>();
        entity.in("id", list);

        List<AllUserLogin> allUserLoginList = allUserLoginService.selectList(entity);

        list = getList(proNewsCharityPayInList, allUserLoginList);

        Page<DonorVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(list);
        mapPage.setTotal(proNewsCharityPayInPage.getTotal());

        return mapPage;
    }

    @Override
    public Boolean insertProNewsCharityPayIn(ProNewsCharityPayIn proNewsCharityPayIn) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        proNewsCharityPayIn.setCreateTime(timeStamp);
        proNewsCharityPayIn.setUpdateTime(timeStamp);

        return this.insert(proNewsCharityPayIn);
    }

    private List getList(List<ProNewsCharityPayIn> proNewsCharityPayInList, List<AllUserLogin> allUserLoginList) {
        List list = new ArrayList();
        for (ProNewsCharityPayIn proNewsCharityPayIn : proNewsCharityPayInList) {
            for (AllUserLogin allUserLogin : allUserLoginList) {
                if (allUserLogin.getId().equals(proNewsCharityPayIn.getPayUsrId())) {
                    DonorVo donorVo = new DonorVo();
                    donorVo.setProNewsCharityPayIn(proNewsCharityPayIn);
                    donorVo.setAllUserLogin(allUserLogin);
                    list.add(donorVo);
                }
            }
        }
        return list;
    }
}
