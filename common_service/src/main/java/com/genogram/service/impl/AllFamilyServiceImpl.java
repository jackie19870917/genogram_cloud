package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllFamily;
import com.genogram.mapper.AllFamilyMapper;
import com.genogram.service.IAllFamilyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 姓氏表
 *
 * @author keriezhang
 * @date 2016/10/31
 */
@Service
public class AllFamilyServiceImpl extends ServiceImpl<AllFamilyMapper, AllFamily> implements IAllFamilyService {

    @Override
    public List<AllFamily> getAllFamilyByFirstLetter(String firstLetter) {

        Wrapper<AllFamily> wrapper = new EntityWrapper<>();
        wrapper.eq("first_letter", firstLetter);

        return this.selectList(wrapper);
    }

    @Override
    public AllFamily updateAllFamily(AllFamily allFamily) {

        this.updateById(allFamily);

        return this.selectById(allFamily.getId());
    }

    @Override
    public AllFamily getAllFamilyById(Integer id) {

        return this.selectById(id);
    }
}
