DROP DATABASE surveymanagement;
CREATE DATABASE IF NOT EXISTS surveymanagement;
USE surveymanagement;

-- Tabla surveys
CREATE TABLE surveys (
    id INT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    description VARCHAR(255),
    name VARCHAR(255) NOT NULL
);

-- Tabla chapter
CREATE TABLE chapter (
    id INT PRIMARY KEY AUTO_INCREMENT,
    survey_id INT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    chapter_number VARCHAR(50),
    chapter_title VARCHAR(50),
    FOREIGN KEY (survey_id) REFERENCES surveys(id)
);

-- Tabla questions
CREATE TABLE questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    chapter_id INT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    question_number VARCHAR(10),
    response_type VARCHAR(20),
    comment_question TEXT,
    question_text TEXT,
    FOREIGN KEY (chapter_id) REFERENCES chapter(id),
    CHECK (response_type IN ('single_choice', 'multiple_choice', 'open_text')) -- Ejemplo de verificación
);

-- Tabla categories_catalog
CREATE TABLE categories_catalog (
    id INT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    name VARCHAR(255) NOT NULL
);

-- Tabla response_options
CREATE TABLE response_options (
    id INT PRIMARY KEY AUTO_INCREMENT,
    option_value INT,
    categorycatalog_id INT,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    parentresponse_id INT,
    question_id INT NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    comment_response TEXT,
    option_text TEXT,
    FOREIGN KEY (question_id) REFERENCES questions(id),
    FOREIGN KEY (categorycatalog_id) REFERENCES categories_catalog(id),
    FOREIGN KEY (parentresponse_id) REFERENCES response_options(id)
);

-- Tabla subresponse_options
CREATE TABLE subresponse_options (
    id INT PRIMARY KEY AUTO_INCREMENT,
    subresponse_number INT,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    responseoptions_id INT,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    subresponse_text VARCHAR(255),
    FOREIGN KEY (responseoptions_id) REFERENCES response_options(id)
);

-- Tabla response_question
CREATE TABLE response_question (
    id INT PRIMARY KEY AUTO_INCREMENT,
    response_id INT,
    subresponses_id INT,
    responsetext VARCHAR(80),
    FOREIGN KEY (response_id) REFERENCES response_options(id),
    FOREIGN KEY (subresponses_id) REFERENCES subresponse_options(id)
);

-- Tabla roles
CREATE TABLE roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

-- Tabla users
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    enabled BOOLEAN NOT NULL,
    username VARCHAR(12) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Tabla users_roles
CREATE TABLE users_roles (
    role_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    PRIMARY KEY (role_id, user_id)
);

DELIMITER $$

CREATE TRIGGER after_user_insert
AFTER INSERT ON users
FOR EACH ROW
BEGIN
    INSERT INTO users_roles (role_id, user_id)
    VALUES (2, NEW.id);
END$$

DELIMITER ;


