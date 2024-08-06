package ru.vtb.javapro.task4.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vtb.javapro.task4.entity.Client;


@Repository
public interface ClientJPARepository extends JpaRepository<Client, Long> {

    @Query("Select c from Client c where id = :aLong")
    Optional<Client> findClient(Long aLong);

    @Query("Select c from Client c where id = :aLong")
    @EntityGraph(attributePaths = {"productList"})
    Optional<Client> findClientWithProducts(Long aLong);
}
