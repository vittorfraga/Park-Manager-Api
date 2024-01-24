create table if not exists establishment
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255),
    cnpj             VARCHAR(14),
    address          VARCHAR(255),
    phone            VARCHAR(30),
    motorcycle_slots INTEGER,
    car_slots        INTEGER
)