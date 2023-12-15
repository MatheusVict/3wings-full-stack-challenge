package br.com.threewings.wingsblog.exceptions.exceptionPattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExceptionDetailsResponse {
    private String title;
    private int status;
    private String details;
    private LocalDateTime timestamp;
    private String developerMessage;
    private String exception;
    private String fields;
    private String fieldsMessage;
}
