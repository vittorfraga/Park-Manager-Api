CREATE TABLE if not exists access_table
(
    id               SERIAL PRIMARY KEY,
    vehicle_plate    VARCHAR(255) NOT NULL,
    establishment_id BIGINT       NOT NULL,
    event_type       VARCHAR(255) NOT NULL,
    vehicle_type     VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP    NOT NULL,
    FOREIGN KEY (establishment_id) REFERENCES establishment (id),
    FOREIGN KEY (vehicle_plate) REFERENCES vehicle (license_plate)
);