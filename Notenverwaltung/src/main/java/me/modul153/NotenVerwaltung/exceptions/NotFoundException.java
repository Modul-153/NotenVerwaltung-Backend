package me.modul153.NotenVerwaltung.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "object does not exist.")
public class NotFoundException extends RuntimeException {
}
