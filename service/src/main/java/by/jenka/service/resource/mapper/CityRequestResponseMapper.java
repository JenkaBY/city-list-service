package by.jenka.service.resource.mapper;

import by.jenka.service.resource.model.request.CityRequest;
import by.jenka.service.resource.model.response.CityResponse;
import by.jenka.service.service.model.City;
import org.mapstruct.Mapper;

@Mapper
public interface CityRequestResponseMapper {

    City toModel(CityRequest source);

    CityResponse toResponse(City source);
}
