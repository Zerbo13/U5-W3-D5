package Mattiazerbini.u5_w3_d5.payloads;

import lombok.Getter;

import java.time.LocalDate;
@Getter
public class EventoPayload {
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private String luogo;
    private int posti;

    public EventoPayload(String titolo, String descrizione, LocalDate data, String luogo, int posti) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.posti = posti;
    }
}
