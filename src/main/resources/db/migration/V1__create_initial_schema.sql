
CREATE TABLE employee (
    id BIGINT NOT NULL AUTO_INCREMENT,
    department VARCHAR(255) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    employee_number INT DEFAULT NULL,
    name VARCHAR(255) DEFAULT NULL,
    position VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE user_details (
    id BIGINT NOT NULL AUTO_INCREMENT,
    password VARCHAR(255) DEFAULT NULL,
    role VARCHAR(255) DEFAULT NULL,
    username VARCHAR(255) DEFAULT NULL,
    employee_id BIGINT DEFAULT NULL,

    PRIMARY KEY (id),

    UNIQUE KEY uk_user_employee (employee_id),

    CONSTRAINT fk_user_employee
        FOREIGN KEY (employee_id)
        REFERENCES employee(id)
) ENGINE=InnoDB;

CREATE TABLE leave_requests (
    id BIGINT NOT NULL AUTO_INCREMENT,
    cause VARCHAR(255) DEFAULT NULL,
    enddate DATE DEFAULT NULL,
    leave_type VARCHAR(255) DEFAULT NULL,
    startdate DATE DEFAULT NULL,
    status VARCHAR(255) DEFAULT NULL,
    employee_id BIGINT DEFAULT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_leave_employee
        FOREIGN KEY (employee_id)
        REFERENCES employee(id)
) ENGINE=InnoDB;