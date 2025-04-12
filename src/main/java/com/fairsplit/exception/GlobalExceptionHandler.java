// package com.fairsplit.exception;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.Map;

// @RestControllerAdvice
// public class GlobalExceptionHandler {

//     @ExceptionHandler(IllegalArgumentException.class)
//     public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
//         return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
//     }

//     @ExceptionHandler(IllegalStateException.class)
//     public ResponseEntity<?> handleConflict(IllegalStateException ex) {
//         return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
//     }

//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<?> handleGeneric(Exception ex) {
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal error"));
//     }
// }

