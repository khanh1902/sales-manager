package com.laptrinhjava.salesmanager.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productname")
    private String productName;

    private Long price;
    private Long quantity;

    @Column(name = "categoryid")
    private Long categoryId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createddate", nullable = false)
    private Date createdDate;
    @PrePersist
    private void onCreated(){
        createdDate = new Date();
    }

    @Column(name = "createdby", nullable = true)
    private String createdBy;

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    // Many to One Có nhiều sản phẩm ở 1 hóa đơn
//    @ManyToMany(mappedBy = "listProductId", fetch = FetchType.LAZY)
//    private List<Order> bills = new ArrayList<>();

    public Products() {
    }

//    public void setBills(List<Order> bills) {
//        this.bills = bills;
//    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
