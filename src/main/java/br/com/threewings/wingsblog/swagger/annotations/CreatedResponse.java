package br.com.threewings.wingsblog.swagger.annotations;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.threewings.wingsblog.swagger.SwaggerResponses.CODE_201;
import static br.com.threewings.wingsblog.swagger.SwaggerResponses.RESPONSE_201;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = CODE_201, description = RESPONSE_201, useReturnTypeSchema = true)
public @interface CreatedResponse {
}