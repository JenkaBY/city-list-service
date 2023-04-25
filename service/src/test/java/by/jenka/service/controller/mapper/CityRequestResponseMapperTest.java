package by.jenka.service.controller.mapper;

import by.jenka.service.controller.model.request.CityRequest;
import by.jenka.service.controller.model.response.CityResponse;
import by.jenka.service.service.model.City;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CityRequestResponseMapperTest {

    private final CityRequestResponseMapper underTest = new CityRequestResponseMapperImpl();
    private final City model = new City(1L, "Name", "photoUrl");
    private final CityRequest request = new CityRequest("Name", "photoUrl");
    private final CityResponse response = new CityResponse(1L, "Name", "photoUrl");

    @Test
    void toModel() {
        var actual = underTest.toModel(request);

        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(model);
    }

    @Test
    void toResponse() {
        var actual = underTest.toResponse(model);

        assertThat(actual).usingRecursiveComparison().isEqualTo(response);
    }
}