package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.FanSysCharitableDeclare;
import com.genogram.mapper.FanSysCharitableDeclareMapper;
import com.genogram.service.IProFanSysCharitableDeclareService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
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
public class ProFanSysCharitableDeclareServiceImpl extends ServiceImpl<FanSysCharitableDeclareMapper, FanSysCharitableDeclare> implements IProFanSysCharitableDeclareService {

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
        boolean result = this.deleteById(id);
        return result;
    }


    /**
     *联谊会慈善帮扶申报添加修改
     *@Author: yuzhou
     *@Date: 2018-12-12
     *@Time: 14:00
     *@Param:
     *@return:
     *@Description:
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
     *联谊会慈善帮扶申报详情
     *@Author: yuzhou
     *@Date: 2018-12-12
     *@Time: 16:16
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public FanSysCharitableDeclare getFamilyStructureDetails(Integer id) {
        FanSysCharitableDeclare fanSysCharitableDeclare = this.selectById(id);
        return fanSysCharitableDeclare;
    }

    /**
     *联谊会慈善帮扶申报详情帮扶反馈添加
     *@Author: yuzhou
     *@Date: 2018-12-12
     *@Time: 18:08
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean addCharityAssistFeedback(Integer id, String helpFeedback, String helpFeedbackFile) {
        //查询数据
        FanSysCharitableDeclare fanSysCharitableDeclare = this.selectById(id);
        //存储
        fanSysCharitableDeclare.setHelpFeedback(helpFeedback);
        fanSysCharitableDeclare.setHelpFeedbackFile(helpFeedbackFile);
        //修改数据
        boolean result = this.updateAllColumnById(fanSysCharitableDeclare);
        return result;
    }
}
