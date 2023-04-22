package by.jenka.service.service.impl;

import by.jenka.service.persistance.entity.CityEntity;
import by.jenka.service.persistance.repository.CityRepository;
import by.jenka.service.resource.model.request.CitySearchCriteria;
import by.jenka.service.service.CityService;
import by.jenka.service.service.exception.ResourceNotFoundException;
import by.jenka.service.service.mapper.CityMapper;
import by.jenka.service.service.mapper.CitySpecificationMapper;
import by.jenka.service.service.model.City;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    private final CityMapper cityMapper;
    private final CityRepository cityRepository;
    private final CitySpecificationMapper citySpecificationMapper;

    @Override
    public Page<City> findAllBySearchCriteria(CitySearchCriteria citySearchCriteria, Pageable pageable) {
        var citySpec = citySpecificationMapper.fromSearchCriteria(citySearchCriteria);
        return cityRepository.findAll(citySpec, pageable)
                .map(cityMapper::fromEntity);
    }

    @Transactional
    @Override
    public City update(Long id, City withUpdates) {
        var updatedEntity = cityRepository.save(cityMapper.updateTargetFields(getCity(id), withUpdates));
        return cityMapper.fromEntity(updatedEntity);
    }

    private CityEntity getCity(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(City.class, id));
    }
}
