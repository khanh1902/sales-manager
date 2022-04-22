package com.laptrinhjava.salesmanager.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "order_")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "totalprice")
    private Long totalMoney;

    @Column(name = "customerMoney", nullable = true)
    private Long customerMoney;

    @Column(name = "balance")
    private Long balance;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createddate", nullable = false)
    private Date createdDate;

    @PrePersist
    private void onCreated() {
        createdDate = new Date();
    } // init system time

    @Column(name = "createdby")
    private String createdBy;

    //     Quan hệ 1-n 1 hóa đơn có nhiều sản phẩm
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "order_orderdetails",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "order_detail_id"))
    private List<OrderDetail> products = new ArrayList<>();
//    private ArrayList<OrderDetail> products;

    //Contractor
    public Order() {
    }

    //Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<OrderDetail> getProducts() {
        return products;
    }

    public void setProducts(List<OrderDetail> products) {
        this.products = products;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(Long customerMoney) {
        this.customerMoney = customerMoney;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
