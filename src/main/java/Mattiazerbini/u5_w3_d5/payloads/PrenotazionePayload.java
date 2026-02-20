package Mattiazerbini.u5_w3_d5.payloads;

import Mattiazerbini.u5_w3_d5.entities.Evento;
import Mattiazerbini.u5_w3_d5.entities.Utente;
import lombok.Getter;

import java.util.UUID;
@Getter
public class PrenotazionePayload {

    private boolean confermata;
    private UUID idEvento;
    private UUID idUtente;

    public PrenotazionePayload(boolean confermata, UUID idEvento, UUID idUtente) {
        this.confermata = confermata;
        this.idEvento = idEvento;
        this.idUtente = idUtente;
    }
}
