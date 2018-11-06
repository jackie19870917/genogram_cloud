package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.mapper.FanNewsCultureZipaiMapper;
import com.genogram.service.IFanNewsCultureZipaiService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Page<FanNewsCultureZipai> commonality(Integer showId, Integer status, Integer pageNo, Integer pageSize) {
            Wrapper<FanNewsCultureZipai> entity = new EntityWrapper<FanNewsCultureZipai>();
            entity.eq("show_id", showId);
            entity.eq("status", status);
            entity.orderBy("create_time", false);
        Page<FanNewsCultureZipai> fanNewsCultureZipais = this.selectPage(new Page<FanNewsCultureZipai>(pageNo, pageSize), entity);
        return fanNewsCultureZipais;
        }

     // 联谊会首页字派查询
    @Override
    public StringBuffer CommonalityIndex(Integer showId, Integer status) {
        Wrapper<FanNewsCultureZipai> entity = new EntityWrapper<FanNewsCultureZipai>();
        entity.eq("show_id", showId);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
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

}
