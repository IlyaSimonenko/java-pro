package ru.simonenko.ilya.spring.db.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.simonenko.ilya.spring.enums.ProductTypeEnum;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ru.simonenko.ilya.spring.enums.ProductTypeEnum.getEnum;

@Converter(autoApply = true)
public class ProductTypeConverter implements AttributeConverter<ProductTypeEnum, String> {
    @Override
    public String convertToDatabaseColumn(ProductTypeEnum productType) {
        return nonNull(productType) ? productType.getProductType() : null;
    }

    @Override
    public ProductTypeEnum convertToEntityAttribute(String value) {

        ProductTypeEnum productType = getEnum(value);

        if (isNull(productType)) throw new IllegalArgumentException();

        return productType;
    }

}
