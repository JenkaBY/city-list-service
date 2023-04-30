package by.jenka.service.service.mapper;

import by.jenka.service.persistance.entity.CityEntity;
import by.jenka.service.service.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface CityMapper {

    City fromEntity(CityEntity source);

    default CityEntity updateTargetFields(CityEntity target, City source) {
        return target.toBuilder()
                .name(source.name())
                .photo(source.photo())
                .build();
    }
}
