package fr.eni.bookhub.bo.authentication;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode
public class AuthenticationResponse {
    private String token;
}
