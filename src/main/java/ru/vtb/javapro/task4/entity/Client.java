package ru.vtb.javapro.task4.entity;


import java.util.ArrayList;
import java.util.List;
import ru.vtb.javapro.task4.dto.ClientDto;


public class Client {
    private Long id;
    private String username;

    private List<Product> products = new ArrayList<>() ;

    public Client(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public List<Product> getProducts() {
        return products.stream()
                   .toList();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void deleteProduct(Product product){
        products.remove(product);
    }

    public void updateProduct(Product product){
        Product pr = products.stream().filter(p->p.getId().equals(product.getId())).findFirst().orElseThrow();
        pr.setBalance(product.getBalance());
        pr.setProductType(product.getProductType());
        pr.setAccountNumber(product.getAccountNumber());
    }

    public ClientDto convertToClientDto(){
        return  new ClientDto(this.id, this.username, products.stream().map(p->p.convertToProductDto()).toList());
    }

}
