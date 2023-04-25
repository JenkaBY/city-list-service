package by.jenka.service.controller.advice;

import by.jenka.service.controller.model.response.ApiErrorResponse;
import by.jenka.service.controller.model.response.ViolationResponse;
import by.jenka.service.service.exception.ApiCityServiceException;
import by.jenka.service.service.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalRestControllerAdvice {

    private static final String REQUEST_OBJECT_CONTAINS_MALFORMED_DATA = "Request object contains malformed data";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiErrorResponse handleResourceNotFound(ResourceNotFoundException exception) {
        log.error("Resource not found error: {}", exception.getMessage(), exception);
        return ApiErrorResponse.withMessage(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiErrorResponse handleConstraintViolationException(ConstraintViolationException exception,
                                                               HttpServletRequest request) {
        log.error("Resource not found error: {}", exception.getMessage(), exception);
        var violations = exception.getConstraintViolations().stream()
                .map(violation -> new ViolationResponse(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();
        var message = "%s URL is malformed".formatted(request.getRequestURI());
        return new ApiErrorResponse(message, violations);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Request data are invalid: {}", exception.getMessage(), exception);

        var violations = exception.getAllErrors()
                .stream()
                .map(objectError ->
                        new ViolationResponse("%s.%s".formatted(objectError.getObjectName(), ((FieldError) objectError).getField()),
                                objectError.getDefaultMessage()))
                .toList();
        return new ApiErrorResponse(REQUEST_OBJECT_CONTAINS_MALFORMED_DATA, violations);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApiCityServiceException.class)
    public ApiErrorResponse handleUnexpected(ApiCityServiceException exception) {
        log.error("Unexpected error: {}", exception.getMessage(), exception);
        return ApiErrorResponse.withMessage(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ApiErrorResponse handleUnexpected(Exception exception) {
        log.error("Unexpected Exception: {}", exception.getMessage(), exception);
        return ApiErrorResponse.withMessage(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class})
    public ApiErrorResponse handleUnexpected(RuntimeException exception) {
        log.error("Unexpected RuntimeException: {}", exception.getMessage(), exception);
        return ApiErrorResponse.withMessage(exception.getMessage());
    }
}
