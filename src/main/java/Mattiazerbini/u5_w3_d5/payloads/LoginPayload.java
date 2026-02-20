package Mattiazerbini.u5_w3_d5.payloads;

import lombok.Getter;

@Getter
public class LoginPayload {

    private String email;
    private String password;

    public LoginPayload(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
