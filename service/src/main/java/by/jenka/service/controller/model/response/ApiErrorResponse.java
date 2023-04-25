package by.jenka.service.controller.model.response;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.Instant;
import java.util.List;


@Getter
public class ApiErrorResponse {
    @NotNull
    private final String message;
    @Nullable
    private final List<ViolationResponse> violations;

    private final Instant timestamp = Instant.now();

    public ApiErrorResponse(String message, @Nullable List<ViolationResponse> violations) {
        this.message = message;
        this.violations = violations;
    }

    public static ApiErrorResponse withMessage(String message) {
        return new ApiErrorResponse(message, null);
    }
}
