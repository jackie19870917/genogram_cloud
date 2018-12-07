package com.genogram.service.impl;

import com.genogram.service.IAllCheckOutService;
import com.genogram.unit.SensitivewordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Administrator
 */
@Service
public class AllCheckOutServiceImpl implements IAllCheckOutService {

    @Override
    public Set getSensitiveWord(String string) {

        SensitivewordFilter sensitivewordFilter = new SensitivewordFilter();

        return sensitivewordFilter.getSensitiveWord(string, 1);
    }
}
