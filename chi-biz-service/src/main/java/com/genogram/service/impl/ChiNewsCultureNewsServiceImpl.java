package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiNewsCultureNews;
import com.genogram.entity.ChiNewsUploadFile;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.ChiNewsCultureNewsMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IChiNewsCultureNewsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IChiNewsUploadFileService;
import com.genogram.service.IUploadFileService;
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
 * 全国-家族文化文章表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-19
 */
@Service
public class ChiNewsCultureNewsServiceImpl extends ServiceImpl<ChiNewsCultureNewsMapper, ChiNewsCultureNews> implements IChiNewsCultureNewsService {

    @Autowired
    private IUploadFileService uploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IChiNewsUploadFileService chiNewsUploadFileService;

    /**
     *全国姓氏文化新增 修改
     *@Author: yuzhou
     *@Date: 2019-02-20
     *@Time: 9:54
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean addOrUpdateCultureNews(ChiNewsCultureNews chiNewsCultureNews, AllUserLogin userLogin,String fileName,String filePath) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if (StringsUtils.isEmpty(chiNewsCultureNews.getId())) {
            chiNewsCultureNews.setCreateUser(userLogin.getId());
            chiNewsCultureNews.setCreateTime(format);
        }
        chiNewsCultureNews.setUpdateUser(userLogin.getId());
        chiNewsCultureNews.setUpdateTime(format);
        boolean result = this.insertOrUpdate(chiNewsCultureNews);

        //插入图片
        if(result && StringsUtils.isNotEmpty(filePath)){
            uploadFileService.storageFanFile(fileName, filePath, chiNewsCultureNews.getId(), chiNewsCultureNews.getShowId());
        }
        return result;
    }

    /**
     *全国姓氏文化删除
     *@Author: yuzhou
     *@Date: 2019-02-20
     *@Time: 14:46
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean deleteCulturById(Integer id, int status, AllUserLogin userLogin) {
        ChiNewsCultureNews chiNewsCultureNews = this.selectById(id);
        if (StringsUtils.isEmpty(chiNewsCultureNews)) {
            return null;
        }
        //修改状态
        chiNewsCultureNews.setStatus(status);
        //修改人
        chiNewsCultureNews.setUpdateUser(userLogin.getId());
        //修改时间
        chiNewsCultureNews.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人待写
        boolean result = this.updateAllColumnById(chiNewsCultureNews);
        return result;
    }

    /**
     *全国姓氏文化详情
     *@Author: yuzhou
     *@Date: 2019-02-20
     *@Time: 15:02
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public NewsDetailVo getFamilyCultureDetail(Integer id) {
        //根据Id查出文章详情
        ChiNewsCultureNews chiNewsCultureNews = this.selectById(id);
        if (StringsUtils.isEmpty(chiNewsCultureNews)) {
            return null;
        }

        //查询图片
        Wrapper<ChiNewsUploadFile> uploadentity = new EntityWrapper<ChiNewsUploadFile>();
        uploadentity.eq("show_id", chiNewsCultureNews.getShowId());
        //置顶封面  是否封面(0.否;1:是封面)
        uploadentity.eq("pic_index", 1);
        uploadentity.eq("news_id", id);
        //查询所有文章id下的图片附件
        List<ChiNewsUploadFile> files = chiNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin updateUser = allUserLoginService.selectById(chiNewsCultureNews.getUpdateUser());
        AllUserLogin createUser = allUserLoginService.selectById(chiNewsCultureNews.getCreateUser());

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetail = new NewsDetailVo();

        //调用方法封装集合
        BeanUtils.copyProperties(chiNewsCultureNews, newsDetail);
        //存储图片list集合
        newsDetail.setNewsUploadFileList(files);
        //存储作者名称时间
        newsDetail.setUpdateTimeLong(chiNewsCultureNews.getUpdateTime().getTime());
        newsDetail.setCreateTimeLong(chiNewsCultureNews.getCreateTime().getTime());
        newsDetail.setCreateUserName(createUser.getNickName());
        newsDetail.setCreateUserName(updateUser.getNickName());
        return newsDetail;
    }

    /**
     *全国姓氏文化后台查询
     *@Author: yuzhou
     *@Date: 2019-02-20
     *@Time: 15:31
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Page<FamilyCultureVo> getFamilyCulturePage(Wrapper<ChiNewsCultureNews> entity, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<FamilyCultureVo> familyCultureVoList = new ArrayList<>();

        //分页查询文章主表
        Page<ChiNewsCultureNews> fanNewsCultureNews = this.selectPage(new Page<ChiNewsCultureNews>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<ChiNewsCultureNews> list = fanNewsCultureNews.getRecords();
        //判断改集合是否为空,如果是直接返回结果
        if (list.size() == 0) {
            return null;
        }

        //得到所有文章id
        List newsids = new ArrayList<>();
        list.forEach((news) -> {
            newsids.add(news.getId());
            //去掉空格
            news.setNewsText(news.getNewsText().replaceAll("&nbsp;", ""));
            //去掉文章标签
            news.setNewsText(StringsUtils.removeTag(news.getNewsText()));
        });

        //查询图片
        Wrapper<ChiNewsUploadFile> uploadentity = new EntityWrapper<ChiNewsUploadFile>();
        uploadentity.eq("show_id", list.get(0).getShowId());
        //  1 表示图片为显示状态
        uploadentity.eq("status", 1);
        //置顶封面
        uploadentity.eq("pic_index", 1);
        uploadentity.in("news_id", newsids);
        //查询所有文章id下的图片附件
        List<ChiNewsUploadFile> files = chiNewsUploadFileService.selectList(uploadentity);


        //遍历主表文章集合,赋值新对象vo
        list.forEach((news) -> {
            FamilyCultureVo familyCultureVo = new FamilyCultureVo();

            //存储新对象
            BeanUtils.copyProperties(news, familyCultureVo);

            //去除html标签
            familyCultureVo.setNewsText(StringsUtils.removeTag(familyCultureVo.getNewsText()));

            //判断改图片文章id是否一样
            List<ChiNewsUploadFile> fanNewsUploadFile = new ArrayList<>();

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
}
