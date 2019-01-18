package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.PuBaseInfo;
import com.genogram.mapper.PuBaseInfoMapper;
import com.genogram.service.IPuBaseInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yizx
 * @since 2019-01-16
 */
@Service
public class PuBaseInfoServiceImpl extends ServiceImpl<PuBaseInfoMapper, PuBaseInfo> implements IPuBaseInfoService {

    @Autowired
    IPuBaseInfoService puBaseInfoService;

    /**
     *查询谱基本信息
     *@Author: yuzhou
     *@Date: 2019-01-18
     *@Time: 9:57
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Page<PuBaseInfo> getPuBaseInfoPage(List statusList, Integer pageNo, Integer pageSize,AllUserLogin userLogin) {
        //查询条件
        Wrapper<PuBaseInfo> entity = new EntityWrapper<PuBaseInfo>();
        entity.eq("create_user", userLogin.getId());
        if (statusList.size() != 0) {
            entity.in("status", statusList);
        }
        entity.orderBy("update_time", false);
        Page<PuBaseInfo> puBaseInfoPage = this.selectPage(new Page<>(pageNo, pageSize), entity);
        if(StringsUtils.isEmpty(puBaseInfoPage)){
            return null;
        }
        return puBaseInfoPage;
    }

    /**
     *删除谱书
     *@Author: yuzhou
     *@Date: 2019-01-18
     *@Time: 9:57
     *@Param:
     *@return:
     *@Description:
    */
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
