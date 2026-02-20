package Mattiazerbini.u5_w3_d5.controllers;

import Mattiazerbini.u5_w3_d5.entities.Utente;
import Mattiazerbini.u5_w3_d5.payloads.UtentePayload;
import Mattiazerbini.u5_w3_d5.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    private final UtenteService utenteService;

    @Autowired
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }


    @GetMapping
    public List<Utente> findAll(){
        return this.utenteService.findAll();
    }

    @GetMapping("/{idUtente}")
    public Utente findById(@PathVariable UUID idUtente) {
        return this.utenteService.findById(idUtente);
    }

    @PutMapping("/me")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody UtentePayload payload) {
        return this.utenteService.findByIdAndUpdate(currentAuthenticatedUtente.getId(), payload);
    }

    @DeleteMapping("/me")
    public void deleteProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        this.utenteService.findByIdAndDelete(currentAuthenticatedUtente.getId());
    }

    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI', 'UTENTE')")
    @PutMapping("/{idUtente}")
    public Utente findByIdAndUpdate(@PathVariable UUID idUtente, @RequestBody UtentePayload payload) {
        return this.utenteService.findByIdAndUpdate(idUtente, payload);
    }

    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI', 'UTENTE')")
    @DeleteMapping("/{idUtente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID idUtente) {
        this.utenteService.findByIdAndDelete(idUtente);
    }
}
