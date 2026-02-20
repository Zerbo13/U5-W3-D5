package Mattiazerbini.u5_w3_d5.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Utente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome;
    private String cognome;
    private LocalDate data_nascita;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    public Utente(String nome, String cognome, LocalDate data_nascita, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
        this.email = email;
        this.password = password;
        this.ruolo = Ruolo.UTENTE;
   }

}
