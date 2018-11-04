package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamily;
import com.genogram.entityvo.TestVo;
import com.genogram.service.TestServ;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@RequestMapping("/test")
@Controller
public class TestController {

    @Autowired
    private TestServ TestServ;
    @ResponseBody
    @RequestMapping("/test1")
    public Response<TestVo> test1(@RequestParam(name = "username") String username,
                                  @RequestParam(name = "pwd") String pwd){
        TestVo vo = TestServ.test(username,pwd);

        Page page = new Page();
        //设置当前页
        page.setCurrent(Integer.parseInt(pwd    ));
        //每页个数
        page.setSize(2);
        Page<AllFamily> mapPage = new Page<>(page.getCurrent(),page.getSize());
        Map requestParam = new HashMap<>();
        //requestParam.put("value","s");
        Page<AllFamily> myItems= TestServ.queryMyItems(mapPage,requestParam);
        System.out.println(myItems);
        return ResponseUtlis.success(vo);
    }
}
