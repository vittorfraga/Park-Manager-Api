package com.vittorfraga.estacionamentoapi.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vittorfraga.estacionamentoapi.domain.vehicle.Vehicle;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleRepository;
import com.vittorfraga.estacionamentoapi.domain.vehicle.VehicleType;
import com.vittorfraga.estacionamentoapi.usecases.vehicle.dto.VehicleRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VehicleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private VehicleRepository vehicleRepository;

    @AfterEach
    void tearDown() {
        this.vehicleRepository.deleteAll();
    }

    @Test
    void createVehicle() throws Exception {
        VehicleRequest vehicleRequest = new VehicleRequest("Brand", "Model", "AB24R89", "verde", "CAR");

        String jsonInput = objectMapper.writeValueAsString(vehicleRequest);

        mockMvc.perform(post("/vehicles")
                        .contentType("application/json")
                        .content(jsonInput))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.brand").value("Brand"))
                .andExpect(jsonPath("$.model").value("Model"))
                .andExpect(jsonPath("$.licensePlate").value("AB24R89"))
                .andExpect(jsonPath("$.color").value("verde"))
                .andExpect(jsonPath("$.type").value("CAR"));
    }

    @Test
    void listVehicles() throws Exception {
        Vehicle vehicle1 = new Vehicle("Brand1", "Model1", "AG24R89", "verde", VehicleType.CAR);
        Vehicle vehicle2 = new Vehicle("Brand2", "Model2", "AG24R90", "verde", VehicleType.CAR);
        Vehicle vehicle3 = new Vehicle("Brand3", "Model3", "AG24R91", "verde", VehicleType.MOTORCYCLE);

        this.vehicleRepository.saveAll(List.of(vehicle1, vehicle2, vehicle3));


        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3))
                .andExpect(jsonPath("$.content[0].brand").value("Brand1"))
                .andExpect(jsonPath("$.content[1].brand").value("Brand2"));


    }

    @Test
    void getVehicleByLicensePlate() throws Exception {
        Vehicle vehicle1 = new Vehicle("Brand1", "Model1", "AG24R89", "verde", VehicleType.CAR);
        this.vehicleRepository.save(vehicle1);

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
        Vehicle vehicle1 = new Vehicle("Brand1", "Model1", "AG24R89", "verde", VehicleType.CAR);
        final var savedVehicle = this.vehicleRepository.save(vehicle1);
        System.out.println("veiculo salvo: " + savedVehicle);
        final var vehicleRequest = new VehicleRequest("Brand Updated", "Model2", "AG24R89", "verde", "MOTORCYCLE");

        String jsonInput = objectMapper.writeValueAsString(vehicleRequest);

        mockMvc.perform(put("/vehicles/" + savedVehicle.getId())
                        .contentType("application/json")
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Brand Updated"))
                .andExpect(jsonPath("$.model").value("Model2"))
                .andExpect(jsonPath("$.licensePlate").value("AG24R89"))
                .andExpect(jsonPath("$.color").value("verde"))
                .andExpect(jsonPath("$.type").value("MOTORCYCLE"))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()));

    }

    @Test
    void deleteVehicleById() throws Exception {
        Vehicle vehicle1 = new Vehicle("Brand1", "Model1", "AG24R89", "verde", VehicleType.CAR);
        final var vehicleSaved = this.vehicleRepository.save(vehicle1);
        mockMvc.perform(delete("/vehicles/" + vehicleSaved.getId()))
                .andExpect(status().isNoContent());
    }
}