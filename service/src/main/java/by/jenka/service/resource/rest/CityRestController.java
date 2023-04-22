package by.jenka.service.resource.rest;

import by.jenka.service.resource.mapper.CityRequestResponseMapper;
import by.jenka.service.resource.model.request.CityRequest;
import by.jenka.service.resource.model.response.CityResponse;
import by.jenka.service.service.CityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cities")
public class CityRestController {

    private final CityService cityService;
    private final CityRequestResponseMapper mapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CityResponse> getAll() {
        log.info("Get all cities");
        return cityService.getAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{cityId}")
    public CityResponse update(@PathVariable @Positive Long cityId,
                               @RequestBody @NotNull @Valid CityRequest city) {
        log.info("Update city[cityId={}] request", cityId);
        return mapper.toResponse(cityService.update(cityId, mapper.toModel(city)));
    }
}
