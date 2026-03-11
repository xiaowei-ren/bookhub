package fr.eni.bookhub.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
public class AppExceptionHandler {

    private MessageSource messageSource;

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<String> capturerMethodArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale)
    {
        String titreMsg = messageSource.getMessage("notvalidexception", null, locale);
        String message = ex
                .getFieldErrors()
                .stream()
                .map(e -> "\n\t- " + e.getDefaultMessage())
                .reduce(titreMsg, String::concat);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
