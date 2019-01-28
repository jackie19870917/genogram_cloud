package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ProNewsCharityOut;
import com.genogram.entity.ProNewsUploadFile;
import com.genogram.entityvo.NewsCharityOutVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.mapper.ProNewsCharityOutMapper;
import com.genogram.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 省级-家族慈善财务收入支出(文章)表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@Service
public class ProNewsCharityOutServiceImpl extends ServiceImpl<ProNewsCharityOutMapper, ProNewsCharityOut> implements IProNewsCharityOutService {

    @Autowired
    private IProNewsUploadFileService proNewsUploadFileService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IUploadFileService uploadFileService;

    @Override
    public Page<ProNewsCharityOut> getProNewsCharityOutPage(Integer showId, Integer newsType, List status, Integer pageNo, Integer pageSize) {
        Wrapper<ProNewsCharityOut> entity = new EntityWrapper<ProNewsCharityOut>();
        entity.eq("show_id", showId);
        entity.eq("news_type", newsType);
        entity.in("status", status);
        entity.orderBy("create_time", false);
        Page<ProNewsCharityOut> proNewsCharityOutPage = this.selectPage(new Page<ProNewsCharityOut>(pageNo, pageSize), entity);
        return proNewsCharityOutPage;
    }

    @Override
    public Page<NewsCharityOutVo> getNewsCharityOutVoPage(Wrapper<ProNewsCharityOut> entity, Integer pageNo, Integer pageSize) {

        List<NewsCharityOutVo> newsCharityOutVoList = new ArrayList<>();

        //得到文件当前页list集合
        Page<ProNewsCharityOut> proNewsCharityOutPage = this.selectPage(new Page<ProNewsCharityOut>(pageNo, pageSize), entity);

        List<ProNewsCharityOut> proNewsCharityOutList = proNewsCharityOutPage.getRecords();

        if (proNewsCharityOutList.size() == 0) {
            return null;
        }

        List list = new ArrayList<>();
        proNewsCharityOutList.forEach((proNewsCharityOut) -> {
            list.add(proNewsCharityOut.getId());
        });

        //查询图片
        Wrapper<ProNewsUploadFile> proNewsUploadFileWrapper = new EntityWrapper<ProNewsUploadFile>();
        proNewsUploadFileWrapper.eq("show_id", proNewsCharityOutList.get(0).getShowId());
        proNewsUploadFileWrapper.eq("status", 1);
        proNewsUploadFileWrapper.eq("pic_index", 1);
        proNewsUploadFileWrapper.in("news_id", list);

        //查询所有文章id下的图片附件
        List<ProNewsUploadFile> proNewsUploadFileList = proNewsUploadFileService.selectList(proNewsUploadFileWrapper);

        //遍历主表文章集合,赋值新对象vo

        proNewsCharityOutList.forEach((proNewsCharityOut) -> {

            NewsCharityOutVo newsCharityOutVo = new NewsCharityOutVo();

            BeanUtils.copyProperties(proNewsCharityOut, newsCharityOutVo);

            List<ProNewsUploadFile> proNewsUploadFiles = new ArrayList<>();

            //判断改图片文章id是否一样
            proNewsUploadFileList.forEach((data) -> {
                if (proNewsCharityOut.getId().equals(data.getNewsId())) {
                    proNewsUploadFiles.add(data);
                }
            });
            //存储图片list集合
            newsCharityOutVo.setNewsUploadFileList(proNewsUploadFiles);

            //存储到新的集合中
            newsCharityOutVoList.add(newsCharityOutVo);
        });

        Page<NewsCharityOutVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(newsCharityOutVoList);
        mapPage.setTotal(proNewsCharityOutPage.getTotal());

        return mapPage;

    }

    @Override
    public NewsDetailVo getNewsCharityOutDetail(Integer id) {

        //根据Id查出文章详情
        ProNewsCharityOut proNewsCharityOut = this.selectById(id);

        if (StringUtils.isEmpty(proNewsCharityOut)) {
            return null;
        }

        //查询图片
        Wrapper<ProNewsUploadFile> entityWrapper = new EntityWrapper<ProNewsUploadFile>();
        entityWrapper.eq("news_id", id);
        entityWrapper.eq("pic_index", 1);
        entityWrapper.eq("show_id", proNewsCharityOut.getShowId());

        //查询所有文章id下的图片附件
        List<ProNewsUploadFile> proNewsUploadFileList = proNewsUploadFileService.selectList(entityWrapper);

        //查出对应的个人对象(创建人)
        AllUserLogin allUserLoginCreateUser = allUserLoginService.selectById(proNewsCharityOut.getCreateUser());

        //查出对应的个人对象(修改人)
        AllUserLogin allUserLoginUpdateUser = allUserLoginService.selectById(proNewsCharityOut.getUpdateUser());

        //返回新VO的集合赋值新对象vo
        NewsDetailVo newsDetailVo = new NewsDetailVo();

        BeanUtils.copyProperties(proNewsCharityOut, newsDetailVo);

        //存储图片list集合
        newsDetailVo.setNewsUploadFileList(proNewsUploadFileList);

        //存储创建人名称
        newsDetailVo.setCreateUserName(allUserLoginCreateUser.getUserName());

        //存储创建时间
        newsDetailVo.setCreateTimeLong(proNewsCharityOut.getCreateTime().getTime());

        //存储修改人名称
        newsDetailVo.setUpdateUserName(allUserLoginUpdateUser.getUserName());

        //存储修改时间
        newsDetailVo.setUpdateTimeLong(proNewsCharityOut.getUpdateTime().getTime());

        return newsDetailVo;
    }

    /***
     *
     * @param proNewsCharityOut   慈善收支
     * @param fileName
     * @param filePath
     * @return
     */
    @Override
    public Boolean insertOrUpdateProNewsCharityOutVo(ProNewsCharityOut proNewsCharityOut, String fileName, String filePath) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        proNewsCharityOut.setUpdateTime(timeStamp);
        if (proNewsCharityOut.getId() == null) {
            proNewsCharityOut.setCreateTime(timeStamp);
            proNewsCharityOut.setVisitNum(0);
        }

        Boolean result = this.insertOrUpdate(proNewsCharityOut);

        if (result) {
            result = uploadFileService.storageFanFile(fileName, filePath, proNewsCharityOut.getId(), proNewsCharityOut.getShowId());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean deleteProNewsCharityOut(Integer id, Integer userId) {

        ProNewsCharityOut proNewsCharityOut = new ProNewsCharityOut();

        proNewsCharityOut.setId(id);
        proNewsCharityOut.setUpdateUser(userId);
        proNewsCharityOut.setStatus(0);
        proNewsCharityOut.setUpdateTime(DateUtil.getCurrentTimeStamp());

        return this.updateById(proNewsCharityOut);
    }

    @Override
    public Boolean insertVisitNum(Integer id) {

        ProNewsCharityOut proNewsCharityOut = this.selectById(id);
        proNewsCharityOut.setId(id);
        proNewsCharityOut.setVisitNum(proNewsCharityOut.getVisitNum() + 1);

        return this.updateById(proNewsCharityOut);
    }

}
