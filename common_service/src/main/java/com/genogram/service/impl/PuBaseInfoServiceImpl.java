package com.genogram.service.impl;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.PuBaseInfo;
import com.genogram.mapper.PuBaseInfoMapper;
import com.genogram.service.IPuBaseInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yizx
 * @since 2019-01-16
 */
@Service
public class PuBaseInfoServiceImpl extends ServiceImpl<PuBaseInfoMapper, PuBaseInfo> implements IPuBaseInfoService {

    @Autowired
    IPuBaseInfoService puBaseInfoService;

    @Override
    public Boolean deletePuBaseInfoById(Integer id, int status, AllUserLogin userLogin) {
        PuBaseInfo puBaseInfo = this.selectById(id);
        if (StringsUtils.isEmpty(puBaseInfo)) {
            return null;
        }
        //修改状态
        puBaseInfo.setStatus(status);
        //修改人
        puBaseInfo.setUpdateUser(userLogin.getId());
        //修改时间
        puBaseInfo.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人待写
        boolean result = this.updateAllColumnById(puBaseInfo);
        return result;
    }
}
