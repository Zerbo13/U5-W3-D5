package Mattiazerbini.u5_w3_d5.repositories;

import Mattiazerbini.u5_w3_d5.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
    boolean existsByUtenteIdAndEvento(UUID idUtente, UUID idEvento);
}
