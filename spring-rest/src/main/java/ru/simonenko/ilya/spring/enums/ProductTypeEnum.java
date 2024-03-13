package ru.simonenko.ilya.spring.enums;

public enum ProductTypeEnum {

    ACCOUNT("ACCOUNT"),
    CARD("CARD");

    private String productType;

    ProductTypeEnum(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }

    public static ProductTypeEnum getEnum(String productType) {
        for (ProductTypeEnum type : values()) {
            if (type.getProductType().equalsIgnoreCase(productType)) {
                return type;
            }
        }

        return null;
    }

}
