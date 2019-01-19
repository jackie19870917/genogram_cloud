package com.genogram.service.impl;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.PuBaseInfo;
import com.genogram.entity.PuPepoleInfo;
import com.genogram.mapper.PuPepoleInfoMapper;
import com.genogram.service.IPuBaseInfoService;
import com.genogram.service.IPuPepoleInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yizx
 * @since 2019-01-18
 */
@Service
public class PuPepoleInfoServiceImpl extends ServiceImpl<PuPepoleInfoMapper, PuPepoleInfo> implements IPuPepoleInfoService {


    @Autowired
    public IPuBaseInfoService puBaseInfoService;

    /**
     *添加人物信息 修改
     *@Author: yuzhou
     *@Date: 2019-01-18
     *@Time: 18:18
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean addPuPepoleInfo(PuPepoleInfo puPepoleInfo, AllUserLogin userLogin,Integer id) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if (puPepoleInfo.getId() == null) {
            //存入创建时间
            puPepoleInfo.setCreateTime(format);
            puPepoleInfo.setCreateUser(userLogin.getId());
            //存入修改时间
            puPepoleInfo.setUpdateTime(format);
            puPepoleInfo.setUpdateUser(userLogin.getId());

            //在基础表上添加根人物Id
            if(puPepoleInfo.getGenerateNum()==1){
                PuBaseInfo puBaseInfo = puBaseInfoService.selectById(id);
                if(puBaseInfo!=null){
                 puBaseInfo.setPepId(puPepoleInfo.getId());
                 puBaseInfoService.updateAllColumnById(puBaseInfo);
                }
            }
        }
            //存入修改时间
            puPepoleInfo.setUpdateTime(format);
            puPepoleInfo.setUpdateUser(userLogin.getId());
        return this.insertOrUpdate(puPepoleInfo);
    }
}
