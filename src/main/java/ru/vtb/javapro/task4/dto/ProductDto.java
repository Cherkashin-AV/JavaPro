package ru.vtb.javapro.task4.dto;


import java.math.BigDecimal;
import ru.vtb.javapro.task4.entity.Product;
import ru.vtb.javapro.task4.entity.Product.ProductType;


public record ProductDto(Long id,
                         String accontNumber,
                         BigDecimal balance,
                         String productType)
{

    public Product convertToProduct(){
        return new Product(this.id, this.accontNumber, this.balance, ProductType.valueOf(this.productType));
    }
}
