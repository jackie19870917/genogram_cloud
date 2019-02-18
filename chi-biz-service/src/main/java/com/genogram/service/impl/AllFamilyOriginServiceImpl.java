package com.genogram.service.impl;

import com.genogram.entity.AllFamilyOrigin;
import com.genogram.entity.AllUserLogin;
import com.genogram.mapper.AllFamilyOriginMapper;
import com.genogram.service.IAllFamilyOriginService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * <p>
 * 姓氏起源 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-18
 */
@Service
public class AllFamilyOriginServiceImpl extends ServiceImpl<AllFamilyOriginMapper, AllFamilyOrigin> implements IAllFamilyOriginService {

    /**
     * 全国姓氏起源新增 修改
     *
     * @Author: yuzhou
     * @Date: 2019-02-18
     * @Time: 12:00
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean addOrUpdateOrigin(AllFamilyOrigin allFamilyOrigin, AllUserLogin userLogin) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if (allFamilyOrigin.getId() == null) {
            allFamilyOrigin.setCreateUser(userLogin.getId());
            allFamilyOrigin.setCreateTime(format);
        }
        allFamilyOrigin.setUpdateUser(userLogin.getId());
        allFamilyOrigin.setUpdateTime(format);
        boolean result = this.insertOrUpdate(allFamilyOrigin);
        return result;
    }
}