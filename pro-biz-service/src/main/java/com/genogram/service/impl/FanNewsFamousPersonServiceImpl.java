package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsFamousPerson;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.mapper.FanNewsFamousPersonMapper;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.*;
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
 * 联谊会-家族名人-家族长老-家族栋梁-组织架构 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsFamousPersonServiceImpl extends ServiceImpl<FanNewsFamousPersonMapper, FanNewsFamousPerson> implements IFanNewsFamousPersonService {

    @Autowired
    private IFanNewsUploadFileService fanNewsUploadFileService;
    @Autowired
    private IProSysRecommendService proSysRecommendService;
    @Autowired
    private IAllUserLoginService allUserLoginService;

    /**
     *联谊会家族文化前台增加查看数
     *@Author: yuzhou
     *@Date: 2018-11-12
     *@Time: 13:49
     *@Param:
     *@return:
     *@Description:
     */
    @Override
    public void addVisitNum(Integer id) {
        //查出详情
        FanNewsFamousPerson fanNewsFamousPerson = this.selectById(id);
        //查看数加一
        Integer visitNum = fanNewsFamousPerson.getVisitNum()+1;
        fanNewsFamousPerson.setVisitNum(visitNum);
        this.updateAllColumnById(fanNewsFamousPerson);
        if(visitNum >200 || visitNum==200){
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=1;
            //要插入的实体类
            FanSysRecommend fanSysRecommend=new FanSysRecommend();
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setNewsSource(newsSource);
            fanSysRecommend.setShowId(fanNewsFamousPerson.getShowId());
            fanSysRecommend.setNewsId(fanNewsFamousPerson.getId());
            proSysRecommendService.addRecommend(fanSysRecommend);
        }
    }
    /**
     *联谊会家族名人详情查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:22
     *@Param:
     *@return:
     *@Description:
     */
    @Override
    public FamilyPersonVo getFamilyFamilyDetail(Integer id) {

        //根据Id查出文章详情
        FanNewsFamousPerson fanNewsFamousPerson=  this.selectById(id);
        if (fanNewsFamousPerson==null){
            return null;
        }

        //查询图片
        Wrapper<FanNewsUploadFile> uploadentity = new EntityWrapper<FanNewsUploadFile>();
        uploadentity.eq("show_id", fanNewsFamousPerson.getShowId());
        //置顶封面  是否封面(0.否;1:是封面)
        uploadentity.eq("pic_index",1);
        uploadentity.eq("news_id",id);
        //查询所有文章id下的图片附件
        List<FanNewsUploadFile> files =  fanNewsUploadFileService.selectList(uploadentity);

        //查出名称
        AllUserLogin updateUser = allUserLoginService.selectById(fanNewsFamousPerson.getUpdateUser());
        AllUserLogin createUser = allUserLoginService.selectById(fanNewsFamousPerson.getCreateUser());

        //返回新VO的集合赋值新对象vo
        FamilyPersonVo familyPersonVo=new FamilyPersonVo();

        //调用方法封装集合
        BeanUtils.copyProperties(fanNewsFamousPerson,familyPersonVo);
        //存储图片list集合
        familyPersonVo.setFanNewsUploadFileList(files);
        //存储作者名称时间
        familyPersonVo.setUpdateTimeLong(fanNewsFamousPerson.getUpdateTime().getTime());
        familyPersonVo.setCreateTimeLong(fanNewsFamousPerson.getCreateTime().getTime());
        familyPersonVo.setCreateUserName(null);
        familyPersonVo.setCreateUserName(null);
        return familyPersonVo;
    }
}
