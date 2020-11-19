create table fazenda (
id UUID NOT NULL PRIMARY KEY,
nome character varying (255) NOT NULL,
cnpj character varying(14) NOT NULL,
cidade character varying(100) NOT NULL,
uf character varying(2) NOT NULL,
logradouro character varying(255) NOT NULL
);