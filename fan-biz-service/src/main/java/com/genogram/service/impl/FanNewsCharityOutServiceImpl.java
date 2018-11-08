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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Page<FanNewsCharityOut> getFanNewsCharityOutPage(Integer showId, Integer newsType,Integer status, Integer pageNo, Integer pageSize) {
        Wrapper<FanNewsCharityOut> entity = new EntityWrapper<FanNewsCharityOut>();
        entity.eq("show_id", showId);
        entity.eq("news_type", newsType);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
        Page<FanNewsCharityOut> fanNewsCharityOutPage = this.selectPage(new Page<FanNewsCharityOut>(pageNo, pageSize), entity);
        return fanNewsCharityOutPage;
    }

    @Override
    public Page<FanNewsCharityOutVo> getFanNewsCharityOutVoPage(Integer showId, Integer newsType, Integer status, Integer pageNo, Integer pageSize) {

        List<FanNewsCharityOutVo> fanNewsCharityOutVoList = new ArrayList<>();

        Wrapper<FanNewsCharityOut> entity = new EntityWrapper<FanNewsCharityOut>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.eq("news_type", newsType);
        entity.orderBy("create_time", false);

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
        fanNewsUploadFileWrapper.eq("show_id", showId);
        fanNewsUploadFileWrapper.eq("status", status);
        fanNewsUploadFileWrapper.in("news_id", list);

        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> fanNewsUploadFileList = fanNewsUploadFileMapper.selectList(fanNewsUploadFileWrapper);

        //遍历主表文章集合,赋值新对象vo

        fanNewsCharityOutList.forEach((fanNewsCharityOuts) -> {

            FanNewsCharityOutVo fanNewsCharityOutVo = new FanNewsCharityOutVo();

            fanNewsCharityOutVo.setId(fanNewsCharityOuts.getId());
            fanNewsCharityOutVo.setShowId(fanNewsCharityOuts.getShowId());
            fanNewsCharityOutVo.setNewsTitle(fanNewsCharityOuts.getNewsTitle());
            fanNewsCharityOutVo.setAmount(fanNewsCharityOuts.getAmount());
            fanNewsCharityOutVo.setUseFor(fanNewsCharityOuts.getUseFor());
            fanNewsCharityOutVo.setNewsText(fanNewsCharityOuts.getNewsText());
            fanNewsCharityOutVo.setVisitNum(fanNewsCharityOuts.getVisitNum());
            fanNewsCharityOutVo.setStatus(fanNewsCharityOuts.getStatus());
            fanNewsCharityOutVo.setCreateTime(fanNewsCharityOuts.getCreateTime());
            fanNewsCharityOutVo.setCreateUser(fanNewsCharityOuts.getCreateUser());
            fanNewsCharityOutVo.setUpdateTime(fanNewsCharityOuts.getUpdateTime());
            fanNewsCharityOutVo.setUpdateUser(fanNewsCharityOuts.getUpdateUser());

            List<FanNewsUploadFile> fanNewsUploadFile = new ArrayList<>();

            //判断改图片文章id是否一样
            fanNewsUploadFileList.forEach((data) -> {
                if (fanNewsCharityOuts.getId() == data.getNewsId()) {
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

        return mapPage;

    }

    @Override
    public NewsDetailVo getNewsCharityOutDetail(Integer id, Integer showId) {

        //根据Id查出文章详情
        FanNewsCharityOut fanNewsCharityOut = this.selectById(id);

        //查询图片
        Wrapper<FanNewsUploadFile> entityWrapper = new EntityWrapper<FanNewsUploadFile>();
        entityWrapper.eq("show_id", showId);
        entityWrapper.eq("news_id",id);

        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> fanNewsUploadFileList = fanNewsUploadFileMapper.selectList(entityWrapper);

        //查出对应的个人对象
        AllUserLogin allUserLogin = allUserLoginMapper.selectById(fanNewsCharityOut.getCreateUser());

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetail=new NewsDetailVo();
        newsDetail.setId(fanNewsCharityOut.getId());
        newsDetail.setShowId(fanNewsCharityOut.getShowId());
        newsDetail.setNewsTitle(fanNewsCharityOut.getNewsTitle());
        newsDetail.setNewsText(fanNewsCharityOut.getNewsText());
        newsDetail.setVisitNum(fanNewsCharityOut.getVisitNum());
        newsDetail.setStatus(fanNewsCharityOut.getStatus());
        newsDetail.setCreateTime(fanNewsCharityOut.getCreateTime());
        newsDetail.setCreateUser(fanNewsCharityOut.getCreateUser());
        newsDetail.setUpdateTime(fanNewsCharityOut.getUpdateTime());
        newsDetail.setUpdateUser(fanNewsCharityOut.getUpdateUser());

        //存储图片list集合
        newsDetail.setFanNewsUploadFileList(fanNewsUploadFileList);

        //存储作者名称
        newsDetail.setUserName(allUserLogin.getRealName());

        return newsDetail;
    }

}
