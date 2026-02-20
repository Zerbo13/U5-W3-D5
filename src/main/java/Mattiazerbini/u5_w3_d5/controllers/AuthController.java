package Mattiazerbini.u5_w3_d5.controllers;

import Mattiazerbini.u5_w3_d5.entities.Utente;
import Mattiazerbini.u5_w3_d5.payloads.LoginPayload;
import Mattiazerbini.u5_w3_d5.payloads.LoginreponsePayload;
import Mattiazerbini.u5_w3_d5.payloads.UtentePayload;
import Mattiazerbini.u5_w3_d5.services.AuthService;
import Mattiazerbini.u5_w3_d5.services.UtenteService;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
private final AuthService authService;
private final UtenteService utenteService;

public AuthController(AuthService authService, UtenteService utenteService) {
    this.authService = authService;
    this.utenteService = utenteService;
}

@PostMapping("/login")
public LoginreponsePayload login(@RequestBody LoginPayload body) {

    return new LoginreponsePayload(this.authService.checkCredentialsAndGenerateToken(body));
}

@PostMapping("/register")
@ResponseStatus(HttpStatus.CREATED)
public Utente creaUtente(@RequestBody @Validated UtentePayload payload, BindingResult validationResult) {
    if (validationResult.hasErrors()) {

        List<String> errorsList = validationResult.getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .toList();

        throw new ValidationException("Errore");
    } else {
        return this.utenteService.salvaUtente(payload);
    }
}
}
