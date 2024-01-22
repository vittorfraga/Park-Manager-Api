package com.vittorfraga.estacionamentoapi.http.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vittorfraga.estacionamentoapi.usecases.establishment.dtos.EstablishmentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EstablishmentControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EstablishmentController establishmentController;

    @Test
    public void testCreateEstablishment() throws Exception {
        EstablishmentRequest establishmentRequest = new EstablishmentRequest("Test Establishment", "12345678901234", "123456789", "Test Address", 5, 10);

        String jsonInput = objectMapper.writeValueAsString(establishmentRequest);

        mockMvc.perform(post("/establishments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Establishment"))
                .andExpect(jsonPath("$.cnpj").value("12345678901234"))
                .andExpect(jsonPath("$.address").value("Test Address"))
                .andExpect(jsonPath("$.phone").value("123456789"))
                .andExpect(jsonPath("$.motorcycleSlots").value(5))
                .andExpect(jsonPath("$.carSlots").value(10))
                .andExpect(jsonPath("$.id").exists()
                );
    }


    @Test
    void listEstablishments() throws Exception {

        this.establishmentController.createEstablishment(new EstablishmentRequest("Test Establishment1", "12345678901234", "123456789", "Test Address", 1, 10));
        this.establishmentController.createEstablishment(new EstablishmentRequest("Test Establishment2", "12345678901234", "123456789", "Test Address", 2, 10));
        this.establishmentController.createEstablishment(new EstablishmentRequest("Test Establishment3", "12345678901234", "123456789", "Test Address", 3, 10));
        this.establishmentController.createEstablishment(new EstablishmentRequest("Test Establishment4", "12345678901234", "123456789", "Test Address", 4, 10));

        mockMvc.perform(get("/establishments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(4))
                .andExpect(jsonPath("$.content[0].name").value("Test Establishment1"))
                .andExpect(jsonPath("$.content[1].name").value("Test Establishment2"))
                .andExpect(jsonPath("$.content[2].name").value("Test Establishment3"))
                .andExpect(jsonPath("$.content[3].name").value("Test Establishment4"));

    }

    @Test
    void getEstablishmentById() throws Exception {
        this.establishmentController.createEstablishment(new EstablishmentRequest("Test Establishment1", "12345678901234", "123456789", "Test Address", 1, 10));

        mockMvc.perform(get("/establishments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Establishment1"))
                .andExpect(jsonPath("$.cnpj").value("12345678901234"))
                .andExpect(jsonPath("$.address").value("Test Address"))
                .andExpect(jsonPath("$.phone").value("123456789"))
                .andExpect(jsonPath("$.motorcycleSlots").value(1))
                .andExpect(jsonPath("$.carSlots").value(10))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.id").exists()
                );


    }

    @Test
    void updateEstablishmentById() throws Exception {
        this.establishmentController.createEstablishment(new EstablishmentRequest("Test Establishment1", "12345678901234", "123456789", "Test Address", 1, 10));

        EstablishmentRequest establishmentRequest = new EstablishmentRequest("Establishment Updated", "12345678901211", "123456789", "Test Address Updated", 20, 30);

        String jsonInput = objectMapper.writeValueAsString(establishmentRequest);


        mockMvc.perform(put("/establishments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Establishment Updated"))
                .andExpect(jsonPath("$.cnpj").value("12345678901211"))
                .andExpect(jsonPath("$.address").value("Test Address Updated"))
                .andExpect(jsonPath("$.phone").value("123456789"))
                .andExpect(jsonPath("$.motorcycleSlots").value(20))
                .andExpect(jsonPath("$.carSlots").value(30));
    }

    @Test
    void deleteEstablishmentById() throws Exception {
        this.establishmentController.createEstablishment(new EstablishmentRequest("Test Establishment1", "12345678901234", "123456789", "Test Address", 1, 10));

        mockMvc.perform(delete("/establishments/1"))
                .andExpect(status().isNoContent());
    }
}