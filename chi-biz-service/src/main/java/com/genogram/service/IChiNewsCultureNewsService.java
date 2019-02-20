package com.genogram.service;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiNewsCultureNews;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.NewsDetailVo;

/**
 * <p>
 * 全国-家族文化文章表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-19
 */
public interface IChiNewsCultureNewsService extends IService<ChiNewsCultureNews> {

    /**
     * 全国姓氏文化新增 修改
     * @param chiNewsCultureNews
     * @param userLogin
     * @param fileName
     * @param filePath
     * @return
     */
    Boolean addOrUpdateCultureNews(ChiNewsCultureNews chiNewsCultureNews, AllUserLogin userLogin, String fileName, String filePath);

    /**
     * 全国姓氏文化删除
     * @param id
     * @param status
     * @param userLogin
     * @return
     */
    Boolean deleteCulturById(Integer id, int status, AllUserLogin userLogin);

    /**
     * 全国姓氏文化详情
     * @param id
     * @return
     */
    NewsDetailVo getFamilyCultureDetail(Integer id);
}
