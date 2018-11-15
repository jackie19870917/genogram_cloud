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
     * 审核状态
     */

    private String statusName;

    public String getStatusName() {

        String statusName = "";
        if (1 == this.getApproveStatus()) {
            statusName = ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_1;
        } else if (2 == this.getApproveStatus()) {
            statusName = ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_2;
        } else if (3 == this.getApproveStatus()) {
            statusName = ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_3;
        } else if (4 == this.getApproveStatus()) {
            statusName = ConstantsStatus.FAN_INDEX_FOUND_DROWING_APPROVE_STATUS_4;
        }

        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
