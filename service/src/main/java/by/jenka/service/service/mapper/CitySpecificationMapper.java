package by.jenka.service.service.mapper;

import by.jenka.service.controller.model.request.CitySearchCriteria;
import by.jenka.service.persistance.entity.CityEntity;
import by.jenka.service.persistance.specification.SpecConst;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.utils.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CitySpecificationMapper {

    private final SpecificationTypeMapper specificationTypeMapper;

    public Specification<CityEntity> fromSearchCriteria(@Nullable CitySearchCriteria citySearchCriteria) {
        var searchCriteriaOptional = Optional.ofNullable(citySearchCriteria);
        var specClass = specificationTypeMapper.toSpecClassOrDefault(searchCriteriaOptional.map(CitySearchCriteria::searchType).orElse(null));
        return SpecificationBuilder.specification(specClass)
                .withParam(SpecConst.CityParam.NAME,
                        searchCriteriaOptional.map(CitySearchCriteria::name).orElse(null))
                .build();
    }
}
