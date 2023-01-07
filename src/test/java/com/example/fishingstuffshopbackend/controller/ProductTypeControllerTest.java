package com.example.fishingstuffshopbackend.controller;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(controllers = {CategoryController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductTypeControllerTest {
    private static final String URL = "/api/v1/category";
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private CategoryService service;
//
//    @Test
//    @DisplayName("Method getAll() works wright")
//    @Order(1)
//    void getAll_ReturnProductTypeList() throws Exception {
//        given(service.findAll()).willReturn(
//                Arrays.asList(
//                        new ProductTypeDto(1L, "Hooks"),
//                        new ProductTypeDto(2L, "Poles")
//                )
//        );
//
//        mvc.perform(get(URL))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.[0].id", is(1)))
//                .andExpect(jsonPath("$.[0].name", is("Hooks")))
//                .andExpect(jsonPath("$.[1].id", is(2)))
//                .andExpect(jsonPath("$.[1].name", is("Poles")));
//    }
//
//    @Test
//    @DisplayName("Method getById() should return ProductType")
//    @Order(2)
//    void getById_ReturnProductType() throws Exception {
//        Long id = 1L;
//        given(service.findById(id)).willReturn(
//                        new ProductTypeDto(id, "Hooks")
//        );
//
//        mvc.perform(get(URL + "/" + id))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.id", is(id.intValue())))
//                .andExpect(jsonPath("$.name", is("Hooks")));
//    }
//
//    @Test
//    @DisplayName("Method getById() should return an error, when it can't find ProductType")
//    @Order(3)
//    void getById_ReturnError_WhenCannotFindProductTypeWithGivenId() throws Exception {
//        Long id = 100500L;
//        given(service.findById(id)).willThrow(new ProductTypeNotFoundException(id));
//
//        mvc.perform(get(URL + "/" + id))
//                .andDo(print())
//                .andExpect(status().is4xxClientError())
//                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.timestamp", anything()))
//                .andExpect(jsonPath("$.status", is(404)))
//                .andExpect(jsonPath("$.error", is("Not Found")))
//                .andExpect(jsonPath("$.message", is("Couldn't find product type with id=" + id)))
//                .andExpect(jsonPath("$.path", is(URL + "/" + id)));
//    }
//
//    @Test
//    @DisplayName("Method create() works wright")
//    @Order(4)
//    void create_ReturnCreatedProductType() throws Exception {
//        Long id = 1L;
//        String name = "Poles";
//        given(service.create(name)).willReturn(new ProductTypeDto(id, name));
//
//        mvc.perform(post(URL).param("typeName", name))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.id", is(id.intValue())))
//                .andExpect(jsonPath("$.name", is(name)));
//    }
//
//    @Test
//    @DisplayName("Method create() should return an error, when typeName parameter is missing")
//    @Order(5)
//    void create_ReturnError_WhenTypeNameParameterIsMissing() throws Exception {
//        mvc.perform(post(URL))
//                .andDo(print())
//                .andExpect(status().is4xxClientError())
//                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.timestamp", anything()))
//                .andExpect(jsonPath("$.status", is(400)))
//                .andExpect(jsonPath("$.error", is("Bad Request")))
//                .andExpect(jsonPath("$.message", is("Required parameter typeName is missing or blank")))
//                .andExpect(jsonPath("$.path", is(URL)));
//    }
//
//    @Test
//    @DisplayName("Method create() should return an error, when typeName parameter is blank")
//    @Order(6)
//    void create_ReturnError_WhenTypeNameParameterIsBlank() throws Exception {
//        mvc.perform(post(URL).param("typeName", " "))
//                .andDo(print())
//                .andExpect(status().is4xxClientError())
//                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.timestamp", anything()))
//                .andExpect(jsonPath("$.status", is(400)))
//                .andExpect(jsonPath("$.error", is("Bad Request")))
//                .andExpect(jsonPath("$.message", is("Required parameter typeName is missing or blank")))
//                .andExpect(jsonPath("$.path", is(URL)));
//    }
}