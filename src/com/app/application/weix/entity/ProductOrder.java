package com.app.application.weix.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by haoya on 3/26/15.
 */
@Entity
@Table(name = "t_order")
public class ProductOrder implements Serializable {

    public ProductOrder() {

    }

    @Id
    //@GeneratedValue(generator = "generator")
    private Integer id;
    @OneToOne
    private Product product;
    @Column(insertable = false,updatable = false)
    private Integer product_id;
    @Column
    private int user_id;
    @Column
    private int number;
    @Column
    private Date createtime;
    @Column
    private String address;
    @Column
    private Double price;
    @Column
    private Date deliverytime;
    @Column
    private String phone;
    @Column
    private String openid;
    
    

    public ProductOrder(int id, int product_id, int user_id, int number, Date createtime, String address, Double price, Date deliverytime, String phone, String openid) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.number = number;
        this.createtime = createtime;
        this.address = address;
        this.price = price;
        this.deliverytime = deliverytime;
        this.phone = phone;
        this.openid = openid;
    }

    @GenericGenerator(name = "generator", strategy = "increment")
//    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

  
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(Date deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the product_id
     */
    public Integer getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

   

}
