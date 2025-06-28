package kfu.itis.arseniy.repository;

import kfu.itis.arseniy.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

    Optional<Stage> findByName(String name);

    List<Stage> findAllByOrderByNameAsc();

    boolean existsByName(String name);
}