package me.modul153.NotenVerwaltung.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "bad Request!")
public class UserBadRequestException extends RuntimeException {
}
