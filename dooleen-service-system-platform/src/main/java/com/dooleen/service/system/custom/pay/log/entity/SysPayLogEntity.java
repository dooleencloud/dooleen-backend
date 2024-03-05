package com.dooleen.service.system.custom.pay.log.entity;
import java.math.BigDecimal;

import javax.persistence.Id;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
*  pay_log
* @author liqh 2019-08-26
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("learn_pay_log")
@ApiModel
@ToString(callSuper = true)
public class SysPayLogEntity extends BaseEntity {

    /**
    * 支付日志号
    */
	@Id
    @TableId(type = IdType.INPUT)
    private String id;

    /**
    * 微信openid
    */
    private String openId;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 发生数量
    */
    private BigDecimal occurAmt;
    
    /**
     * 积分数量
     */
     private BigDecimal pointsAmt;

    /**
    * 发生方向
    */
    private String occurDirection;

    /**
    * 发生原因
    */
    private String occurReason;


    /**
    * 交易状态
    */
    private String transStatus;

    /**
    * noncestr
    */
    private String nonceStr;

    /**
     * 备注
     */
    private String remark;


    public SysPayLogEntity() {
    }
}