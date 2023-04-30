package by.jenka.service.controller.rest;

import by.jenka.service.controller.mapper.CityRequestResponseMapper;
import by.jenka.service.controller.model.request.CityRequest;
import by.jenka.service.controller.model.request.CitySearchCriteria;
import by.jenka.service.controller.model.response.CityResponse;
import by.jenka.service.service.CityService;
import by.jenka.service.service.model.City;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CityRestController.class)
class CityRestControllerTest {
    private static final String CITY_API_URL = "/api/v1/cities";

    @MockBean
    private CityService cityService;
    @MockBean
    private CityRequestResponseMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class FindBySearchCriteria {

        @Test
        void should_Return_Ok_When_PageRequestParamsArePresent() throws Exception {
            var page = spy(Page.<City>empty());
            int pageNumber = 1;
            int pageSize = 5;
            when(cityService.findAllBySearchCriteria(new CitySearchCriteria(null, null),
                    PageRequest.of(pageNumber, pageSize, Sort.unsorted()))).thenReturn(page);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("page", "1");
            params.add("size", "5");
            mockMvc.perform(MockMvcRequestBuilders
                            .get(CITY_API_URL).params(params)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content").isEmpty())
                    .andExpect(jsonPath("$.totalPages").value(1))
                    .andExpect(jsonPath("$.totalElements").value(0))
                    .andExpect(jsonPath("$.size").value(0))
                    .andExpect(jsonPath("$.number").value(0));
        }

        @Test
        void should_Return_Ok_When_PageRequestParamsAreAbsent() throws Exception {
            var page = spy(Page.<City>empty());

            when(cityService.findAllBySearchCriteria(new CitySearchCriteria(null, null),
                    Pageable.ofSize(20))).thenReturn(page);

            mockMvc.perform(MockMvcRequestBuilders
                            .get(CITY_API_URL)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content").isEmpty())
                    .andExpect(jsonPath("$.totalPages").value(1))
                    .andExpect(jsonPath("$.totalElements").value(0))
                    .andExpect(jsonPath("$.size").value(0))
                    .andExpect(jsonPath("$.number").value(0));
        }
    }

    @Nested
    class Update {

        private static final String NAME_TEMPLATED_CITY_PAYLOAD_TEMPLATE = """
                     {
                        "name" : %s,
                        "photo" : "url"
                     }
                """;
        private static final String PHOTO_TEMPLATED_CITY_PAYLOAD_TEMPLATE = """
                     {
                        "name" : "cityName",
                        "photo" : %s
                     }
                """;

        private static final String VALID_REQUEST = NAME_TEMPLATED_CITY_PAYLOAD_TEMPLATE.formatted("\"name\"");

        @Nested
        class Fail {

            @ParameterizedTest
            @NullAndEmptySource
            void when_NameIsEmpty(String name) throws Exception {
                var cityId = 1;
                mockMvc.perform(MockMvcRequestBuilders
                                .put(CITY_API_URL + "/" + cityId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(NAME_TEMPLATED_CITY_PAYLOAD_TEMPLATE.formatted(Optional.ofNullable(name)
                                        .map("\"%s\""::formatted).orElse(name)))
                        ).andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("Request object contains malformed data"))
                        .andExpect(jsonPath("$.violations[0].field").value("cityRequest.name"))
                        .andExpect(jsonPath("$.violations[0].message").value("must not be blank"));
            }

            @ParameterizedTest
            @NullAndEmptySource
            void when_UrlIsEmpty(String photoUrl) throws Exception {
                var cityId = 1;
                mockMvc.perform(MockMvcRequestBuilders
                                .put(CITY_API_URL + "/" + cityId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(PHOTO_TEMPLATED_CITY_PAYLOAD_TEMPLATE.formatted(Optional.ofNullable(photoUrl)
                                        .map("\"%s\""::formatted).orElse(photoUrl)))
                        ).andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("Request object contains malformed data"))
                        .andExpect(jsonPath("$.violations[0].field").value("cityRequest.photo"))
                        .andExpect(jsonPath("$.violations[0].message").value("must not be blank"));
            }

            @ParameterizedTest
            @CsvSource({"-1", "0"})
            void when_PathIsInvalid(String cityId) throws Exception {
                mockMvc.perform(MockMvcRequestBuilders
                                .put(CITY_API_URL + "/" + cityId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(VALID_REQUEST)
                        ).andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value(containsString("URL is malformed")))
                        .andExpect(jsonPath("$.violations[0].field").isNotEmpty())
                        .andExpect(jsonPath("$.violations[0].message").value("The cityId must be greater than 0"));
            }
        }

        @Nested
        class Success {
            private static final String CITY_RESPONSE = """
                         {
                            "id" : 1,
                            "name" : "UpdatedName",
                            "photo" : "url"
                         }
                    """;

            @Test
            void when_RequestIsValid() throws Exception {
                var cityId = 1L;
                var cityModel = mock(City.class);
                var cityUpdatedModel = mock(City.class);
                when(mapper.toModel(any(CityRequest.class))).thenReturn(cityModel);
                when(cityService.update(cityId, cityModel)).thenReturn(cityUpdatedModel);
                var response = objectMapper.readValue(CITY_RESPONSE, CityResponse.class);
                when(mapper.toResponse(cityUpdatedModel)).thenReturn(response);

                mockMvc.perform(MockMvcRequestBuilders
                                .put(CITY_API_URL + "/" + cityId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(VALID_REQUEST)
                        ).andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(cityId))
                        .andExpect(jsonPath("$.name").value("UpdatedName"))
                        .andExpect(jsonPath("$.photo").value("url"));
            }
        }
    }
}
