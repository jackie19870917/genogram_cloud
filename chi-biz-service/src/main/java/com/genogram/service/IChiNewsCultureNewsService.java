package com.genogram.service;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiNewsCultureNews;
import com.baomidou.mybatisplus.service.IService;

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
}
