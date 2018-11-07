package com.genogram.service.impl;

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

    @Override
    public List<DonorVo> getDonorVoPage(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
        Map map = new HashMap();
        map.put("showId", showId);
        map.put("status", status);
        map.put("pageNo", pageNo-1);
        map.put("pageSize", pageSize);

        return fanNewsCharityPayInMapper.getDonorVoPage(map);
    }
}
