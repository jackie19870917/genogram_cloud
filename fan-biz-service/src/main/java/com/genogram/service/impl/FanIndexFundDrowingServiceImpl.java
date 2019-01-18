package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.ConstantsStatus;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanIndexFundDrowing;
import com.genogram.entityvo.IndexFundDrowingVo;
import com.genogram.mapper.FanIndexFundDrowingMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IFanIndexFundDrowingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 线上提现 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanIndexFundDrowingServiceImpl extends ServiceImpl<FanIndexFundDrowingMapper, FanIndexFundDrowing> implements IFanIndexFundDrowingService {

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Override
    public Boolean insertFanIndexFundDrowing(FanIndexFundDrowing fanIndexFundDrowing) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        fanIndexFundDrowing.setCreateTime(timeStamp);
        fanIndexFundDrowing.setApproveStatus(1);
        fanIndexFundDrowing.setUpdateTime(timeStamp);

        return this.insert(fanIndexFundDrowing);
    }

    @Override
    public Boolean updateFanIndexFundDrowing(FanIndexFundDrowing fanIndexFundDrowing) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();
        fanIndexFundDrowing.setUpdateTime(timeStamp);

        return this.updateById(fanIndexFundDrowing);
    }

    @Override
    public Page<IndexFundDrowingVo> getIndexFundDrowingVoPage(Integer siteId, Integer pageNo, Integer pageSize) {

        Wrapper<FanIndexFundDrowing> wrapper = new EntityWrapper<FanIndexFundDrowing>();
        wrapper.eq("site_id", siteId);
        wrapper.orderBy("update_time", false);

        Page<FanIndexFundDrowing> fanIndexFundDrowingPage = this.selectPage(new Page<>(pageNo, pageSize), wrapper);

        List<FanIndexFundDrowing> fanIndexFundDrowingList = fanIndexFundDrowingPage.getRecords();

        List list = new ArrayList();
        for (FanIndexFundDrowing fanIndexFundDrowing : fanIndexFundDrowingList) {
            IndexFundDrowingVo indexFundDrowingVo = new IndexFundDrowingVo();

            BeanUtils.copyProperties(fanIndexFundDrowing, indexFundDrowingVo);

            AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(fanIndexFundDrowing.getCreateUser());

            indexFundDrowingVo.setCreateName(allUserLogin.getNickName());

            list.add(indexFundDrowingVo);
        }

        Page<IndexFundDrowingVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(list);
        mapPage.setTotal(fanIndexFundDrowingPage.getTotal());

        return mapPage;
    }

    @Override
    public Page<IndexFundDrowingVo> getIndexFundDrowingVoPage(Integer pageNo, Integer pageSize) {

        Page<FanIndexFundDrowing> fanIndexFundDrowingPage = this.selectPage(new Page<>(pageNo, pageSize));

        List<FanIndexFundDrowing> fanIndexFundDrowingList = fanIndexFundDrowingPage.getRecords();

        List list = new ArrayList();
        for (FanIndexFundDrowing fanIndexFundDrowing : fanIndexFundDrowingList) {
            IndexFundDrowingVo indexFundDrowingVo = new IndexFundDrowingVo();

            BeanUtils.copyProperties(fanIndexFundDrowing, indexFundDrowingVo);

            list.add(indexFundDrowingVo);
        }

        Page<IndexFundDrowingVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(list);
        mapPage.setTotal(fanIndexFundDrowingPage.getTotal());

        return mapPage;
    }

}
