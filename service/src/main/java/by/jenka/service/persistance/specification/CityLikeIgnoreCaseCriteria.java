package by.jenka.service.persistance.specification;

import by.jenka.service.persistance.entity.CityEntity;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = SpecConst.CityParam.NAME, spec = LikeIgnoreCase.class)
public interface CityLikeIgnoreCaseCriteria extends Specification<CityEntity> {
}
