package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamily;
import com.genogram.entity.FanNewsCharityOut;
import com.genogram.mapper.FanNewsCharityOutMapper;
import com.genogram.service.IFanNewsCharityOutService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会-家族慈善财务支出表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsCharityOutServiceImpl extends ServiceImpl<FanNewsCharityOutMapper, FanNewsCharityOut> implements IFanNewsCharityOutService {

    @Autowired
    private FanNewsCharityOutMapper fanNewsCharityOutMapper;

    @Override
    public Page<FanNewsCharityOut> selectPage(Integer siteId, Integer status, Integer pageNo, Integer pageSize) {
        Wrapper<FanNewsCharityOut> entity = new EntityWrapper<FanNewsCharityOut>();
        entity.eq("show_id", siteId);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
        Page<FanNewsCharityOut> fanNewsCharityOutPage = this.selectPage(new Page<FanNewsCharityOut>(pageNo, pageSize), entity);
        return fanNewsCharityOutPage;
    }

    @Override
    public List<FanNewsCharityOut> selectPageList(Integer siteId, Integer status, Integer pageNo, Integer pageSize) {
        Wrapper<FanNewsCharityOut> entity = new EntityWrapper<FanNewsCharityOut>();
        entity.eq("show_id", siteId);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
        List<FanNewsCharityOut> fanNewsCharityOutList = fanNewsCharityOutMapper.selectPage(new Page<FanNewsCharityOut>(pageNo, pageSize), entity);
        return fanNewsCharityOutList;
    }

}
