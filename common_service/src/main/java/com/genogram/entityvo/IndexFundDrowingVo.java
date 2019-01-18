package com.genogram.entityvo;

import com.genogram.config.ConstantsStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Toxicant
 * @date: 2018-11-15
 * @time: 16:12
 * @param:
 * @return:
 * @Description:
 */
public class IndexFundDrowingVo {

    /**
     * 序号
     */
    @Getter
    @Setter
    private Integer id;
    /**
     * 网站id(fan_sys_site_id)
     */
    @Getter
    @Setter
    private Integer siteId;
    /**
     * 提现金额
     */
    @Getter
    @Setter
    private BigDecimal drowAmount;
    /**
     * 到账银行
     */
    @Getter
    @Setter
    private String drowBank;
    /**
     * 到账银行支行名称
     */
    @Getter
    @Setter
    private String drowBankSub;
    /**
     * 提现时间
     */
    @Getter
    @Setter
    private Date drowTime;
    /**
     * 账户名
     */
    @Getter
    @Setter
    private String drowInAccountName;
    /**
     * 入账卡号
     */
    @Getter
    @Setter
    private String drowInAccountCard;
    /**
     * 备注
     */
    @Getter
    @Setter
    private String drowComments;
    /**
     * 审核状态(1:审核中;2:打款中;3:成功4:拒绝)
     */
    @Getter
    @Setter
    private Integer approveStatus;
    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;
    /**
     * 创建人
     */
    @Getter
    @Setter
    private Integer createUser;
    /**
     * 创建人姓名
     */
    @Getter
    @Setter
    private String createName;
    /**
     * 修改时间
     */
    @Getter
    @Setter
    private Date updateTime;
    /**
     * 修改人
     */
    @Getter
    @Setter
    private Integer updateUser;

    /**
     * 审核状态 (0:取消;1:审核中;2:打款中;3:成功4:拒绝)
     */

    private String statusName;

    Integer status01 = 1;
    Integer status02 = 2;
    Integer status03 = 3;
    Integer status04 = 4;

    public String getStatusName() {

        String statusName = "";
        if (status01.equals(this.getApproveStatus())) {
            statusName = ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_1;
        } else if (status02.equals(this.getApproveStatus())) {
            statusName = ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_2;
        } else if (status03.equals(this.getApproveStatus())) {
            statusName = ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_3;
        } else if (status04.equals(this.getApproveStatus())) {
            statusName = ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_4;
        }

        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
