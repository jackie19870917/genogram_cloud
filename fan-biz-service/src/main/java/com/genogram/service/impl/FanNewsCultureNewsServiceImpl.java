package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.FanNewsCultureNewsMapper;
import com.genogram.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
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
public class FanNewsCultureNewsServiceImpl extends ServiceImpl<FanNewsCultureNewsMapper, FanNewsCultureNews> implements IFanNewsCultureNewsService {

    @Autowired
    private IFanNewsUploadFileService fanNewsUploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IUploadFileService uploadFileService;

    @Autowired
    private IFanSysRecommendService fanSysRecommendService;

    @Autowired
    private IFanSysWebNewsShowService fanSysWebNewsShowService;

    @Override
    public Page<FamilyCultureVo> getFamilyCulturePage(Wrapper<FanNewsCultureNews> entity, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<FamilyCultureVo> familyCultureVoList = new ArrayList<>();

        //分页查询文章主表
        Page<FanNewsCultureNews> fanNewsCultureNews = this.selectPage(new Page<FanNewsCultureNews>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<FanNewsCultureNews> list = fanNewsCultureNews.getRecords();
        //判断改集合是否为空,如果是直接返回结果
        if (list.size() == 0) {
            return null;
        }

        //得到所有文章id
        List newsids = new ArrayList<>();
        list.forEach((news) -> {
            newsids.add(news.getId());
            //去掉空格
            news.setNewsText(news.getNewsText().replaceAll("&nbsp;",""));
            //去掉文章标签
            news.setNewsText(StringsUtils.removeTag(news.getNewsText()));
        });

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", list.get(0).getShowId());
        //  1 表示图片为显示状态
        uploadentity.eq("status", 1);
        //置顶封面
        uploadentity.eq("pic_index", 1);
        uploadentity.in("news_id", newsids);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files = fanNewsUploadFileService.selectList(uploadentity);


        //遍历主表文章集合,赋值新对象vo
        list.forEach((news) -> {
            FamilyCultureVo familyCultureVo = new FamilyCultureVo();

            //存储新对象
            BeanUtils.copyProperties(news, familyCultureVo);

            //去除html标签
            familyCultureVo.setNewsText(StringsUtils.removeTag(familyCultureVo.getNewsText()));

            //判断改图片文章id是否一样
            List<FanNewsUploadFile> fanNewsUploadFile = new ArrayList<>();

            files.forEach((data) -> {
                if (news.getId().equals(data.getNewsId())) {
                    fanNewsUploadFile.add(data);
                }
            });

            //存储图片list集合
            familyCultureVo.setNewsUploadFileList(fanNewsUploadFile);

            //转换时间为long
            familyCultureVo.setCreateTimeLong(news.getCreateTime().getTime());
            familyCultureVo.setUpdateTimeLong(news.getUpdateTime().getTime());

            //存储到新的集合中
            familyCultureVoList.add(familyCultureVo);
        });

        //重新设置page对象
        Page<FamilyCultureVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(familyCultureVoList);
        mapPage.setSize(fanNewsCultureNews.getSize());
        mapPage.setTotal(fanNewsCultureNews.getTotal());

        return mapPage;
    }

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
        newsDetail.setCreateUserName(createUser.getNickName());
        newsDetail.setCreateUserName(updateUser.getNickName());
        return newsDetail;
    }

