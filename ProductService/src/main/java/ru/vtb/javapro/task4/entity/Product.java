package ru.vtb.javapro.task4.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.javapro.task4.dto.ProductDTO;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "accountnumber")
    private String accountNumber;
    private BigDecimal balance;
    @Column(name = "producttype")
    @Convert(converter = ProductTypeConverter.class)
    private ProductType productType;

    @Override
    public String toString() {
        return "Product{" + "id=" + id + " , accountNumber='" + accountNumber + '\'' +
                   ", balance=" + balance + ", productType=" + productType + '}';
    }

    public enum ProductType{
        ACCOUNT(1), CARD(2);
        private final Integer code;

        ProductType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public static ProductType getInstanceByCode(Integer code){
            return Arrays.stream(ProductType.values())
                       .filter(v->v.code.equals(code))
                       .findFirst()
                       .orElseThrow();
        }

    }

    public ProductDTO convertToProductDto(){
        return new ProductDTO(this.id,this.accountNumber, this.balance, this.productType.name());
    }

    public static Product createFromProductDTO(ProductDTO productDTO){
        return new Product(productDTO.id(), productDTO.accontNumber(), productDTO.balance(), ProductType.valueOf(productDTO.productType()));
    }
}
