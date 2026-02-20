package Mattiazerbini.u5_w3_d5.controllers;

import Mattiazerbini.u5_w3_d5.entities.Evento;
import Mattiazerbini.u5_w3_d5.entities.Utente;
import Mattiazerbini.u5_w3_d5.payloads.EventoPayload;
import Mattiazerbini.u5_w3_d5.payloads.UtentePayload;
import Mattiazerbini.u5_w3_d5.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    private final EventoService eventoService;

    @Autowired

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Evento> findAll(){
        return this.eventoService.findAll();
    }

    @GetMapping("/{idEvento}")
    public Evento findById(@PathVariable UUID idEvento) {
        return this.eventoService.findById(idEvento);
    }

    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI')")
    @PostMapping
    public Evento salvaEvento(@RequestBody EventoPayload payload) {
        return this.eventoService.salvaEvento(payload);
    }

    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI')")
    @PutMapping("/{idEvento}")
    public Evento findByIdAndUpdate(@PathVariable UUID idEvento, @RequestBody EventoPayload payload) {
        return this.eventoService.findByIdAndUpdate(idEvento, payload);
    }

    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI')")
    @DeleteMapping("/{idEvento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID idEvento) {
        this.eventoService.findByIdAndDelete(idEvento);
    }
}

