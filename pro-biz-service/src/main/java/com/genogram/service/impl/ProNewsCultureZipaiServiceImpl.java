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
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    public Page<NewsCultureZipaiVo> getZipaiVaguePage(Wrapper<ProNewsCultureZipai> entity, Integer pageNo, Integer pageSize,Integer showId,String zipaiTxt) {

        //返回新VO的集合
        List<NewsCultureZipaiVo> familyCultureVoList=new ArrayList<>();

        //查出省级字派表
        Page<ProNewsCultureZipai> proNewsCultureZipaiPage = this.selectPage(new Page<ProNewsCultureZipai>(pageNo, pageSize), entity);

        List<ProNewsCultureZipai> records = proNewsCultureZipaiPage.getRecords();


        records.forEach(( news)->{
            NewsCultureZipaiVo newsCultureZipaiVo =new NewsCultureZipaiVo();
            //调用方法封装集合
            BeanUtils.copyProperties(news,newsCultureZipaiVo);
            familyCultureVoList.add(newsCultureZipaiVo);
        });

        //状态(0:删除;1:已发布;2:草稿3:不显示)
        List statusList = new ArrayList();
        statusList.add(1);
        Wrapper<FanNewsCultureZipai> fanNewsEntity = new EntityWrapper<FanNewsCultureZipai>();
        entity.eq("show_id",showId);
        entity.in("status",statusList);
        entity.like("zipai_txt",zipaiTxt);
        //查出县级字派表
        List<FanNewsCultureZipai> list = fanNewsCultureZipaiService.selectList(fanNewsEntity);

        list.forEach(( news)->{
            NewsCultureZipaiVo newsCultureZipaiVo =new NewsCultureZipaiVo();
            //调用方法封装集合
            BeanUtils.copyProperties(news,newsCultureZipaiVo);
            familyCultureVoList.add(newsCultureZipaiVo);
        });

        //重新设置page对象
        Page<NewsCultureZipaiVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(familyCultureVoList);
        mapPage.setSize(proNewsCultureZipaiPage.getSize());
        mapPage.setTotal(proNewsCultureZipaiPage.getTotal());
        return mapPage;
    }

    /**
     *省级字派进入后台页面
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 16:16
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public ProNewsCultureZipai getZiPaiDetail(Integer id) {
        ProNewsCultureZipai proNewsCultureZipai = this.selectById(id);
        return proNewsCultureZipai;
    }

    /**
     *省级字派后台新增
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 16:36
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public boolean addOrUpdateZiPai(ProNewsCultureZipai proNewsCultureZipai) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if(proNewsCultureZipai.getId()==null){
            //存入创建时间
            proNewsCultureZipai.setCreateUser(null);
            proNewsCultureZipai.setCreateTime(format);
            //存入修改时间
            proNewsCultureZipai.setUpdateTime(format);
            proNewsCultureZipai.setUpdateUser(null);
        }else{
            //存入修改时间
            proNewsCultureZipai.setUpdateTime(format);
            proNewsCultureZipai.setUpdateUser(null);
        }
        return this.insertOrUpdate(proNewsCultureZipai);
    }

    /**
     *省级字派后台删除
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 16:51
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean deleteZipaiById(Integer id, int status) {
        ProNewsCultureZipai proNewsCultureZipai = this.selectById(id);
        proNewsCultureZipai.setStatus(status);
        proNewsCultureZipai.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人  待写
        return this.updateAllColumnById(proNewsCultureZipai);
    }
}
