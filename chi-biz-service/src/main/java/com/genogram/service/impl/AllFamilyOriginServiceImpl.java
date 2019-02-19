package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamilyOrigin;
import com.genogram.entity.AllUserLogin;
import com.genogram.mapper.AllFamilyOriginMapper;
import com.genogram.service.IAllFamilyOriginService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
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

    /**
     *删除姓氏起源信息
     *@Author: yuzhou
     *@Date: 2019-02-18
     *@Time: 14:34
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean deleteOrigin(Integer id, int status, AllUserLogin userLogin) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        //根据id查询数据
        AllFamilyOrigin allFamilyOrigin = this.selectById(id);

        if(StringsUtils.isEmpty(allFamilyOrigin)){
            return false;
        }

        allFamilyOrigin.setStatus(status);
        allFamilyOrigin.setUpdateUser(userLogin.getId());
        allFamilyOrigin.setUpdateTime(format);
        //逻辑删除数据
        Boolean aBoolean = this.updateAllColumnById(allFamilyOrigin);
        return aBoolean;
    }

    /**
     *全国姓氏起源查询
     *@Author: yuzhou
     *@Date: 2019-02-19
     *@Time: 15:54
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Page<AllFamilyOrigin> getOriginPage(Integer pageNo, Integer pageSize, Wrapper<AllFamilyOrigin> entity) {
        Page<AllFamilyOrigin> allFamilyOriginPage = this.selectPage(new Page<AllFamilyOrigin>(pageNo, pageSize), entity);
        return allFamilyOriginPage;
    }

}
