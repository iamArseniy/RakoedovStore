package kfu.itis.arseniy.service;

import kfu.itis.arseniy.entity.Client;
import kfu.itis.arseniy.entity.EmailCode;
import kfu.itis.arseniy.repository.ClientRepository;
import kfu.itis.arseniy.repository.EmailCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailCodeLoginService {

    private final EmailCodeRepository emailCodeRepository;
    private final ClientRepository clientRepository;
    private final EmailCodeService emailService;

    @Transactional
    public void sendLoginCode(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        emailCodeRepository.deleteByEmail(email);

        EmailCode emailCode = new EmailCode(null, email, code, expiry);
        emailCodeRepository.save(emailCode);

        emailService.sendCode(email, code);
    }
    @Transactional
    public Optional<Client> verifyCode(String email, String code) {
        Optional<EmailCode> record = emailCodeRepository.findByEmailAndCode(email, code);
        if (!record.isPresent() || record.get().getExpiryTime().isBefore(LocalDateTime.now())) {

            return Optional.empty();
        }
        emailCodeRepository.deleteByEmail(email);
        return clientRepository.findByEmail(email);
    }
}