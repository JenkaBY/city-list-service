package by.jenka.service.service.mapper;


import by.jenka.service.persistance.entity.CityEntity;
import by.jenka.service.service.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface CityMapper {

    CityEntity toEntity(City source);

    City fromEntity(CityEntity source);

    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "photo", source = "source.photo")
    @Mapping(target = "id", ignore = true)
    CityEntity updateTargetFields(@MappingTarget CityEntity target, City source);
}
