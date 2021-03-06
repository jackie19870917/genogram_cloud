package com.genogram.entityvo;

import com.genogram.entity.AllUserReply;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
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

    private String nickName;

    private String picSrc;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 评论时间
     */
    private Date createTime;
    /**
     * 嵌套评论或回复结构体
     */
    private List<CommentVo> next;


}
