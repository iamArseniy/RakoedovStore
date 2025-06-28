package kfu.itis.arseniy.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
}
