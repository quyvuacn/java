CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name NVARCHAR(100),
    birthday NVARCHAR(50),
    address NVARCHAR(200),
    position NVARCHAR(50),
    department NVARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);