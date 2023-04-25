package by.jenka.service.controller.model.request;

import jakarta.validation.constraints.NotBlank;

public record CityRequest(@NotBlank String name, String photo) {
}
