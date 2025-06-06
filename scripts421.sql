-- Добавление ограничения: возраст студента не менее 16 лет
ALTER TABLE student
ADD CONSTRAINT check_student_age CHECK (age >= 16);

-- Добавление ограничений: имя студента уникально и не NULL
ALTER TABLE student
ADD CONSTRAINT unique_student_name UNIQUE (name),
ALTER COLUMN name SET NOT NULL;

-- Добавление ограничения: пара "название факультета" и "цвет" уникальна
ALTER TABLE faculty
ADD CONSTRAINT unique_name_color UNIQUE (name, color);

-- Установка значения по умолчанию для возраста студента (20 лет)
ALTER TABLE student
ALTER COLUMN age SET DEFAULT 20;