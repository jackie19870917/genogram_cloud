package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.mapper.FanNewsCultureZipaiMapper;
import com.genogram.service.IFanNewsCultureZipaiService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
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


    //联谊会字派查询
    @Override
    public Page<FanNewsCultureZipai> commonality(Wrapper<FanNewsCultureZipai> entity, Integer pageNo, Integer pageSize) {
        Page<FanNewsCultureZipai> fanNewsCultureZipais = this.selectPage(new Page<FanNewsCultureZipai>(pageNo, pageSize), entity);
        return fanNewsCultureZipais;
        }

     // 联谊会首页字派查询
    @Override
    public StringBuffer CommonalityIndex(Wrapper<FanNewsCultureZipai> entity) {

        //首页字派查询
        List<FanNewsCultureZipai> fanNewsCultureZipais = this.selectList(entity);
        if (fanNewsCultureZipais.size()==0){
            return null;
        }
        StringBuffer string=new StringBuffer();
        fanNewsCultureZipais.forEach(( data)->{

            string.append(data.getZipaiTxt()+",");
        });
        //删除最后一个字符
        string.deleteCharAt(string.length() - 1);
        return string;
    }

    //联谊会字派后台进入修改页面
    @Override
    public FanNewsCultureZipai getZiPaiDetail(Integer id) {
        FanNewsCultureZipai fanNewsCultureZipai=this.selectById(id);
        return fanNewsCultureZipai;
    }

    //联谊会字派后台查询
    @Override
    public boolean addOrUpdateZiPai(FanNewsCultureZipai fanNewsCultureZipai) {
        //生成时间
        Timestamp format = DateUtil.format(new Date());
        if(fanNewsCultureZipai.getId()==null){
            fanNewsCultureZipai.setCreateUser(null);
            //存入创建时间
            fanNewsCultureZipai.setCreateTime(format);
        }else{
            //存入修改时间
            fanNewsCultureZipai.setUpdateTime(format);
            fanNewsCultureZipai.setUpdateUser(null);
        }
        return this.insertOrUpdate(fanNewsCultureZipai);
    }

}
