package com.ydw.admin.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-04-14
 */
public class UserClicked implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 该记录对应的日期
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date dateTime;

    private Integer total;
    /**
     * 0-1点在线用户
     */
    private Integer zero;

    /**
     * 1-2点在线用户
     */
    private Integer one;

    private Integer two;

    private Integer three;

    private Integer four;

    private Integer five;

    private Integer six;

    private Integer seven;

    private Integer eight;

    private Integer nine;

    private Integer ten;

    private Integer eleven;

    private Integer twelve;

    private Integer thirteen;

    private Integer fourteen;

    private Integer fifteen;

    private Integer sixteen;

    private Integer seventeen;

    private Integer eighteen;

    private Integer nineteen;

    private Integer twenty;

    private Integer twentyOne;

    /**
     * 22-23点在线用户
     */
    private Integer twentyTwo;

    /**
     * 23-0点在线用户数
     */
    private Integer twentyThree;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getZero() {
        return zero;
    }

    public void setZero(Integer zero) {
        this.zero = zero;
    }

    public Integer getOne() {
        return one;
    }

    public void setOne(Integer one) {
        this.one = one;
    }

    public Integer getTwo() {
        return two;
    }

    public void setTwo(Integer two) {
        this.two = two;
    }

    public Integer getThree() {
        return three;
    }

    public void setThree(Integer three) {
        this.three = three;
    }

    public Integer getFour() {
        return four;
    }

    public void setFour(Integer four) {
        this.four = four;
    }

    public Integer getFive() {
        return five;
    }

    public void setFive(Integer five) {
        this.five = five;
    }

    public Integer getSix() {
        return six;
    }

    public void setSix(Integer six) {
        this.six = six;
    }

    public Integer getSeven() {
        return seven;
    }

    public void setSeven(Integer seven) {
        this.seven = seven;
    }

    public Integer getEight() {
        return eight;
    }

    public void setEight(Integer eight) {
        this.eight = eight;
    }

    public Integer getNine() {
        return nine;
    }

    public void setNine(Integer nine) {
        this.nine = nine;
    }

    public Integer getTen() {
        return ten;
    }

    public void setTen(Integer ten) {
        this.ten = ten;
    }

    public Integer getEleven() {
        return eleven;
    }

    public void setEleven(Integer eleven) {
        this.eleven = eleven;
    }

    public Integer getTwelve() {
        return twelve;
    }

    public void setTwelve(Integer twelve) {
        this.twelve = twelve;
    }

    public Integer getThirteen() {
        return thirteen;
    }

    public void setThirteen(Integer thirteen) {
        this.thirteen = thirteen;
    }

    public Integer getFourteen() {
        return fourteen;
    }

    public void setFourteen(Integer fourteen) {
        this.fourteen = fourteen;
    }

    public Integer getFifteen() {
        return fifteen;
    }

    public void setFifteen(Integer fifteen) {
        this.fifteen = fifteen;
    }

    public Integer getSixteen() {
        return sixteen;
    }

    public void setSixteen(Integer sixteen) {
        this.sixteen = sixteen;
    }

    public Integer getSeventeen() {
        return seventeen;
    }

    public void setSeventeen(Integer seventeen) {
        this.seventeen = seventeen;
    }

    public Integer getEighteen() {
        return eighteen;
    }

    public void setEighteen(Integer eighteen) {
        this.eighteen = eighteen;
    }

    public Integer getNineteen() {
        return nineteen;
    }

    public void setNineteen(Integer nineteen) {
        this.nineteen = nineteen;
    }

    public Integer getTwenty() {
        return twenty;
    }

    public void setTwenty(Integer twenty) {
        this.twenty = twenty;
    }

    public Integer getTwentyOne() {
        return twentyOne;
    }

    public void setTwentyOne(Integer twentyOne) {
        this.twentyOne = twentyOne;
    }

    public Integer getTwentyTwo() {
        return twentyTwo;
    }

    public void setTwentyTwo(Integer twentyTwo) {
        this.twentyTwo = twentyTwo;
    }

    public Integer getTwentyThree() {
        return twentyThree;
    }

    public void setTwentyThree(Integer twentyThree) {
        this.twentyThree = twentyThree;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "UserClicked{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", total=" + total +
                ", zero=" + zero +
                ", one=" + one +
                ", two=" + two +
                ", three=" + three +
                ", four=" + four +
                ", five=" + five +
                ", six=" + six +
                ", seven=" + seven +
                ", eight=" + eight +
                ", nine=" + nine +
                ", ten=" + ten +
                ", eleven=" + eleven +
                ", twelve=" + twelve +
                ", thirteen=" + thirteen +
                ", fourteen=" + fourteen +
                ", fifteen=" + fifteen +
                ", sixteen=" + sixteen +
                ", seventeen=" + seventeen +
                ", eighteen=" + eighteen +
                ", nineteen=" + nineteen +
                ", twenty=" + twenty +
                ", twentyOne=" + twentyOne +
                ", twentyTwo=" + twentyTwo +
                ", twentyThree=" + twentyThree +
                '}';
    }
}
