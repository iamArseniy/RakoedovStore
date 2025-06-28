package kfu.itis.arseniy.controller.api;

import kfu.itis.arseniy.dto.ClientDto;
import kfu.itis.arseniy.entity.Client;
import kfu.itis.arseniy.converter.ClientMapper;
import kfu.itis.arseniy.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientRestController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> clients = clientService.findAll()
                .stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        Optional<Client> clientOpt = clientService.findById(id);
        return clientOpt.map(client -> ResponseEntity.ok(clientMapper.toDto(client)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        Client created = clientService.create(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.toDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable Long id, @RequestBody ClientDto dto) {
        Optional<Client> existing = clientService.findById(id);
        if (existing.isPresent()) {
            Client client = clientMapper.toEntity(dto);
            client.setId(id);
            clientService.update(client);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
