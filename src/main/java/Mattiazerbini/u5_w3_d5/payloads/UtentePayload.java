package Mattiazerbini.u5_w3_d5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UtentePayload {
    @NotBlank(message = "Il nome proprio è un campo obbligatorio")
    @Size(min = 1, max = 20, message = "Il nome  deve essere tra i 2 e i 20 caratteri")
    private String nome;
    @NotBlank(message = "Il cognome è un campo obbligatorio")
    @Size(min = 1, max =20, message = "Il cognome deve essere tra i 1 e i 20 caratteri")
    private String cognome;
    private LocalDate data_nascita;
    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "L'indirizzo email inserito non è nel formato corretto!")
    private String email;
    @NotBlank(message = "La password è obbligatoria")
    @Size(min = 3, message = "La password deve avere almeno 3 caratteri")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{4,}$", message = "La password deve contenere una maiuscola, una minuscola ")
    private String password;

    public UtentePayload(String nome, String cognome, LocalDate data_nascita, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
        this.email = email;
        this.password = password;
    }
}
