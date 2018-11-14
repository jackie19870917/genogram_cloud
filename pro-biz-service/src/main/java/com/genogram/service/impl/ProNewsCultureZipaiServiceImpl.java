package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.entity.ProNewsCultureZipai;
import com.genogram.entityvo.NewsCultureZipaiVo;
import com.genogram.mapper.ProNewsCultureZipaiMapper;
import com.genogram.service.IFanNewsCultureZipaiService;
import com.genogram.service.IProNewsCultureZipaiService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 省级-家族文化-字派表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProNewsCultureZipaiServiceImpl extends ServiceImpl<ProNewsCultureZipaiMapper, ProNewsCultureZipai> implements IProNewsCultureZipaiService {

    @Autowired
    private IFanNewsCultureZipaiService fanNewsCultureZipaiService;

    /**
     *省级家族字派查询
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 9:44
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Page<ProNewsCultureZipai> commonality(Wrapper<ProNewsCultureZipai> entity, Integer pageNo, Integer pageSize) {
        Page<ProNewsCultureZipai> proNewsCultureZipaiPage = this.selectPage(new Page<ProNewsCultureZipai>(pageNo, pageSize), entity);
        return proNewsCultureZipaiPage;
    }

    /**
     *省级家族字派模糊查询
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 11:25
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Page<NewsCultureZipaiVo> getZipaiVaguePage(Wrapper<ProNewsCultureZipai> entity, Integer pageNo, Integer pageSize) {
        //查出省级字派表
        Page<ProNewsCultureZipai> proNewsCultureZipaiPage = this.selectPage(new Page<ProNewsCultureZipai>(pageNo, pageSize), entity);
        return null;
    }
}
