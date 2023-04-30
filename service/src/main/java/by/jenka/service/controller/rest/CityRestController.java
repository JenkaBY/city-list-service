package by.jenka.service.controller.rest;

import by.jenka.service.controller.mapper.CityRequestResponseMapper;
import by.jenka.service.controller.model.request.CityRequest;
import by.jenka.service.controller.model.request.CitySearchCriteria;
import by.jenka.service.controller.model.response.CityResponse;
import by.jenka.service.service.CityService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cities")
public class CityRestController {

    private final CityService cityService;
    private final CityRequestResponseMapper mapper;

    @ApiResponse(responseCode = "200",
            description = "Return pageable list of cities. Allows to specify the search criteria for filtering." +
                    "Additionally supports search type")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<CityResponse> findAllBySearchCriteria(
            @ParameterObject @Nullable CitySearchCriteria citySearchCriteria,
            @ParameterObject Pageable pageableRequest) {
        log.info("Find cities by criteria {} and page {}", citySearchCriteria, pageableRequest);
        return cityService.findAllBySearchCriteria(citySearchCriteria, pageableRequest)
                .map(mapper::toResponse);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return a city"),
            @ApiResponse(responseCode = "404", description = "The city with specified id was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CityResponse find(@PathVariable @Positive @NotNull Long id) {
        log.info("Find city by id {}", id);
        return mapper.toResponse(cityService.findById(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{cityId}")
    public CityResponse update(@PathVariable @Positive(message = "{validation.cityId.positive}") Long cityId,
                               @RequestBody @NotNull @Valid CityRequest city) {
        log.info("Update city[cityId={}] request", cityId);
        return mapper.toResponse(cityService.update(cityId, mapper.toModel(city)));
    }
}
