package kfu.itis.arseniy.repository;

import kfu.itis.arseniy.entity.EmailCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailCodeRepository extends JpaRepository<EmailCode, Long> {
    Optional<EmailCode> findByEmailAndCode(String email, String code);
    void deleteByEmail(String email);
}