package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanSysCharitableDeclare;
import com.genogram.mapper.FanSysCharitableDeclareMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IFanSysCharitableDeclareService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaohei
 * @since 2018-11-29
 */
@Service
public class FanSysCharitableDeclareServiceImpl extends ServiceImpl<FanSysCharitableDeclareMapper, FanSysCharitableDeclare> implements IFanSysCharitableDeclareService {

    @Autowired
    private IUploadFileService iuploadFileService;

    /**
     * 分页查询
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<FanSysCharitableDeclare> getCharitableDeclarePage(Wrapper<FanSysCharitableDeclare> entity, Integer pageNo, Integer pageSize) {
        //分页查询文章主表
        Page<FanSysCharitableDeclare> fanNewsFamilyRecord = this.selectPage(new Page<FanSysCharitableDeclare>(pageNo, pageSize), entity);
        return fanNewsFamilyRecord;
    }

    /**
     * 联谊会慈善帮扶删除
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteCharitableDeclareById(Integer id) {
        //查询是否有这条数据
        FanSysCharitableDeclare fanSysCharitableDeclare = this.selectById(id);
        if (StringsUtils.isEmpty(fanSysCharitableDeclare)) {
            return null;
        }
        //状态(0:审核通过;1:审核中;2:草稿3:审核不通过)
        Integer status = 2;
        if (!fanSysCharitableDeclare.getStatus().equals(status)) {
            return null;
        }
        boolean result = this.deleteById(id);
        return result;
    }


    /**
     * 联谊会慈善帮扶申报添加修改
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 14:00
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean addCharityAssist(FanSysCharitableDeclare fanSysCharitableDeclare) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if (fanSysCharitableDeclare.getId() == null) {
            //存入创建时间
            fanSysCharitableDeclare.setCreateTime(format);
        }
        //存入修改时间
        fanSysCharitableDeclare.setUpdateTime(format);
        boolean result = this.insertOrUpdate(fanSysCharitableDeclare);
        return result;
    }

    /**
     * 联谊会慈善帮扶申报详情
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 16:16
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public FanSysCharitableDeclare getFamilyStructureDetails(Integer id) {
        FanSysCharitableDeclare fanSysCharitableDeclare = this.selectById(id);
        return fanSysCharitableDeclare;
    }

    /**
     * 联谊会慈善帮扶申报详情帮扶反馈添加
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 18:08
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean addCharityAssistFeedback(Integer id, String helpFeedback, String helpFeedbackFile, AllUserLogin userLoginInfoByToken) {
        //查询数据
        FanSysCharitableDeclare fanSysCharitableDeclare = this.selectById(id);
        //存储
        fanSysCharitableDeclare.setHelpFeedback(helpFeedback);
        fanSysCharitableDeclare.setHelpFeedbackFile(helpFeedbackFile);
        //修改人
        fanSysCharitableDeclare.setUpdateUser(userLoginInfoByToken.getId());
        //修改时间
        fanSysCharitableDeclare.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改数据
        boolean result = this.updateAllColumnById(fanSysCharitableDeclare);
        return result;
    }
}
