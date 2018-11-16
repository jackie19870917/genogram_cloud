package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.ConstantsStatus;
import com.genogram.entity.FanIndexFundDrowing;
import com.genogram.entityvo.IndexFundDrowingVo;
import com.genogram.mapper.FanIndexFundDrowingMapper;
import com.genogram.service.IFanIndexFundDrowingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    @Override
    public Boolean insertFanIndexFundDrowing(FanIndexFundDrowing fanIndexFundDrowing) {

        fanIndexFundDrowing.setCreateTime(DateUtil.getCurrentTimeStamp());
        fanIndexFundDrowing.setApproveStatus(1);
        fanIndexFundDrowing.setUpdateTime(DateUtil.getCurrentTimeStamp());

        return this.insert(fanIndexFundDrowing);
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

            /*if (fanIndexFundDrowing.getApproveStatus() == 1) {
                indexFundDrowingVo.setStatusName(ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_1);
            } else if (fanIndexFundDrowing.getApproveStatus() == 2) {
                indexFundDrowingVo.setStatusName(ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_2);
            } else if (fanIndexFundDrowing.getApproveStatus() == 3) {
                indexFundDrowingVo.setStatusName(ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_3);
            } else if (fanIndexFundDrowing.getApproveStatus() == 4) {
                indexFundDrowingVo.setStatusName(ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_4);
            }*/

            BeanUtils.copyProperties(fanIndexFundDrowing, indexFundDrowingVo);

            list.add(indexFundDrowingVo);
        }

        Page<IndexFundDrowingVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(list);
        mapPage.setTotal(fanIndexFundDrowingPage.getTotal());

        return mapPage;
    }

}
