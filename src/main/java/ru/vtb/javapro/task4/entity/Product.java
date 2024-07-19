package ru.vtb.javapro.task4.entity;


import java.math.BigDecimal;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vtb.javapro.task4.dto.ProductDto;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
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

    public ProductDto convertToProductDto(){
        return new ProductDto(this.id,this.accountNumber, this.balance, this.productType.name());
    }
}
