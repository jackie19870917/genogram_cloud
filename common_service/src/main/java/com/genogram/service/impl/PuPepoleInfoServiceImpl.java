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
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;

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

    @Autowired
    private IPuPepoleInfoService puPepoleInfoService;

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
    public Boolean addPuPepoleInfo(PuPepoleInfo puPepoleInfo, AllUserLogin userLogin,Integer puBaseInfoId,Integer pepoleId,Integer isPepId) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        //判断id是否为空 是修改还是新增
        if (puPepoleInfo.getId() == null) {
            //存入创建时间
            puPepoleInfo.setCreateTime(format);
            puPepoleInfo.setCreateUser(userLogin.getId());
        }
            //存入修改时间
            puPepoleInfo.setUpdateTime(format);
            puPepoleInfo.setUpdateUser(userLogin.getId());
        boolean result = this.insertOrUpdate(puPepoleInfo);

        //格式化时间
        Format time = new SimpleDateFormat("yyyy-MM-dd");
        String createTime = time.format(puPepoleInfo.getCreateTime());
        String updateTime = time.format(puPepoleInfo.getUpdateTime());

        //在基础表上添加根人物Id  判断修改和新增时间是否相等来确定是新增还是修改
        if(result && isPepId==0 && createTime.equals(updateTime)){
            PuBaseInfo puBaseInfo = puBaseInfoService.selectById(puBaseInfoId);
            if(puBaseInfo!=null){
                puBaseInfo.setPepId(puBaseInfo.getPepId()+" "+puPepoleInfo.getId().toString());
            }else {
                puBaseInfo.setPepId(puPepoleInfo.getId().toString());
            }
            puBaseInfoService.updateAllColumnById(puBaseInfo);

            //属于第几代   根人物第一代
            puPepoleInfo.setGenerateNum(1);
            this.updateAllColumnById(puPepoleInfo);

        // isPepId 1=兄弟姐妹 2=配偶 3=女儿 4=儿子
        }else if(result && isPepId!=0 && createTime.equals(updateTime)){
            PuPepoleInfo puPepole = puPepoleInfoService.selectById(pepoleId);
            if(isPepId==1){
                if(StringUtils.isEmpty(puPepole.getBrothersSistersId())){
                    puPepole.setBrothersSistersId(puPepoleInfo.getId().toString());
                }
                puPepole.setBrothersSistersId(puPepole.getBrothersSistersId()+" "+puPepoleInfo.getId().toString());
            }else if(isPepId==2){
                if(StringUtils.isEmpty(puPepole.getSpouseId())){
                    puPepole.setSpouseId(puPepoleInfo.getId().toString());
                }
                puPepole.setSpouseId(puPepole.getSpouseId()+" "+puPepoleInfo.getId().toString());
            }else if(isPepId==3){
                if(StringUtils.isEmpty(puPepole.getDaughterId())){
                    puPepole.setDaughterId(puPepoleInfo.getId().toString());
                }
                puPepole.setDaughterId(puPepole.getDaughterId()+" "+puPepoleInfo.getId().toString());
            }else if(isPepId==4){
                if(StringUtils.isEmpty(puPepole.getSonId())){
                    puPepole.setSonId(puPepoleInfo.getId().toString());
                }
                puPepole.setSonId(puPepole.getSonId()+" "+puPepoleInfo.getId().toString());
            }
                puPepoleInfoService.updateAllColumnById(puPepole);

            //判断写入是第几代
            if(isPepId==1 || isPepId==2){
                puPepole.setGenerateNum(puPepole.getGenerateNum());
                puPepoleInfoService.updateAllColumnById(puPepole);
            }else if(isPepId==3 || isPepId==4){
                puPepole.setGenerateNum(puPepole.getGenerateNum()+1);
                puPepoleInfoService.updateAllColumnById(puPepole);
            }
        }
        return result;
    }
}
