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

import java.util.List;
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

    public List<Evento> findAll() {
        return this.eventoRepository.findAll();
    }


    public Evento findById(UUID idEvento) {
        return this.eventoRepository.findById(idEvento)
                .orElseThrow(() -> new NotFoundException(idEvento));
    }

    public Evento findByIdAndUpdate(UUID idEvento, EventoPayload payload) {

        Evento found = this.findById(idEvento);
        found.setTitolo(payload.getTitolo());
        found.setDescrizione(payload.getDescrizione());
        found.setData(payload.getData());
        found.setLuogo(payload.getLuogo());
        found.setPosti(payload.getPosti());

        Evento eventoModificato = this.eventoRepository.save(found);
        log.info("L'evento con id " + eventoModificato.getId() + " è stato modificato correttamente");
        return eventoModificato;
    }

    public void findByIdAndDelete(UUID idEvento) {
        Evento found = this.findById(idEvento);
        this.eventoRepository.delete(found);
    }
}
