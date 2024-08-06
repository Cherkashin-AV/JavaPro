package ru.vtb.javapro.task4.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vtb.javapro.task4.entity.Product;


@Repository
public interface ProductJPARepository extends JpaRepository<Product, Long> {

    @Query(value = "Delete from products p where p.productsid in (Select c.products from users c where c.id = :userId)", nativeQuery = true)
    void deleteAll(Long userId);

    @Query(value = "Delete from products p where p.productsid in (Select c.products from users c where c.id = :userId) and p.id=:id", nativeQuery = true)
    void delete(Long userId, Long id);

    @Query(value = "Select p.* from users c join products p on c.products=p.productsid where c.id = :userId and p.id=:id", nativeQuery = true)
    Optional<Product> getProduct(Long userId, Long id);
}
