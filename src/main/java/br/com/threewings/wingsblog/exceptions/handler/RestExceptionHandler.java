package br.com.threewings.wingsblog.exceptions.handler;

import br.com.threewings.wingsblog.exceptions.PostNotFoundException;
import br.com.threewings.wingsblog.exceptions.SlugAlreadyExistsException;
import br.com.threewings.wingsblog.exceptions.exceptionPattern.ExceptionDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ExceptionDetailsResponse> handlerNotFoundException(PostNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionDetailsResponse.builder()
                        .title("Post not found")
                        .status(HttpStatus.NOT_FOUND.value())
                        .details(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .developerMessage(
                                "Post Not Found Exception, check the documentation "
                        )
                        .exception(e.getClass().getName())
                        .build());

    }

    @ExceptionHandler(SlugAlreadyExistsException.class)
    public ResponseEntity<ExceptionDetailsResponse> handlerSlugAlreadyExistsException(SlugAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ExceptionDetailsResponse.builder()
                        .title("Slug already exists")
                        .status(HttpStatus.CONFLICT.value())
                        .details(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .developerMessage(
                                "Slug already exists Exception, check the documentation "
                        )
                        .exception(e.getClass().getName())
                        .build());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetailsResponse> handlerMethodArgumentNotValidExceptionException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldError = exception.getBindingResult().getFieldErrors();
        String fields = fieldError.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldMessage = fieldError.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(
                ExceptionDetailsResponse.builder()
                        .title("Bad Request Exception, Invalid Fields")
                        .status(400)
                        .details("Check the field(s) error")
                        .timestamp(LocalDateTime.now())
                        .developerMessage("Bad Request Exception, Invalid Fields, check the documentation")
                        .exception(exception.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldMessage)
                        .build());
    }
}
