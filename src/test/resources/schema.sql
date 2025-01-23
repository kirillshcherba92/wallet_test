CREATE TABLE IF NOT EXISTS wallet(
    id varchar(36) PRIMARY KEY not null ,
    amount int
);

CREATE TABLE IF NOT EXISTS wallet_operation(
id SERIAL PRIMARY KEY,
operation_type varchar(20) not null,
amount int not null ,
id_wallet varchar,
FOREIGN KEY (id_wallet) REFERENCES wallet(id) ON DELETE CASCADE
);