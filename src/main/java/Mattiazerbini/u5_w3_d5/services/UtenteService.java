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

import java.util.List;
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
                payload.getEmail(), bcrypt.encode(payload.getPassword()), payload.getRuolo());
        Utente utenteSalvato = this.utenteRepository.save(newUtente);
        log.info("Il dipendente "+newUtente.getNome()+" " +newUtente.getCognome()+ " è stato inserito con successo!");
        return utenteSalvato;
    }

    public List<Utente> findAll() {
        return this.utenteRepository.findAll();
    }


    public Utente findById(UUID idUtente) {
        return this.utenteRepository.findById(idUtente)
                .orElseThrow(() -> new NotFoundException(idUtente));
    }

    public Utente findByIdAndUpdate(UUID idUtente, UtentePayload payload) {

        Utente found = this.findById(idUtente);
        if (!found.getEmail().equals(payload.getEmail())) this.utenteRepository.findByEmail(payload.getEmail()).ifPresent(user -> {
            try {
                throw new BadRequestException("L'email inserita " + user.getEmail() + " è già in uso!");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        });
        found.setNome(payload.getNome());
        found.setCognome(payload.getCognome());
        found.setEmail(payload.getEmail());
        found.setPassword(payload.getPassword());
        found.setData_nascita(payload.getData_nascita());
        found.setRuolo(payload.getRuolo());

        Utente utenteModificato = this.utenteRepository.save(found);

        log.info("L'utente con id " + utenteModificato.getId() + " è stato modificato correttamente");

        return utenteModificato;
    }

    public void findByIdAndDelete(UUID idUtente) {
        Utente found = this.findById(idUtente);
        this.utenteRepository.delete(found);
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email)
                .orElseThrow(() -> new ValidationException("L'utente con email " + email + " non è stato trovato!"));
    }

}
