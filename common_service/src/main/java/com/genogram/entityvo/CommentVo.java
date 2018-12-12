package com.genogram.entityvo;

import com.genogram.entity.AllUserReply;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVo {

    /**
     * id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论时间
     */
    private Date createTime;
    /**
     *  嵌套评论或回复结构体
     */
    private List<CommentVo> next;


}
