package by.jenka.service.persistance.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "city")
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 65000)
    private String photo;

    @Override
    public int hashCode() {
        return 11;
    }

    @Override
    public boolean equals(Object another) {
        if (this == another)
            return true;
        if (another == null)
            return false;
        if (getClass() != another.getClass())
            return false;
        CityEntity other = (CityEntity) another;
        return id != null && id.equals(other.getId());
    }
}
