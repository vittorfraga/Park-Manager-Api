CREATE TABLE if not exists slots_manager
(
    id                                SERIAL PRIMARY KEY,
    establishment_id                  BIGINT  NOT NULL,
    motorcycle_slots                  INTEGER NOT NULL,
    car_slots                         INTEGER NOT NULL,
    current_occupied_car_slots        INTEGER NOT NULL,
    current_occupied_motorcycle_slots INTEGER NOT NULL,
    FOREIGN KEY (establishment_id) REFERENCES establishment (id)
);



