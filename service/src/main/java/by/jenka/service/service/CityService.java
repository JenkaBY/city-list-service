package by.jenka.service.service;

import by.jenka.service.service.model.City;

import java.util.List;

public interface CityService {

    List<City> getAll();

    City update(Long id, City toBeSaved);
}
