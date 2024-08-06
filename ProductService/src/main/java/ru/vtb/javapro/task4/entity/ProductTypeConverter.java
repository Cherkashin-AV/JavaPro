package ru.vtb.javapro.task4.entity;


import jakarta.persistence.AttributeConverter;
import ru.vtb.javapro.task4.entity.Product.ProductType;


public class ProductTypeConverter implements AttributeConverter<ProductType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProductType attribute) {
        return attribute.getCode();
    }

    @Override
    public ProductType convertToEntityAttribute(Integer dbData) {
        return ProductType.getInstanceByCode(dbData);
    }
}
