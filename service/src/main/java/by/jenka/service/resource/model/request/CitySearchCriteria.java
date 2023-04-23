package by.jenka.service.resource.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record CitySearchCriteria(
        @Schema(description = "Search parameters to search the city")
        @Nullable String name,
        @Schema(description = "Allows to look up by starting " +
                "or any entry of characters passed in the name params")
        @NotNull SearchType searchType) {

}
