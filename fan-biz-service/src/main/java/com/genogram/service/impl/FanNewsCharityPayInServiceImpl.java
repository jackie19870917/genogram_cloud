package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCharityPayIn;
import com.genogram.entityvo.DonorVo;
import com.genogram.mapper.FanNewsCharityPayInMapper;
import com.genogram.service.IFanNewsCharityPayInService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

   /* @Override
    public Page<FanNewsCharityPayIn> selectPage(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        Wrapper<FanNewsCharityPayIn> entity = new EntityWrapper<FanNewsCharityPayIn>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.groupBy("pay_usr_id");
        // entity.orderBy(, "pay_amount");
        entity.orderBy("sum(pay_amount)", false);
        Page<FanNewsCharityPayIn> fanNewsCharityPayInPage = this.selectPage(new Page<>(pageNo, pageSize), entity);
        return fanNewsCharityPayInPage;
    }*/

    @Override
    public List<DonorVo> queryMyItems(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        Map map = new HashMap();
        map.put("showId", showId);
        map.put("status", status);
        map.put("pageNo", pageNo-1);
        map.put("pageSize", pageSize);
        return fanNewsCharityPayInMapper.queryMyItems(map);
    }
}
