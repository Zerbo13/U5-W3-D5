package Mattiazerbini.u5_w3_d5.services;

import Mattiazerbini.u5_w3_d5.entities.Utente;
import Mattiazerbini.u5_w3_d5.exceptions.NotFoundException;
import Mattiazerbini.u5_w3_d5.payloads.UtentePayload;
import Mattiazerbini.u5_w3_d5.repositories.UtenteRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UtenteService {

    private UtenteRepository utenteRepository;
    private final PasswordEncoder bcrypt;

    @Autowired
    public UtenteService(PasswordEncoder bcrypt, UtenteRepository utenteRepository) {
        this.bcrypt = bcrypt;
        this.utenteRepository = utenteRepository;
    }


    public Utente salvaUtente(UtentePayload payload){
        if(this.utenteRepository.existsByEmail(payload.getEmail())){
            throw new ValidationException("Questa mail" + payload.getEmail() + "è gia registrata");
        }
        Utente newUtente = new Utente(payload.getNome(), payload.getCognome(), payload.getData_nascita(),
                payload.getEmail(), bcrypt.encode(payload.getPassword()));
        Utente utenteSalvato = this.utenteRepository.save(newUtente);
        log.info("Il dipendente "+newUtente.getNome()+" " +newUtente.getCognome()+ " è stato inserito con successo!");
        return utenteSalvato;
    }

    public Utente findByIdAndUpdate(UUID idUtente, UtentePayload payload) {
        // 1. Cerchiamo l'utente nel db
        Utente found = this.findById(idUtente);

        // 2. Validazione dati (esempio controllo se email è già in uso
        if (!found.getEmail().equals(payload.getEmail())) this.utenteRepository.findByEmail(payload.getEmail()).ifPresent(user -> {
            try {
                throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        });
        // Il controllo su email già in uso lo faccio solo se l'utente sta modificando effettivamente la sua email

        // 3. Modifico l'utente trovato
        found.setNome(payload.getNome());
        found.setCognome(payload.getCognome());
        found.setEmail(payload.getEmail());
        found.setPassword(payload.getPassword());

        // 4. Salvo
        Utente modifiedUser = this.utenteRepository.save(found);

        // 5. Log
        log.info("L'utente con id " + modifiedUser.getId() + " è stato modificato correttamente");

        // 6. Ritorno l'utente modificato
        return modifiedUser;
    }

    public void findByIdAndDelete(UUID idUtente) {
        Utente found = this.findById(idUtente);
        this.utenteRepository.delete(found);
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email)
                .orElseThrow(() -> new ValidationException("L'utente con email " + email + " non è stato trovato!"));
    }

    public Utente findById(UUID idUtente) {
        return this.utenteRepository.findById(idUtente)
                .orElseThrow(() -> new NotFoundException(idUtente));
    }
}
