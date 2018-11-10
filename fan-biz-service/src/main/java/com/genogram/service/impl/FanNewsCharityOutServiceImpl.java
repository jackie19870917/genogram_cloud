package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entityvo.FanNewsCharityOutVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.mapper.FanNewsCharityOutMapper;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.IFanNewsCharityOutService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private FanNewsUploadFileMapper fanNewsUploadFileMapper;

    @Autowired
    private AllUserLoginMapper allUserLoginMapper;

    @Override
    public Page<FanNewsCharityOut> getFanNewsCharityOutPage(Integer showId, Integer newsType, List status, Integer pageNo, Integer pageSize) {
        Wrapper<FanNewsCharityOut> entity = new EntityWrapper<FanNewsCharityOut>();
        entity.eq("show_id", showId);
        entity.eq("news_type", newsType);
        entity.in("status", status);
        entity.orderBy("create_time", false);
        Page<FanNewsCharityOut> fanNewsCharityOutPage = this.selectPage(new Page<FanNewsCharityOut>(pageNo, pageSize), entity);
        return fanNewsCharityOutPage;
    }

    @Override
    public Page<FanNewsCharityOutVo> getFanNewsCharityOutVoPage(Wrapper<FanNewsCharityOut> entity, Integer pageNo, Integer pageSize) {

        List<FanNewsCharityOutVo> fanNewsCharityOutVoList = new ArrayList<>();

        //得到文件当前页list集合
        Page<FanNewsCharityOut> fanNewsCharityOutPage = this.selectPage(new Page<FanNewsCharityOut>(pageNo, pageSize), entity);

        List<FanNewsCharityOut> fanNewsCharityOutList = fanNewsCharityOutPage.getRecords();

        if (fanNewsCharityOutList.isEmpty() && fanNewsCharityOutList == null) {
            return null;
        }

        List list = new ArrayList<>();
        fanNewsCharityOutList.forEach((fanNewsCharityOuts) -> {
            list.add(fanNewsCharityOuts.getId());
        });

        //查询图片
        Wrapper<FanNewsUploadFile> fanNewsUploadFileWrapper = new EntityWrapper<FanNewsUploadFile>();
        fanNewsUploadFileWrapper.eq("show_id", fanNewsCharityOutList.get(0).getShowId());
        fanNewsUploadFileWrapper.eq("status", 1);
        fanNewsUploadFileWrapper.in("news_id", list);

        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> fanNewsUploadFileList = fanNewsUploadFileMapper.selectList(fanNewsUploadFileWrapper);

        //遍历主表文章集合,赋值新对象vo

        fanNewsCharityOutList.forEach((fanNewsCharityOuts) -> {

            FanNewsCharityOutVo fanNewsCharityOutVo = new FanNewsCharityOutVo();

            BeanUtils.copyProperties(fanNewsCharityOuts,fanNewsCharityOutVo);

            List<FanNewsUploadFile> fanNewsUploadFile = new ArrayList<>();

            //判断改图片文章id是否一样
            fanNewsUploadFileList.forEach((data) -> {
                if (fanNewsCharityOuts.getId() .equals(data.getId())) {
                    fanNewsUploadFile.add(data);

                }
            });
            //存储图片list集合
            fanNewsCharityOutVo.setFanNewsUploadFileList(fanNewsUploadFile);

            //存储到新的集合中
            fanNewsCharityOutVoList.add(fanNewsCharityOutVo);
        });

        Page<FanNewsCharityOutVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(fanNewsCharityOutVoList);
        mapPage.setTotal(fanNewsCharityOutPage.getTotal());

        return mapPage;

    }

    @Override
    public NewsDetailVo getNewsCharityOutDetail(Integer id) {

        //根据Id查出文章详情
        FanNewsCharityOut fanNewsCharityOut = this.selectById(id);

        //查询图片
        Wrapper<FanNewsUploadFile> entityWrapper = new EntityWrapper<FanNewsUploadFile>();
        entityWrapper.eq("news_id", id);
        entityWrapper.eq("show_id", fanNewsCharityOut.getShowId());

        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> fanNewsUploadFileList = fanNewsUploadFileMapper.selectList(entityWrapper);

        //查出对应的个人对象(创建人)
        AllUserLogin allUserLoginCreateUser = allUserLoginMapper.selectById(fanNewsCharityOut.getCreateUser());

        //查出对应的个人对象(修改人)
        AllUserLogin allUserLoginUpdateUser = allUserLoginMapper.selectById(fanNewsCharityOut.getUpdateUser());

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetailVo = new NewsDetailVo();

        BeanUtils.copyProperties(fanNewsCharityOut,newsDetailVo);

        //存储图片list集合
        newsDetailVo.setFanNewsUploadFileList(fanNewsUploadFileList);

        //存储创建人名称
        newsDetailVo.setCreateUserName(allUserLoginCreateUser.getUserName());

        //存储创建时间
        newsDetailVo.setCreateTimeLong(fanNewsCharityOut.getCreateTime().getTime());

        //存储修改人名称
        newsDetailVo.setUpdateUserName(allUserLoginUpdateUser.getUserName());

        //存储修改时间
        newsDetailVo.setUpdateTimeLong(fanNewsCharityOut.getUpdateTime().getTime());

        return newsDetailVo;
    }

    /***
     *
     * @param fanNewsCharityOut   慈善收支
     * @param fanNewsUploadFileList
     * @return
     */
    @Override
    public Boolean insertOrUpdateFanNewsCharityOutVo(FanNewsCharityOut fanNewsCharityOut, List<FanNewsUploadFile> fanNewsUploadFileList) {

        if (fanNewsCharityOut.getId() == null) {
            Date date = new Date();
            fanNewsCharityOut.setUpdateTime(date);
            boolean b = this.insertOrUpdate(fanNewsCharityOut);


            for (FanNewsUploadFile fanNewsUploadFile : fanNewsUploadFileList) {
                fanNewsUploadFile.setUpdateTime(date);
                fanNewsUploadFileMapper.updateById(fanNewsUploadFile);
            }
        } else {
            Date date = new Date();
            fanNewsCharityOut.setCreateTime(date);
            this.insertOrUpdate(fanNewsCharityOut);


            for (FanNewsUploadFile fanNewsUploadFile : fanNewsUploadFileList) {
                fanNewsUploadFile.setCreateTime(date);
                fanNewsUploadFileMapper.insert(fanNewsUploadFile);
            }
        }
        return null;
    }

}
