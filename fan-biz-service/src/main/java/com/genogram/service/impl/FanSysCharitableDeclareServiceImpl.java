package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsFamilyRecordVedio;
import com.genogram.entity.FanSysCharitableDeclare;
import com.genogram.mapper.FanSysCharitableDeclareMapper;
import com.genogram.service.IFanSysCharitableDeclareService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * <p>
 *  服务实现类
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
     * @param showId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<FanSysCharitableDeclare> getCharitableDeclarePage(Integer showId, Integer pageNo, Integer pageSize) {
        Wrapper<FanSysCharitableDeclare> entity = new EntityWrapper<FanSysCharitableDeclare>();
        entity.eq("show_id", showId);
        //分页查询文章主表
        Page<FanSysCharitableDeclare> fanNewsFamilyRecord =this.selectPage(new Page<FanSysCharitableDeclare>(pageNo, pageSize), entity);
        return fanNewsFamilyRecord;
    }

    /**
     * 新增修改
     * @param fanSysCharitableDeclare
     * @param fileName
     * @param filePath
     * @return
     */
    @Override
    public boolean addOrUpdateCharitableDeclare(FanSysCharitableDeclare fanSysCharitableDeclare,String fileName,String filePath) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if(fanSysCharitableDeclare.getId()==null){
            //存入创建时间
            fanSysCharitableDeclare.setCreateTime(format);
            fanSysCharitableDeclare.setCreateUser(null);
            //插入修改时间
            fanSysCharitableDeclare.setUpdateTime(format);
            fanSysCharitableDeclare.setUpdateUser(null);
        }else{
            //存入修改时间
            fanSysCharitableDeclare.setUpdateTime(format);
            fanSysCharitableDeclare.setUpdateUser(null);
        }
        boolean result = this.insertOrUpdate(fanSysCharitableDeclare);
        //存储图片
        if(result && StringsUtils.isNotEmpty(filePath)){
            iuploadFileService.storageFanFile(fileName,filePath,fanSysCharitableDeclare.getId(),fanSysCharitableDeclare.getShowId());
        }
        return result;
    }

    /**
     * 删除
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean deleteCharitableDeclareById(Integer id, int status) {
        FanSysCharitableDeclare fanSysCharitableDeclare = this.selectById(id);
        fanSysCharitableDeclare.setStatus(status);
        fanSysCharitableDeclare.setUpdateTime(DateUtil.getCurrentTimeStamp());
        //修改人 待写
        boolean result = this.updateAllColumnById(fanSysCharitableDeclare);
        return result;
    }
}
