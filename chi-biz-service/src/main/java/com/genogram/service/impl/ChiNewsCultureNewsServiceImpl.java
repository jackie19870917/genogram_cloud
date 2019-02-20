package com.genogram.service.impl;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiNewsCultureNews;
import com.genogram.mapper.ChiNewsCultureNewsMapper;
import com.genogram.service.IChiNewsCultureNewsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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
}
