package kfu.itis.arseniy.repository;

import kfu.itis.arseniy.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Client c SET c.first_name = ?1, c.last_name = ?2, c.phone = ?3, c.password = ?4 WHERE c.id = ?5")
    void updateClient(String firstName, String lastName, String phone, String password, Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Client c WHERE c.id = ?1")
    void deleteWithCarts(Long id);
}
