package com.genogram.entityvo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class IndexFundDrowingVo {

    /**
     * 序号
     */
    private Integer id;
    /**
     * 网站id(fan_sys_site_id)
     */
    private Integer siteId;
    /**
     * 提现金额
     */
    private BigDecimal drowAmount;
    /**
     * 到账银行
     */
    private String drowBank;
    /**
     * 到账银行支行名称
     */
    private String drowBankSub;
    /**
     * 提现时间
     */
    private Date drowTime;
    /**
     * 账户名
     */
    private String drowInAccountName;
    /**
     * 入账卡号
     */
    private String drowInAccountCard;
    /**
     * 备注
     */
    private String drowComments;
    /**
     * 审核状态(1:审核中;2:打款中;3:成功4:拒绝)
     */
    private String approveStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改人
     */
    private Integer updateUser;

    /**
     *
     */
    private String statusName;
}
