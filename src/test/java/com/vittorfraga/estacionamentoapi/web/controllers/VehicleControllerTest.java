package com.vittorfraga.estacionamentoapi.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.dto.VehicleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class VehicleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    VehicleController vehicleController;

    @Test
    void createVehicle() throws Exception {
        VehicleRequest vehicleRequest = new VehicleRequest("Brand", "Model", "AG24R89", "verde", "CAR");

        String jsonInput = objectMapper.writeValueAsString(vehicleRequest);

        mockMvc.perform(post("/vehicles")
                        .contentType("application/json")
                        .content(jsonInput))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.brand").value("Brand"))
                .andExpect(jsonPath("$.model").value("Model"))
                .andExpect(jsonPath("$.licensePlate").value("AG24R89"))
                .andExpect(jsonPath("$.color").value("verde"))
                .andExpect(jsonPath("$.type").value("CAR"));
    }

    @Test
    void listVehicles() throws Exception {
        this.vehicleController.createVehicle(new VehicleRequest("Brand1", "Model1", "AG24R89", "verde", "CAR"));
        this.vehicleController.createVehicle(new VehicleRequest("Brand2", "Model1", "AG24R87", "verde", "CAR"));

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].brand").value("Brand1"))
                .andExpect(jsonPath("$.content[1].brand").value("Brand2"));


    }

    @Test
    void getVehicleByLicensePlate() throws Exception {
        this.vehicleController.createVehicle(new VehicleRequest("Brand1", "Model1", "AG24R89", "verde", "CAR"));

        mockMvc.perform(get("/vehicles/AG24R89"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Brand1"))
                .andExpect(jsonPath("$.model").value("Model1"))
                .andExpect(jsonPath("$.licensePlate").value("AG24R89"))
                .andExpect(jsonPath("$.color").value("verde"))
                .andExpect(jsonPath("$.type").value("CAR"));
    }

    @Test
    void updateVehicle() throws Exception {
        this.vehicleController.createVehicle(new VehicleRequest("Brand1", "Model1", "AG24R89", "verde", "CAR"));

        final var vehicleRequest = new VehicleRequest("Brand1 Updated", "Model2", "AG24R89", "verde", "MOTORCYCLE");

        String jsonInput = objectMapper.writeValueAsString(vehicleRequest);

        mockMvc.perform(put("/vehicles/1")
                        .contentType("application/json")
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Brand1 Updated"))
                .andExpect(jsonPath("$.model").value("Model2"))
                .andExpect(jsonPath("$.licensePlate").value("AG24R89"))
                .andExpect(jsonPath("$.color").value("verde"))
                .andExpect(jsonPath("$.type").value("MOTORCYCLE"));
    }

    @Test
    void deleteVehicleById() throws Exception {
        this.vehicleController.createVehicle(new VehicleRequest("Brand1", "Model1", "AG24R89", "verde", "CAR"));

        mockMvc.perform(delete("/vehicles/1"))
                .andExpect(status().isNoContent());

    }
}