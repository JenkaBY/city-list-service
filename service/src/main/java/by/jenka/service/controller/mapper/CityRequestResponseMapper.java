package by.jenka.service.controller.mapper;

import by.jenka.service.controller.model.request.CityRequest;
import by.jenka.service.controller.model.response.CityResponse;
import by.jenka.service.service.model.City;
import org.mapstruct.Mapper;

@Mapper
public interface CityRequestResponseMapper {

    City toModel(CityRequest source);

    CityResponse toResponse(City source);
}
