package Mattiazerbini.u5_w3_d5.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Utente implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Questo metodo deve restituire una collection di Authorities, cioè di RUOLI
        // SimpleGrantedAuthority è una classe che ci permette di creare degli oggetti "ruolo" compatibili con questa collection
        // Quindi passiamo il valore dell'enum a quel costruttore
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
        // return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
