## Project structure
Create This project for Company where Use 2 frontend (User, Admin)

## Technology
Use Oarth1 and Oarth2 both

## DataBase
### for types of service

CREATE TABLE services (
id SERIAL PRIMARY KEY,
type TEXT,
details TEXT,
headline TEXT,
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
is_deleted BOOLEAN DEFAULT FALSE
);

