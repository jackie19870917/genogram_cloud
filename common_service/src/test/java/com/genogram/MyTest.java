package com.genogram;


import com.genogram.entity.AllFamily;
import com.genogram.mapper.AllFamilyMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {
    @Autowired
    private AllFamilyMapper allFamilyMapper;

    @Test
    public void contextLoads() {
        //查询
        AllFamily a = allFamilyMapper.selectById("8");
        //update
        a.setValue("ffff");
        allFamilyMapper.updateById(a);
        System.out.println(a);
        //insert
        a =  new AllFamily();
        a.setFirstLetter("ss");
        allFamilyMapper.insert(a);

        //allFamilyMapper.selectPage()
    }



}
