package by.jenka.service.persistance.repository;

import by.jenka.service.persistance.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
}
