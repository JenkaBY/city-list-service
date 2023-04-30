package by.jenka.service.service.impl;

import by.jenka.service.controller.model.request.CitySearchCriteria;
import by.jenka.service.persistance.entity.CityEntity;
import by.jenka.service.persistance.repository.CityRepository;
import by.jenka.service.service.exception.ResourceNotFoundException;
import by.jenka.service.service.mapper.CityMapper;
import by.jenka.service.service.mapper.CitySpecificationMapper;
import by.jenka.service.service.model.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {
    @Mock
    private CityMapper cityMapper;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CitySpecificationMapper citySpecificationMapper;
    @InjectMocks
    private CityServiceImpl underTest;

    @Test
    void findAllBySearchCriteria_Should_ReturnPageWithCities() {
        var searchCriteria = mock(CitySearchCriteria.class);
        var pageable = mock(Pageable.class);
        Specification<CityEntity> spec = (Specification<CityEntity>) mock(Specification.class);
        Page<CityEntity> pageEntities = (Page<CityEntity>) mock(Page.class);
        Page<City> pageModels = (Page<City>) mock(Page.class);
        when(citySpecificationMapper.fromSearchCriteria(searchCriteria)).thenReturn(spec);
        when(cityRepository.findAll(spec, pageable)).thenReturn(pageEntities);
        when(pageEntities.map(any(Function.class))).thenReturn(pageModels);

        var actual = underTest.findAllBySearchCriteria(searchCriteria, pageable);

        assertThat(actual).isNotNull();
    }

    @Test
    void update_Should_SaveUpdatedEntity_When_BeingUpdatedCityExists() {
        var withUpdate = mock(City.class);
        var id = 1L;
        var foundCityEntity = mock(CityEntity.class);
        var updatedCityEntity = mock(CityEntity.class);
        var updatedCity = mock(City.class);
        when(cityRepository.findById(id)).thenReturn(Optional.of(foundCityEntity));
        when(cityRepository.save(updatedCityEntity)).thenReturn(updatedCityEntity);
        when(cityMapper.updateTargetFields(foundCityEntity, withUpdate)).thenReturn(updatedCityEntity);
        when(cityMapper.fromEntity(updatedCityEntity)).thenReturn(updatedCity);

        var actual = underTest.update(id, withUpdate);

        assertThat(actual).isNotNull();
    }

    @Test
    void update_Should_ThrowResourceNotFound_When_BeingUpdatedCityDoesNotExist() {
        var withUpdate = mock(City.class);
        var id = 1L;
        when(cityRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.update(id, withUpdate))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resource City with Key %s not found".formatted(id));
    }
}
