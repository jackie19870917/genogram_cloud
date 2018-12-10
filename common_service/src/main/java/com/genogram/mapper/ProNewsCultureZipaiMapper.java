package com.genogram.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsCultureZipai;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.genogram.entityvo.ProNewsCultureZipaiVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 省级-家族文化-字派表 Mapper 接口
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface ProNewsCultureZipaiMapper extends BaseMapper<ProNewsCultureZipai> {

    /**
     * 多表模糊查询
     *
     * @param mapPage 分页
     * @param map     查询条件
     * @return
     */
    List<ProNewsCultureZipaiVo> selectLike(Page<ProNewsCultureZipaiVo> mapPage, Map map);

}
