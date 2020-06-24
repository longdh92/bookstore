package com.myweb.bookstore.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

    @Entity
    @Table(name = "product")
    public class Product implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name")
        private String name;

        @Column(name = "image")
        private String image;

        @Column(name = "quantity")
        private Integer quantity;

        @Column(name = "price")
        private Double price;

        @Column(name = "manufactureddate")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        private LocalDate manufacturedDate;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id", referencedColumnName = "id")
        private Category category;

        @Column(name = "description", columnDefinition = "TEXT")
        private String description;

        @Column(name = "status")
        private String status;

        @OneToMany(mappedBy = "product")
        private List<CartDetail> cartDetailList = new ArrayList<>();

        @OneToMany(mappedBy = "product")
        private List<BillDetail> billDetailList = new ArrayList<>();
        public Product() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public LocalDate getManufacturedDate() {
            return manufacturedDate;
        }

        public void setManufacturedDate(LocalDate manufacturedDate) {
            this.manufacturedDate = manufacturedDate;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<CartDetail> getCartDetailList() {
            return cartDetailList;
        }

        public void setCartDetailList(List<CartDetail> cartDetailList) {
            this.cartDetailList = cartDetailList;
        }

        public List<BillDetail> getBillDetailList() {
            return billDetailList;
        }

        public void setBillDetailList(List<BillDetail> billDetailList) {
            this.billDetailList = billDetailList;
        }
}
