package Mattiazerbini.u5_w3_d5.controllers;

import Mattiazerbini.u5_w3_d5.entities.Evento;
import Mattiazerbini.u5_w3_d5.entities.Prenotazione;
import Mattiazerbini.u5_w3_d5.payloads.EventoPayload;
import Mattiazerbini.u5_w3_d5.payloads.PrenotazionePayload;
import Mattiazerbini.u5_w3_d5.services.EventoService;
import Mattiazerbini.u5_w3_d5.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @Autowired

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }


    @PostMapping
    public Prenotazione salvaPrenotazione(@RequestBody PrenotazionePayload payload) {
        return this.prenotazioneService.salvaPrenotazione(payload);
    }


    @DeleteMapping("/{idPrenotazione}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID idPrenotazione) {
        this.prenotazioneService.findByIdAndDelete(idPrenotazione);
    }

    @GetMapping("/{idPrenotazione}")
    public Prenotazione findById(@PathVariable UUID idPrenotazione) {
        return this.prenotazioneService.findById(idPrenotazione);
    }
}
