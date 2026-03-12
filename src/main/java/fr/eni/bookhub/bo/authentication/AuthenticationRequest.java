package fr.eni.bookhub.bo.authentication;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "email")
public class AuthenticationRequest {
    private String email;
    private String password;
}
