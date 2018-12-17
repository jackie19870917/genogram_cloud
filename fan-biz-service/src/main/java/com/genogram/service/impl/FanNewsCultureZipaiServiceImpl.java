package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.entityvo.NewsCultureZipaiVo;
import com.genogram.mapper.FanNewsCultureZipaiMapper;
import com.genogram.service.IFanNewsCultureZipaiService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 联谊会-家族文化-字派表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanNewsCultureZipaiServiceImpl extends ServiceImpl<FanNewsCultureZipaiMapper, FanNewsCultureZipai> implements IFanNewsCultureZipaiService {

    /**
     * 联谊会字派查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:23
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Page<NewsCultureZipaiVo> commonality(Wrapper<FanNewsCultureZipai> entity, Integer pageNo, Integer pageSize) {
        //返回新VO的集合
        List<NewsCultureZipaiVo> familyCultureVoList = new ArrayList<>();
        Page<FanNewsCultureZipai> fanNewsCultureZipais = this.selectPage(new Page<FanNewsCultureZipai>(pageNo, pageSize), entity);
        //获取list集合
        List<FanNewsCultureZipai> list = fanNewsCultureZipais.getRecords();
        if (list.size() == 0) {
            return null;
        }
        list.forEach((news) -> {
            NewsCultureZipaiVo newsCultureZipaiVo = new NewsCultureZipaiVo();
            //存储新对象
            BeanUtils.copyProperties(news, newsCultureZipaiVo);
            //转换时间为long
            newsCultureZipaiVo.setCreateTimeLong(news.getCreateTime().getTime());
            newsCultureZipaiVo.setUpdateTimeLong(news.getUpdateTime().getTime());

            familyCultureVoList.add(newsCultureZipaiVo);
        });
        //重新设置page对象
        Page<NewsCultureZipaiVo> mapPage = new Page<>(pageNo, pageSize);
        mapPage.setRecords(familyCultureVoList);
        mapPage.setSize(fanNewsCultureZipais.getSize());
        mapPage.setTotal(fanNewsCultureZipais.getTotal());
        return mapPage;
    }

    /**
     * 联谊会首页字派查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:23
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public StringBuffer commonalityIndex(Wrapper<FanNewsCultureZipai> entity) {

        //首页字派查询
        List<FanNewsCultureZipai> fanNewsCultureZipais = this.selectList(entity);
        if (fanNewsCultureZipais.size() == 0) {
            return null;
        }
        StringBuffer string = new StringBuffer();
        fanNewsCultureZipais.forEach((data) -> {

            string.append(data.getZipaiTxt() + ",");
        });
        //删除最后一个字符
        string.deleteCharAt(string.length() - 1);
        return string;
    }

    /**
     * 联谊会字派后台进入修改页面
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:23
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public FanNewsCultureZipai getZiPaiDetail(Integer id) {
        FanNewsCultureZipai fanNewsCultureZipai = this.selectById(id);
        return fanNewsCultureZipai;
    }

    /**
     * 联谊会字派后台新增
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public boolean addOrUpdateZiPai(FanNewsCultureZipai fanNewsCultureZipai) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        if (fanNewsCultureZipai.getId() == null) {
            //存入创建时间
            fanNewsCultureZipai.setCreateTime(format);
            //存入修改时间
            fanNewsCultureZipai.setUpdateTime(format);
            //初始化查看数为0
            fanNewsCultureZipai.setVisitNum(0);
        } else {
            //存入修改时间
            fanNewsCultureZipai.setUpdateTime(format);
        }
        return this.insertOrUpdate(fanNewsCultureZipai);
    }

    /**
     * 联谊会家族字派后台删除
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 11:45
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public Boolean deleteZipaiById(Integer id, int status, AllUserLogin userLogin) {
        FanNewsCultureZipai fanNewsCultureZipai = this.selectById(id);
        if(StringUtils.isEmpty(fanNewsCultureZipai)){
            return null;
        }
        //修改状态
        fanNewsCultureZipai.setStatus(status);
        //修改人
        fanNewsCultureZipai.setUpdateUser(userLogin.getId());
        //修改时间
        fanNewsCultureZipai.setUpdateTime(DateUtil.getCurrentTimeStamp());
        return this.updateAllColumnById(fanNewsCultureZipai);
    }

    /**
     *联谊会家族字派后台置顶
     *@Author: yuzhou
     *@Date: 2018-12-15
     *@Time: 9:33
     *@Param:
     *@return:
     *@Description:
    */
    @Override
    public Boolean ZipaiStick(Integer id, AllUserLogin userLogin) {
        FanNewsCultureZipai fanNewsCultureZipai = this.selectById(id);
        if(StringUtils.isEmpty(fanNewsCultureZipai)){
            return null;
        }
        //修改人
        fanNewsCultureZipai.setUpdateUser(userLogin.getId());
        //修改时间
        fanNewsCultureZipai.setUpdateTime(DateUtil.getCurrentTimeStamp());
        return this.updateAllColumnById(fanNewsCultureZipai);
    }
}
