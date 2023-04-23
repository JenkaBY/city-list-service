package by.jenka.service.service.mapper;

import by.jenka.service.persistance.entity.CityEntity;
import by.jenka.service.persistance.specification.CityLikeIgnoreCaseCriteria;
import by.jenka.service.persistance.specification.CityStartsIgnoreCaseCriteria;
import by.jenka.service.resource.model.request.SearchType;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpecificationTypeMapper {

    private static final Map<SearchType, Class<? extends Specification<CityEntity>>> CITY_SPEC_BY_TYPE =
            Map.ofEntries(
                    Map.entry(SearchType.LIKE_IGNORE_CASE, CityLikeIgnoreCaseCriteria.class),
                    Map.entry(SearchType.STARTS_WITH_IGNORE_CASE, CityStartsIgnoreCaseCriteria.class)
            );

    @Nonnull
    public Class<? extends Specification<CityEntity>> toSpecClassOrDefault(@Nullable SearchType type) {
        return CITY_SPEC_BY_TYPE.getOrDefault(type, CityLikeIgnoreCaseCriteria.class);
    }
}
