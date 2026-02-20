package Mattiazerbini.u5_w3_d5.services;

import Mattiazerbini.u5_w3_d5.entities.Evento;
import Mattiazerbini.u5_w3_d5.entities.Prenotazione;
import Mattiazerbini.u5_w3_d5.entities.Utente;
import Mattiazerbini.u5_w3_d5.exceptions.ExceptionPrenotatione;
import Mattiazerbini.u5_w3_d5.exceptions.NotFoundException;
import Mattiazerbini.u5_w3_d5.payloads.PrenotazionePayload;
import Mattiazerbini.u5_w3_d5.repositories.EventoRepository;
import Mattiazerbini.u5_w3_d5.repositories.PrenotazioneRepository;
import Mattiazerbini.u5_w3_d5.repositories.UtenteRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final UtenteRepository utenteRepository;
    private final EventoRepository eventoRepository;

    @Autowired
    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository,UtenteRepository utenteRepository, EventoRepository eventoRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.utenteRepository = utenteRepository;
        this.eventoRepository = eventoRepository;
    }

    public Prenotazione salvaPrenotazione(PrenotazionePayload payload){
        Utente newUtente = utenteRepository.findById(payload.getIdUtente())
                .orElseThrow(()->new ValidationException("Utente con id "+payload.getIdUtente()+ " non trovato"));

        Evento newEvento = eventoRepository.findById(payload.getIdEvento())
                .orElseThrow(() -> new ValidationException("Evento con id "+payload.getIdEvento()+ " non trovato"));

        boolean prenotazioneEsistente = prenotazioneRepository.existsByUtenteIdAndEvento
                (payload.getIdUtente(), payload.getIdEvento());
        if(prenotazioneEsistente){
            throw new ExceptionPrenotatione("L'utente ha gia effettuato una prenotazione");
        }
        Prenotazione prenotazione = new Prenotazione(payload.isConfermata(), newEvento, newUtente);
        Prenotazione prenotazioneSalvata = this.prenotazioneRepository.save(prenotazione);
        log.info("Prenotazione con id " +prenotazioneSalvata.getId()+ " salvata con successo!" );
        return prenotazioneSalvata;
    }

    public List<Prenotazione> findAll() {
        return this.prenotazioneRepository.findAll();
    }


    public Prenotazione findById(UUID idPrenotazione) {
        return this.prenotazioneRepository.findById(idPrenotazione)
                .orElseThrow(() -> new NotFoundException(idPrenotazione));
    }

    public void findByIdAndDelete(UUID idPrenotazione) {
        Prenotazione found = this.findById(idPrenotazione);
        this.prenotazioneRepository.delete(found);
    }
}
