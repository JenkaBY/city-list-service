package by.jenka.service.persistance.repository;

import by.jenka.service.persistance.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CityRepository extends PagingAndSortingRepository<CityEntity, Long>,
        JpaSpecificationExecutor<CityEntity>, JpaRepository<CityEntity, Long> {
}
