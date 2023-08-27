package com.mo1isting.backend.dao;

import java.io.Serializable;

/**
 * coffeemaker
 * @author 
 */
public class Coffeemaker implements Serializable {
    private Integer coffeemakerid;

    /**
     * 器具名称
     */
    private String coffeemakername;

    /**
     * 购买价格
     */
    private Double coffeemakerprice;

    /**
     * 器具图片,存链接
     */
    private String coffeemakeravatar;

    /**
     * 冲煮参数
     */
    private String coffeemakervar;

    private static final long serialVersionUID = 1L;

    public Integer getCoffeemakerid() {
        return coffeemakerid;
    }

    public void setCoffeemakerid(Integer coffeemakerid) {
        this.coffeemakerid = coffeemakerid;
    }

    public String getCoffeemakername() {
        return coffeemakername;
    }

    public void setCoffeemakername(String coffeemakername) {
        this.coffeemakername = coffeemakername;
    }

    public Double getCoffeemakerprice() {
        return coffeemakerprice;
    }

    public void setCoffeemakerprice(Double coffeemakerprice) {
        this.coffeemakerprice = coffeemakerprice;
    }

    public String getCoffeemakeravatar() {
        return coffeemakeravatar;
    }

    public void setCoffeemakeravatar(String coffeemakeravatar) {
        this.coffeemakeravatar = coffeemakeravatar;
    }

    public String getCoffeemakervar() {
        return coffeemakervar;
    }

    public void setCoffeemakervar(String coffeemakervar) {
        this.coffeemakervar = coffeemakervar;
    }
}