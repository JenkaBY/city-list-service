package by.jenka.service.controller.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

public record CitySearchCriteria(
        @Schema(description = "Search parameters to search the city")
        @Nullable String name,
        @Schema(description = "Allows to look up by starting " +
                "or any entry of characters passed in the name params. If not specified, the default is 'LIKE_IGNORE_CASE'",
                allowableValues = {"LIKE_IGNORE_CASE", "STARTS_WITH_IGNORE_CASE"},
                defaultValue = "LIKE_IGNORE_CASE")
        SearchType searchType) {

}
