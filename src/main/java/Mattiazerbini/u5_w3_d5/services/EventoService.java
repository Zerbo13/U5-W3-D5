package Mattiazerbini.u5_w3_d5.services;

import Mattiazerbini.u5_w3_d5.entities.Evento;
import Mattiazerbini.u5_w3_d5.entities.Utente;
import Mattiazerbini.u5_w3_d5.exceptions.NotFoundException;
import Mattiazerbini.u5_w3_d5.payloads.EventoPayload;
import Mattiazerbini.u5_w3_d5.payloads.UtentePayload;
import Mattiazerbini.u5_w3_d5.repositories.EventoRepository;
import Mattiazerbini.u5_w3_d5.repositories.PrenotazioneRepository;
import Mattiazerbini.u5_w3_d5.repositories.UtenteRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class EventoService {

    private final EventoRepository eventoRepository;

    @Autowired
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public Evento salvaEvento(EventoPayload payload){
        Evento newViaggio = new Evento(payload.getTitolo(), payload.getDescrizione(), payload.getData(),
                payload.getLuogo(), payload.getPosti());
        Evento eventoSalvato = this.eventoRepository.save(newViaggio);
        log.info("L'evento "+newViaggio.getLuogo()+" " +newViaggio.getData()+ " è stato inserito con successo!");
        return eventoSalvato;
    }
}
