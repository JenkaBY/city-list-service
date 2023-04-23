package by.jenka.service.persistance.specification;

import by.jenka.service.persistance.entity.CityEntity;
import net.kaczmarzyk.spring.data.jpa.domain.StartingWithIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = SpecConst.CityParam.NAME, spec = StartingWithIgnoreCase.class)
public interface CityStartsIgnoreCaseCriteria extends Specification<CityEntity> {
}
