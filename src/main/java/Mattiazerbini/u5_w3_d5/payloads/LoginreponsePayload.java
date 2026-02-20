package Mattiazerbini.u5_w3_d5.payloads;

import lombok.Getter;

@Getter
public class LoginreponsePayload {
    private String accesToken;

    public LoginreponsePayload(String accesToken) {
        this.accesToken = accesToken;
    }
}
