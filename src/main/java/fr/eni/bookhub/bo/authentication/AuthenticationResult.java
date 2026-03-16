package fr.eni.bookhub.bo.authentication;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class AuthenticationResult {

    private String prenom;

    private String role;

    private String token;

}
