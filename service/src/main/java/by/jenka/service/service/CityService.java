package by.jenka.service.service;

import by.jenka.service.controller.model.request.CitySearchCriteria;
import by.jenka.service.service.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

    Page<City> findAllBySearchCriteria(CitySearchCriteria citySearchCriteria, Pageable pageable);

    City findById(Long id);

    City update(Long id, City toBeSaved);
}
