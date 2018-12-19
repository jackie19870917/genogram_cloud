package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entityvo.NewsCharityOutVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.FanNewsCharityOutMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IFanNewsCharityOutService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IFanNewsUploadFileService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
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
    private IFanNewsUploadFileService fanNewsUploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IUploadFileService uploadFileService;

    @Override
    public Page<FanNewsCharityOut> getFanNewsCharityOutPage(Integer showId, Integer newsType, List status, Integer pageNo, Integer pageSize) {
        Wrapper<FanNewsCharityOut> entity = new EntityWrapper<FanNewsCharityOut>();
        entity.eq("show_id", showId);
        entity.eq("news_type", newsType);
        entity.in("status", status);
        entity.orderBy("create_time", false);
        Page<FanNewsCharityOut> fanNewsCharityOutPage = this.selectPage(new Page<>(pageNo, pageSize), entity);
        return fanNewsCharityOutPage;
    }

    @Override
    public Page<NewsCharityOutVo> getFanNewsCharityOutVoPage(Wrapper<FanNewsCharityOut> entity, Integer pageNo, Integer pageSize) {

        List<NewsCharityOutVo> newsCharityOutVoList = new ArrayList<>();

        //得到文件当前页list集合
        Page<FanNewsCharityOut> fanNewsCharityOutPage = this.selectPage(new Page<>(pageNo, pageSize), entity);

        List<FanNewsCharityOut> fanNewsCharityOutList = fanNewsCharityOutPage.getRecords();

        if (fanNewsCharityOutList.size() == 0) {
            return null;
        }

        List list = new ArrayList<>();
        fanNewsCharityOutList.forEach((fanNewsCharityOuts) -> {
            list.add(fanNewsCharityOuts.getId());

            //去掉文章标签
            fanNewsCharityOuts.setNewsText(StringsUtils.removeTag(fanNewsCharityOuts.getNewsText()));
        });

        //查询图片
        Wrapper<FanNewsUploadFile> fanNewsUploadFileWrapper = new EntityWrapper<FanNewsUploadFile>();
        fanNewsUploadFileWrapper.eq("show_id", fanNewsCharityOutList.get(0).getShowId());
        fanNewsUploadFileWrapper.eq("status", 1);
        fanNewsUploadFileWrapper.eq("pic_index", 1);
        fanNewsUploadFileWrapper.in("news_id", list);

        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> fanNewsUploadFileList = fanNewsUploadFileService.selectList(fanNewsUploadFileWrapper);

        //遍历主表文章集合,赋值新对象vo

        fanNewsCharityOutList.forEach((fanNewsCharityOuts) -> {

            NewsCharityOutVo newsCharityOutVo = new NewsCharityOutVo();

            fanNewsCharityOuts.setNewsText(fanNewsCharityOuts.getNewsTitle().replaceAll("&nbsp;", ""));
            BeanUtils.copyProperties(fanNewsCharityOuts, newsCharityOutVo);

            List<FanNewsUploadFile> fanNewsUploadFile = new ArrayList<>();

            //判断改图片文章id是否一样
            fanNewsUploadFileList.forEach((data) -> {
                if (fanNewsCharityOuts.getId().equals(data.getNewsId())) {
                    fanNewsUploadFile.add(data);

                }
            });
            //存储图片list集合
            newsCharityOutVo.setNewsUploadFileList(fanNewsUploadFile);

            //存储到新的集合中
            newsCharityOutVoList.add(newsCharityOutVo);
        });

        Page<NewsCharityOutVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(newsCharityOutVoList);
        mapPage.setTotal(fanNewsCharityOutPage.getTotal());

        return mapPage;

    }

    @Override
    public NewsDetailVo getNewsCharityOutDetail(Integer id) {

        //根据Id查出文章详情
        FanNewsCharityOut fanNewsCharityOut = this.selectById(id);

        if (StringUtils.isEmpty(fanNewsCharityOut)) {
            return null;
        }

        //查询图片
        Wrapper<FanNewsUploadFile> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("news_id", id);
        entityWrapper.eq("pic_index", 1);
        entityWrapper.eq("show_id", fanNewsCharityOut.getShowId());

        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> fanNewsUploadFileList = fanNewsUploadFileService.selectList(entityWrapper);

        //查出对应的个人对象(创建人)
        AllUserLogin allUserLoginCreateUser = allUserLoginService.selectById(fanNewsCharityOut.getCreateUser());

        //查出对应的个人对象(修改人)
        AllUserLogin allUserLoginUpdateUser = allUserLoginService.selectById(fanNewsCharityOut.getUpdateUser());

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetailVo = new NewsDetailVo();

        BeanUtils.copyProperties(fanNewsCharityOut, newsDetailVo);

        //存储图片list集合
        newsDetailVo.setNewsUploadFileList(fanNewsUploadFileList);

        //存储创建人名称
        newsDetailVo.setCreateUserName(allUserLoginCreateUser.getNickName());

        //存储创建时间
        newsDetailVo.setCreateTimeLong(fanNewsCharityOut.getCreateTime().getTime());

        //存储修改人名称
        newsDetailVo.setUpdateUserName(allUserLoginUpdateUser.getNickName());

        //存储修改时间
        newsDetailVo.setUpdateTimeLong(fanNewsCharityOut.getUpdateTime().getTime());

        return newsDetailVo;
    }

    /**
     * @param fanNewsCharityOut 慈善收支
     * @param fileName
     * @param filePath
     * @return
     */
    @Override
    public Boolean insertOrUpdateFanNewsCharityOutVo(FanNewsCharityOut fanNewsCharityOut, String fileName, String filePath) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        fanNewsCharityOut.setUpdateTime(timeStamp);
        if (fanNewsCharityOut.getId() == null) {
            fanNewsCharityOut.setCreateTime(timeStamp);
        }

        Boolean result = this.insertOrUpdate(fanNewsCharityOut);

        if (result && StringsUtils.isNotEmpty(filePath)) {
            uploadFileService.storageFanFile(fileName, filePath, fanNewsCharityOut.getId(), fanNewsCharityOut.getShowId());
        }
        return result;

    }

    @Override
    public Boolean deleteFanNewsCharityOut(Integer id, Integer userId) {

        FanNewsCharityOut fanNewsCharityOut = new FanNewsCharityOut();

        fanNewsCharityOut.setId(id);
        fanNewsCharityOut.setStatus(0);
        fanNewsCharityOut.setUpdateUser(userId);
        fanNewsCharityOut.setUpdateTime(DateUtil.getCurrentTimeStamp());

        return this.updateById(fanNewsCharityOut);
    }

    @Override
    public Boolean insertVisitNum(Integer id) {

        FanNewsCharityOut fanNewsCharityOut = this.selectById(id);
        fanNewsCharityOut.setId(id);
        fanNewsCharityOut.setVisitNum(fanNewsCharityOut.getVisitNum() + 1);

        return this.updateById(fanNewsCharityOut);
    }

}
