package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllFamily;

import java.util.List;

/**
 * 姓氏表
 *
 * @author keriezhang
 * @date 2016/10/31
 */
public interface IAllFamilyService extends IService<AllFamily> {

    /**
     * 根据首字母查询
     *
     * @param firstLetter
     * @return
     */
    List<AllFamily> getAllFamilyByFirstLetter(String firstLetter);

    /**
     * 修改姓氏
     *
     * @param allFamily
     * @return
     */
    AllFamily updateAllFamily(AllFamily allFamily);
}
