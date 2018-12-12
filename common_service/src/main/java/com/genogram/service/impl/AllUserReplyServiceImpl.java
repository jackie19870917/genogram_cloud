package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.AllUserReply;
import com.genogram.mapper.AllUserReplyMapper;
import com.genogram.service.IAllUserReplyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-07
 */
@Service
public class AllUserReplyServiceImpl extends ServiceImpl<AllUserReplyMapper, AllUserReply> implements IAllUserReplyService {

    @Override
    public List<AllUserReply> getAllUserReply(Integer commentId) {

        List<AllUserReply> allUserReplyList=null;
        Wrapper<AllUserReply> Wrapper=new EntityWrapper<>();
        Wrapper.eq("comment_id",commentId);
        if(!StringUtils.isEmpty(commentId)){
            allUserReplyList=this.selectList(Wrapper);
        }
        return allUserReplyList;
    }
}
