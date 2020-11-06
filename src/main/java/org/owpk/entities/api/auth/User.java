package org.owpk.entities.api.auth;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String login;
    private String password;
    private String twofa_code;
    private boolean remember;
}