    /**
     * 家族文化后台添加
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:23
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public boolean addOrUpdateCulture(FanNewsCultureNews fanNewsCultureNews, String fileName, String filePath) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if (fanNewsCultureNews.getId() == null) {
            //查看数 默认为0
            fanNewsCultureNews.setVisitNum(0);
            //存入创建时间
            fanNewsCultureNews.setCreateTime(format);
            //存入修改时间
            fanNewsCultureNews.setUpdateTime(format);
        } else {
            //存入修改时间
            fanNewsCultureNews.setUpdateTime(format);
        }
        //插入数据
        boolean result = this.insertOrUpdate(fanNewsCultureNews);
        //存储图片
        if (result && StringsUtils.isNotEmpty(filePath)) {
            uploadFileService.storageFanFile(fileName, filePath, fanNewsCultureNews.getId(), fanNewsCultureNews.getShowId());
        }
        return result;
    }

    /**
     * 联谊会家族文化后台删除
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 11:58
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean deleteCulturById(Integer id, int status, AllUserLogin userLoginInfoByToken) {
        FanNewsCultureNews fanNewsCultureNews = this.selectById(id);
        if (StringsUtils.isEmpty(fanNewsCultureNews)) {
            return null;
        }
        //修改状态
        fanNewsCultureNews.setStatus(status);
        //修改人
        fanNewsCultureNews.setUpdateUser(userLoginInfoByToken.getId());
        //修改时间
        fanNewsCultureNews.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人待写
        boolean result = this.updateAllColumnById(fanNewsCultureNews);
        return result;
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
        if (visitNum > Constants.FAN_VISIT_NUM || visitNum.equals(Constants.FAN_VISIT_NUM)) {
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status = 2;
            //来源:(1县级,2省级)
            int newsSource = 1;
            //是否自动推荐(0:否;1:是)
            int isAuto = 1;
            //要插入的实体类
            FanSysRecommend fanSysRecommend = new FanSysRecommend();
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setStatus(isAuto);
            fanSysRecommend.setNewsSource(newsSource);
            fanSysRecommend.setShowId(fanNewsCultureNews.getShowId());
            fanSysRecommend.setNewsId(fanNewsCultureNews.getId());
            fanSysRecommendService.addRecommend(fanSysRecommend);
        }
    }

    /**
     * 联谊会首页家族文化文章查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-30
     * @Time: 14:50
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Page<FamilyCultureVo> getFamilyIndexCulturePage(Integer siteId, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<FamilyCultureVo> familyCultureVoList = new ArrayList<>();
        //查询家族文化菜单Id
        Wrapper<FanSysWebNewsShow> entity = new EntityWrapper();
        entity.eq("site_id", siteId);
        entity.eq("menu_code", "culture");
        FanSysWebNewsShow fanSysWebNewsShow = fanSysWebNewsShowService.selectOne(entity);
        //查询家族文化分类各个showId
        Wrapper<FanSysWebNewsShow> entityShow = new EntityWrapper();
        entityShow.eq("site_id", siteId);
        entityShow.eq("parent_id", fanSysWebNewsShow.getMenuId());
        List<FanSysWebNewsShow> fanSysWebNewsShows = fanSysWebNewsShowService.selectList(entityShow);
        //获取家族文化各个分类的showId
        List<Integer> list = new ArrayList<>();
        for (FanSysWebNewsShow sysWebNewsShow : fanSysWebNewsShows) {
            list.add(sysWebNewsShow.getShowId());
        }
        //查询家族文化各个分类的文章
        Wrapper<FanNewsCultureNews> entityCulture = new EntityWrapper();
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        entityCulture.eq("status", 1);
        entityCulture.in("show_id", list);
        entityCulture.orderBy("update_time", false);
        Page<FanNewsCultureNews> fanNewsCultureNewsPage = this.selectPage(new Page<>(pageNo, pageSize), entityCulture);
        //得到文件当前页list集合
        List<FanNewsCultureNews> listCulture = fanNewsCultureNewsPage.getRecords();
        //判断改集合是否为空,如果是直接返回结果
        if (listCulture.size() == 0) {
            return null;
        }
        //得到所有文章id
        List newsids = new ArrayList<>();
        listCulture.forEach((news) -> {
            newsids.add(news.getId());
            //去掉文章标签
            news.setNewsText(StringsUtils.removeTag(news.getNewsText()));
        });
        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.in("show_id", list);
        //  1 表示图片为显示状态
        uploadentity.eq("status", 1);
        //置顶封面
        uploadentity.eq("pic_index", 1);
        uploadentity.in("news_id", newsids);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files = fanNewsUploadFileService.selectList(uploadentity);
        //遍历主表文章集合,赋值新对象vo
        listCulture.forEach((news) -> {
            FamilyCultureVo familyCultureVo = new FamilyCultureVo();
            //存储新对象
            BeanUtils.copyProperties(news, familyCultureVo);
            //去除html标签
            familyCultureVo.setNewsText(StringsUtils.removeTag(familyCultureVo.getNewsText()));
            //判断改图片文章id是否一样
            List<FanNewsUploadFile> fanNewsUploadFile = new ArrayList<>();
            files.forEach((data) -> {
                if (news.getId().equals(data.getNewsId())) {
                    fanNewsUploadFile.add(data);
                }
            });
            //存储图片list集合
            familyCultureVo.setNewsUploadFileList(fanNewsUploadFile);
            //转换时间为long
            familyCultureVo.setCreateTimeLong(news.getCreateTime().getTime());
            familyCultureVo.setUpdateTimeLong(news.getUpdateTime().getTime());
            //存储到新的集合中
            familyCultureVoList.add(familyCultureVo);
        });
        //重新设置page对象
        Page<FamilyCultureVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(familyCultureVoList);
        mapPage.setSize(fanNewsCultureNewsPage.getSize());
        mapPage.setTotal(fanNewsCultureNewsPage.getTotal());
        return mapPage;
    }

    /**
     * 联谊会家族文化后台置顶
     *
     * @Author: yuzhou
     * @Date: 2018-12-15
     * @Time: 9:42
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean culturStick(Integer id, AllUserLogin userLogin) {
        FanNewsCultureNews fanNewsCultureNews = this.selectById(id);
        if (StringsUtils.isEmpty(fanNewsCultureNews)) {
            return null;
        }
        //修改人
        fanNewsCultureNews.setUpdateUser(userLogin.getId());
        //修改时间
        fanNewsCultureNews.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人待写
        boolean result = this.updateAllColumnById(fanNewsCultureNews);
        return result;
    }
}
