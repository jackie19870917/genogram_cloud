package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entity.ProNewsCultureNews;
import com.genogram.entity.ProNewsUploadFile;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.ProNewsCultureNewsMapper;
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
 * 省级-家族文化文章表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProNewsCultureNewsServiceImpl extends ServiceImpl<ProNewsCultureNewsMapper, ProNewsCultureNews> implements IProNewsCultureNewsService {

    @Autowired
    private IProNewsUploadFileService proNewsUploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IProSysRecommendService proSysRecommendService;

    @Autowired
    private IUploadFileService uploadFileService;

    /**
     *省级家族文化查询
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 13:53
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Page<FamilyCultureVo> getFamilyCulturePage(Wrapper<ProNewsCultureNews> entity, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<FamilyCultureVo> familyCultureVoList=new ArrayList<>();

        //分页查询文章主表
        Page<ProNewsCultureNews> fanNewsCultureNews =this.selectPage(new Page<ProNewsCultureNews>(pageNo, pageSize), entity);

        //得到文件当前页list集合
        List<ProNewsCultureNews> list = fanNewsCultureNews.getRecords();
        //判断改集合是否为空,如果是直接返回结果
        if(list.size()==0){
            return null;
        }

        //得到所有文章id
        List newsids =  new ArrayList<>();
        list.forEach(( news)->{
            newsids.add(news.getId());
        });

        //查询图片
        Wrapper<ProNewsUploadFile> uploadentity = new EntityWrapper<ProNewsUploadFile>();
        uploadentity.eq("show_id", list.get(0).getShowId());
        //  1 表示图片为显示状态
        uploadentity.eq("status", 1);
        //置顶封面
        uploadentity.eq("pic_index",1);
        uploadentity.in("news_id",newsids);
        //查询所有文章id下的图片附件
        List<ProNewsUploadFile> files =  proNewsUploadFileService.selectList(uploadentity);


        //遍历主表文章集合,赋值新对象vo
        list.forEach(( news)->{
            FamilyCultureVo familyCultureVo=new FamilyCultureVo();

            //存储新对象
            BeanUtils.copyProperties(news,familyCultureVo);

            //判断改图片文章id是否一样
            List<ProNewsUploadFile> proNewsUploadFile=new ArrayList<>();

            files.forEach(( data)->{
                if(news.getId().equals(data.getNewsId())){
                    proNewsUploadFile.add(data);
                }
            });

            //存储图片list集合
            familyCultureVo.setNewsUploadFileList(proNewsUploadFile);

            //转换时间为long
            familyCultureVo.setCreateTimeLong(news.getCreateTime().getTime());
            familyCultureVo.setUpdateTimeLong(news.getUpdateTime().getTime());

            //存储到新的集合中
            familyCultureVoList.add(familyCultureVo);
        });

        //重新设置page对象
        Page<FamilyCultureVo> mapPage = new Page<>(pageNo,pageSize);
        mapPage.setRecords(familyCultureVoList);
        mapPage.setSize(fanNewsCultureNews.getSize());
        mapPage.setTotal(fanNewsCultureNews.getTotal());

        return mapPage;
    }

    /**
     *省级家族文化详情查询
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 14:17
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public NewsDetailVo getFamilyCultureDetail(Integer id) {
        //根据Id查出文章详情
        ProNewsCultureNews proNewsCultureNews = this.selectById(id);
        if (proNewsCultureNews==null){
            return null;
        }

        //查询图片
        Wrapper<ProNewsUploadFile> uploadentity = new EntityWrapper<ProNewsUploadFile>();
        uploadentity.eq("show_id", proNewsCultureNews.getShowId());
        //置顶封面  是否封面(0.否;1:是封面)
        uploadentity.eq("pic_index",1);
        uploadentity.eq("news_id",id);
        //查询所有文章id下的图片附件
        List<ProNewsUploadFile> files =  proNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin updateUser = allUserLoginService.selectById(proNewsCultureNews.getUpdateUser());
        AllUserLogin createUser = allUserLoginService.selectById(proNewsCultureNews.getCreateUser());

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetail=new NewsDetailVo();

        //调用方法封装集合
        BeanUtils.copyProperties(proNewsCultureNews,newsDetail);
        //存储图片list集合
        newsDetail.setNewsUploadFileList(files);
        //存储作者名称时间
        newsDetail.setUpdateTimeLong(proNewsCultureNews.getUpdateTime().getTime());
        newsDetail.setCreateTimeLong(proNewsCultureNews.getCreateTime().getTime());
        newsDetail.setCreateUserName(null);
        newsDetail.setCreateUserName(null);
        return newsDetail;
    }

    /**
     *省级家族文化文章增加查看数
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 14:17
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public void addVisitNum(Integer id) {
        //查出详情
        ProNewsCultureNews proNewsCultureNews = this.selectById(id);
        //查看数加一
        Integer visitNum = proNewsCultureNews.getVisitNum()+1;
        proNewsCultureNews.setVisitNum(visitNum);
        this.updateAllColumnById(proNewsCultureNews);
        if(visitNum > Constants.PRO_VISIT_NUM || visitNum.equals(Constants.PRO_VISIT_NUM)){
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=2;
            //要插入的实体类
            FanSysRecommend fanSysRecommend=new FanSysRecommend();
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setNewsSource(newsSource);
            fanSysRecommend.setShowId(proNewsCultureNews.getShowId());
            fanSysRecommend.setNewsId(proNewsCultureNews.getId());
            proSysRecommendService.addRecommend(fanSysRecommend);
        }
    }

    /**
     *省级家族文化新增 修改
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 17:12
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public boolean addOrUpdateCulture(ProNewsCultureNews proNewsCultureNews, String fileName, String filePath) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if(proNewsCultureNews.getId()==null){
            //查看数 默认为0
            proNewsCultureNews.setVisitNum(0);
            //存入创建时间
            proNewsCultureNews.setCreateTime(format);
            proNewsCultureNews.setCreateUser(null);
            //存入修改时间
            proNewsCultureNews.setUpdateTime(format);
            proNewsCultureNews.setUpdateUser(null);
        }else{
            //存入修改时间
            proNewsCultureNews.setUpdateTime(format);
            proNewsCultureNews.setUpdateUser(null);
        }
        //插入数据
        boolean result = this.insertOrUpdate(proNewsCultureNews);
        //存储图片
        if(result && StringsUtils.isNotEmpty(filePath)){
            uploadFileService.storageFanFile(fileName,filePath,proNewsCultureNews.getId(),proNewsCultureNews.getShowId());
        }
        return result;
    }

    /**
     *省级家族文化后台删除
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 17:21
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean deleteCulturById(Integer id, int status) {
        ProNewsCultureNews proNewsCultureNews = this.selectById(id);
        proNewsCultureNews.setStatus(status);
        proNewsCultureNews.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人待写
        boolean result = this.updateAllColumnById(proNewsCultureNews);
        return result;
    }
}
