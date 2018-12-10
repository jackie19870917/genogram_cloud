package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsCultureNews;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.FanNewsCultureNewsMapper;
import com.genogram.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 联谊会-家族文化文章表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsCultureNewsServiceImpl extends ServiceImpl<FanNewsCultureNewsMapper, FanNewsCultureNews> implements IProFanNewsCultureNewsService {

    @Autowired
    private IProFanNewsUploadFileService fanNewsUploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IProSysRecommendService proSysRecommendService;

    /**
     * 联谊会家族文化详情查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:22
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public NewsDetailVo getFamilyCultureDetail(Integer id) {

        //根据Id查出文章详情
        FanNewsCultureNews fanNewsCultureNews = this.selectById(id);
        if (fanNewsCultureNews == null) {
            return null;
        }

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", fanNewsCultureNews.getShowId());
        //置顶封面  是否封面(0.否;1:是封面)
        uploadentity.eq("pic_index", 1);
        uploadentity.eq("news_id", id);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files = fanNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin updateUser = allUserLoginService.selectById(fanNewsCultureNews.getUpdateUser());
        AllUserLogin createUser = allUserLoginService.selectById(fanNewsCultureNews.getCreateUser());

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetail = new NewsDetailVo();

        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsCultureNews, newsDetail);
        //存储图片list集合
        newsDetail.setNewsUploadFileList(files);
        //存储作者名称时间
        newsDetail.setUpdateTimeLong(fanNewsCultureNews.getUpdateTime().getTime());
        newsDetail.setCreateTimeLong(fanNewsCultureNews.getCreateTime().getTime());
        newsDetail.setCreateUserName(null);
        newsDetail.setCreateUserName(null);
        return newsDetail;
    }

    /**
     * 联谊会家族文化前台增加查看数
     *
     * @Author: yuzhou
     * @Date: 2018-11-12
     * @Time: 13:49
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public void addVisitNum(Integer id) {
        //查出详情
        FanNewsCultureNews fanNewsCultureNews = this.selectById(id);
        //查看数加一
        Integer visitNum = fanNewsCultureNews.getVisitNum() + 1;
        fanNewsCultureNews.setVisitNum(visitNum);
        this.updateAllColumnById(fanNewsCultureNews);
        if (visitNum > Constants.PRO_VISIT_NUM || visitNum.equals(Constants.PRO_VISIT_NUM)) {
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status = 1;
            //来源:(1县级,2省级)
            int newsSource = 1;
            //要插入的实体类
            FanSysRecommend fanSysRecommend = new FanSysRecommend();
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setNewsSource(newsSource);
            fanSysRecommend.setShowId(fanNewsCultureNews.getShowId());
            fanSysRecommend.setNewsId(fanNewsCultureNews.getId());
            proSysRecommendService.addRecommend(fanSysRecommend);
        }
    }
}
