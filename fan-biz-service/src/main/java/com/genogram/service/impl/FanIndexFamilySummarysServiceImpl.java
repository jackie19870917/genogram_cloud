package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFamilySummarys;
import com.genogram.mapper.FanIndexFamilySummarysMapper;
import com.genogram.service.IFanIndexFamilySummarysService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 联谊会首页-联谊堂 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanIndexFamilySummarysServiceImpl extends ServiceImpl<FanIndexFamilySummarysMapper, FanIndexFamilySummarys> implements IFanIndexFamilySummarysService {

    @Override
    public Page<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(Integer siteId, List list, Integer pageNo, Integer pageSize) {

        Wrapper<FanIndexFamilySummarys> entity = new EntityWrapper<>();
        entity.eq("site_id", siteId);
        entity.in("status", list);
        entity.orderBy("update_time", false);

        return this.selectPage(new Page<>(pageNo, pageSize), entity);

    }

    @Override
    public Boolean insertOrUpdateFanIndexFamilySummarys(FanIndexFamilySummarys fanIndexFamilySummarys) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        if (fanIndexFamilySummarys.getId() != null) {
            fanIndexFamilySummarys.setCreateTime(timeStamp);
        }

        fanIndexFamilySummarys.setUpdateTime(timeStamp);

        return  this.insertOrUpdate(fanIndexFamilySummarys);
    }

    @Override
    public FanIndexFamilySummarys getFanIndexFamilySummarys(Integer id) {

        FanIndexFamilySummarys fanIndexFamilySummarys = this.selectById(id);

        if (StringUtils.isEmpty(fanIndexFamilySummarys)) {
            return null;
        } else {
            return fanIndexFamilySummarys;
        }
    }

    @Override
    public Boolean deleteFanIndexFamilySummarys(Integer id,Integer userId) {

        FanIndexFamilySummarys fanIndexFamilySummarys = new FanIndexFamilySummarys();

        fanIndexFamilySummarys.setId(id);
        fanIndexFamilySummarys.setStatus(0);
        fanIndexFamilySummarys.setUpdateUser(userId);
        fanIndexFamilySummarys.setUpdateTime(DateUtil.getCurrentTimeStamp());

        return this.updateById(fanIndexFamilySummarys);
    }
}
