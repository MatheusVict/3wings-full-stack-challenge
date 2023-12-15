package br.com.threewings.wingsblog.swagger.annotations;

import br.com.threewings.wingsblog.exceptions.exceptionPattern.ExceptionDetailsResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.threewings.wingsblog.swagger.SwaggerResponses.CODE_400;
import static br.com.threewings.wingsblog.swagger.SwaggerResponses.RESPONSE_404;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = CODE_400, description = RESPONSE_404,
        useReturnTypeSchema = true,
        content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = ExceptionDetailsResponse.class)
        )
)
public @interface NotFoundResponse {
}
