package com.ydw.netbar.model.db;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-07-28
 */
@TableName("tb_user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private Long userId;

    /**
     * 用户积分
     */
    private Integer userPoint;

    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 楠炲啿褰寸敮浣风稇妫?
     */
    private BigDecimal userBalance;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(Integer userPoint) {
        this.userPoint = userPoint;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public BigDecimal getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
        "id=" + id +
        ", userId=" + userId +
        ", userPoint=" + userPoint +
        ", userLevel=" + userLevel +
        ", userBalance=" + userBalance +
        "}";
    }
}
