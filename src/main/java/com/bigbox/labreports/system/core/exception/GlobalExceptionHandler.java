package com.bigbox.labreports.system.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;
import java.text.ParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>("Yetkiniz geçersiz", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        return new ResponseEntity<>("Girdi/Çıktı hatası meydana geldi: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleParseException(ParseException ex) {
        return new ResponseEntity<>("Tarih/Saat biçim hatası: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Bir hata meydana geldi: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

