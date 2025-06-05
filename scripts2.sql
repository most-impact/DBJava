-- Создание таблицы для людей
CREATE TABLE person (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER CHECK (age >= 0),
    has_license BOOLEAN NOT NULL
);

-- Создание таблицы для машин
CREATE TABLE car (
     id BIGSERIAL PRIMARY KEY,
     brand VARCHAR(255) NOT NULL,
     model VARCHAR(255) NOT NULL,
     price DECIMAL(10, 2) CHECK (price >= 0)
);

-- Создание промежуточной таблицы для связи человек-машина
CREATE TABLE person_car (
    person_id BIGINT REFERENCES person(id) ON DELETE CASCADE,
    car_id BIGINT REFERENCES car(id) ON DELETE CASCADE,
    PRIMARY KEY (person_id, car_id)
);