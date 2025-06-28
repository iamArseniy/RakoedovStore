package kfu.itis.arseniy.converter;

import kfu.itis.arseniy.dto.ClientDto;
import kfu.itis.arseniy.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDto toDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getFirst_name(),
                client.getLast_name(),
                client.getPhone(),
                client.getEmail()
        );
    }

    public Client toEntity(ClientDto dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setFirst_name(dto.getFirst_name());
        client.setLast_name(dto.getLast_name());
        client.setPhone(dto.getPhone());
        client.setEmail(dto.getEmail());
        client.setPassword(null); // Пароль задаётся отдельно при регистрации/обновлении
        return client;
    }
}
