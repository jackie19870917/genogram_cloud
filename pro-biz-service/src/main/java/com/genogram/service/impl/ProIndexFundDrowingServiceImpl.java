package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProIndexFundDrowing;
import com.genogram.entityvo.IndexFundDrowingVo;
import com.genogram.mapper.ProIndexFundDrowingMapper;
import com.genogram.service.IProIndexFundDrowingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 省级线上提现 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-15
 */
@Service
public class ProIndexFundDrowingServiceImpl extends ServiceImpl<ProIndexFundDrowingMapper, ProIndexFundDrowing> implements IProIndexFundDrowingService {

    @Override
    public Boolean insertProIndexFundDrowing(ProIndexFundDrowing proIndexFundDrowing) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();
        proIndexFundDrowing.setCreateTime(timeStamp);
        proIndexFundDrowing.setUpdateTime(timeStamp);
        proIndexFundDrowing.setApproveStatus(1);

        return this.insert(proIndexFundDrowing);
    }

    @Override
    public Page<IndexFundDrowingVo> getIndexFundDrowingVoPage(Integer siteId, Integer pageNo, Integer pageSize) {
        Wrapper<ProIndexFundDrowing> wrapper = new EntityWrapper<ProIndexFundDrowing>();
        wrapper.eq("site_id", siteId);
        wrapper.orderBy("update_time", false);

        Page<ProIndexFundDrowing> proIndexFundDrowingPage = this.selectPage(new Page<>(pageNo, pageSize), wrapper);

        List<ProIndexFundDrowing> proIndexFundDrowingList = proIndexFundDrowingPage.getRecords();

        List list = new ArrayList();
        for (ProIndexFundDrowing proIndexFundDrowing : proIndexFundDrowingList) {
            IndexFundDrowingVo indexFundDrowingVo = new IndexFundDrowingVo();

            BeanUtils.copyProperties(proIndexFundDrowing, indexFundDrowingVo);

            list.add(indexFundDrowingVo);
        }

        Page<IndexFundDrowingVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(list);
        mapPage.setTotal(proIndexFundDrowingPage.getTotal());

        return mapPage;
    }

}
