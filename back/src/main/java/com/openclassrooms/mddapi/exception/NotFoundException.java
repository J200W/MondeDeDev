package com.openclassrooms.mddapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lancée lorsqu'une ressource n'est pas trouvée
 * @see RuntimeException
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
}
