package Mattiazerbini.u5_w3_d5.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID message) {
        super(String.valueOf(message));
    }
}
