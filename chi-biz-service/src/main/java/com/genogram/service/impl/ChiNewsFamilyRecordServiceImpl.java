package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiNewsFamilyRecord;
import com.genogram.mapper.ChiNewsFamilyRecordMapper;
import com.genogram.service.IChiNewsFamilyRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import com.genogram.unit.StringsUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * <p>
 * 省级-记录家族-家族动态,家族通告文章表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@Service
public class ChiNewsFamilyRecordServiceImpl extends ServiceImpl<ChiNewsFamilyRecordMapper, ChiNewsFamilyRecord> implements IChiNewsFamilyRecordService {

    /**
     * 全国记录家族文章新增 修改
     *
     * @Author: yuzhou
     * @Date: 2019-02-18
     * @Time: 17:49
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean addOrUpdateRecord(ChiNewsFamilyRecord chiNewsFamilyRecord, AllUserLogin userLogin) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();

        //判断是否有id
        if (chiNewsFamilyRecord.getId() == null) {
            chiNewsFamilyRecord.setCreateUser(userLogin.getId());
            chiNewsFamilyRecord.setCreateTime(format);
        }
        chiNewsFamilyRecord.setUpdateUser(userLogin.getId());
        chiNewsFamilyRecord.setUpdateTime(format);
        boolean result = this.insertOrUpdate(chiNewsFamilyRecord);
        return result;
    }

    /**
     * 全国记录家族文章删除
     *
     * @Author: yuzhou
     * @Date: 2019-02-18
     * @Time: 18:09
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean deleteRecord(Integer id, AllUserLogin userLogin, int status) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();

        //查询数据
        ChiNewsFamilyRecord chiNewsFamilyRecord = this.selectById(id);
        if (StringsUtils.isEmpty(chiNewsFamilyRecord)) {
            return false;
        }
        chiNewsFamilyRecord.setUpdateTime(format);
        chiNewsFamilyRecord.setUpdateUser(userLogin.getId());
        chiNewsFamilyRecord.setStatus(status);

        //逻辑删除
        boolean result = this.updateById(chiNewsFamilyRecord);
        return result;
    }

    /**
     * 全国记录家族文章查询
     *
     * @Author: yuzhou
     * @Date: 2019-02-19
     * @Time: 16:36
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Page<ChiNewsFamilyRecord> getRecordPage(Wrapper<ChiNewsFamilyRecord> entity, Integer pageNo, Integer pageSize) {
        Page<ChiNewsFamilyRecord> chiNewsFamilyRecordPage = this.selectPage(new Page<>(pageNo, pageSize), entity);
        return chiNewsFamilyRecordPage;
    }

    /**
     *全国记录家族文章详情
     *@Author: yuzhou
     *@Date: 2019-02-20
     *@Time: 16:48
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public ChiNewsFamilyRecord getFamilyRecordDetail(Integer id) {
        ChiNewsFamilyRecord chiNewsFamilyRecord = this.selectById(id);
        return chiNewsFamilyRecord;
    }
}
