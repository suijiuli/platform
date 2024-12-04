package com.example.demo1.model;

import java.math.BigDecimal;

public class Goods {
    public String goodsId;
    public String user;
    public String goodsName;
    public int quantity;
    public BigDecimal price;

    public Goods(String goodsId, String user, String goodsName, int quantity, BigDecimal price) {
        this.goodsId = goodsId;
        this.user = user;
        this.goodsName = goodsName;
        this.quantity = quantity;
        this.price = price;
    }

    public Goods() {

    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId='" + goodsId + '\'' +
                ", user='" + user + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}