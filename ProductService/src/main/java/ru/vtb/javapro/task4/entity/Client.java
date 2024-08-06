package ru.vtb.javapro.task4.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratedColumn;
import ru.vtb.javapro.task4.dto.ClientDTO;

@Entity()
@Table(name = "users")
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="productsid", referencedColumnName="products")
    private final List<Product> productList = new ArrayList<>() ;

    @GeneratedColumn("")
    private Long products;


    public Client(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public List<Product> getProductList() {
        return List.copyOf(productList);
    }

    public void addProduct(Product product){
        productList.add(product);
    }

    public void deleteProduct(Product product){
        productList.remove(product);
    }

    public void updateProduct(Product product){
        Product pr = productList.stream().filter(p->p.getId().equals(product.getId())).findFirst().orElseThrow();
        pr.setBalance(product.getBalance());
        pr.setProductType(product.getProductType());
        pr.setAccountNumber(product.getAccountNumber());
    }

    public ClientDTO convertToClientDto(){
        return  new ClientDTO(this.id, this.username, productList.stream().map(p->p.convertToProductDto()).toList());
    }

    public static Client createFromClientDTO(ClientDTO clientDTO){
        Client result = new Client(clientDTO.id(), clientDTO.clientName());
        clientDTO.products().forEach(p-> result.addProduct(Product.createFromProductDTO(p)));
        return result;
    }

}
