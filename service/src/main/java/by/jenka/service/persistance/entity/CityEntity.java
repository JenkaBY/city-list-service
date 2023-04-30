package by.jenka.service.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "city")
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
