package com.genogram.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllUserLogin;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.service.IUserService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 *@author: Toxicant
 *@date: 2018-11-23
*/
@Service
public class UserServiceImpl extends ServiceImpl<AllUserLoginMapper, AllUserLogin> implements IUserService {

    @Override
    public AllUserLogin getUserLoginInfoByToken(String str) {

        try {

            //Base64解密
            str= new String(Base64.decodeBase64(str));

            str=str.substring(str.indexOf("{")+1, str.lastIndexOf("}"));

            List<String> list = Arrays.asList(str.split("="));

            AllUserLogin allUserLogin = new AllUserLogin();
            allUserLogin.setId(Integer.valueOf(list.get(3)));
            allUserLogin.setUserName(list.get(4));
            allUserLogin.setMobilePhone(list.get(0));
            allUserLogin.setPassword(list.get(1));

            if(allUserLogin.getId()==null){
                allUserLogin.setId(1);
            }

            return allUserLogin;
        } catch (Exception e) {
            return null;
        }
    }
}
