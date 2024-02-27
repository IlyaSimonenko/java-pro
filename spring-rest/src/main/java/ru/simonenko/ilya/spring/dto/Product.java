package ru.simonenko.ilya.spring.dto;

import ru.simonenko.ilya.spring.enums.ProductTypeEnum;

import java.math.BigDecimal;

public class Product {

    private Long id;
    private Long userId;
    private String accountNumber;
    private BigDecimal balance;
    private ProductTypeEnum productType;

    public Product() {
    }

    public Product(Long userId, String accountNumber, BigDecimal balance, ProductTypeEnum productType) {
        this(null, userId, accountNumber, balance, productType);
    }

    public Product(Long id, Long userId, String accountNumber, BigDecimal balance, ProductTypeEnum productType) {
        this.id = id;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.productType = productType;
    }

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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public ProductTypeEnum getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeEnum productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", userId=" + userId +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", productType='" + productType + '\'' +
                '}';
    }

}
