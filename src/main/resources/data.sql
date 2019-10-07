-- This will create some database objects as well some data on server init,
-- Springboot will execute all DML/DDL commands it gets on data.sql
-- more: https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html (78.3 Initialize a database)

--Clean all objects
--DROP ALL OBJECTS;

--Create a new table 'restaurante'
--CREATE TABLE restaurante (
--  id   Long AUTO_INCREMENT PRIMARY KEY,
--  nome VARCHAR(30),
--  endereco VARCHAR(50),
--  last_modification TIMESTAMP
--);

--Insert some restaurante's  data
INSERT INTO restaurante(id, nome, endereco) VALUES (1, 'Restaurante A', 'Av. Assis Brasil');
INSERT INTO restaurante(id, nome, endereco) VALUES (2, 'Restaurante B', 'Av. Ipiranga');
INSERT INTO restaurante(id, nome, endereco) VALUES (3, 'Restaurante C', 'Av. Bento Gonçalves');
INSERT INTO restaurante(id, nome, endereco) VALUES (4, 'Restaurante D', 'Rua José Bonifácio');
INSERT INTO restaurante(id, nome, endereco) VALUES (5, 'Restaurante E', 'Av. Protásio Alves');
INSERT INTO restaurante(id, nome, endereco) VALUES (6, 'Restaurante F', 'Av. Wenceslau Escobar');

--CREATE TABLE funcionario (
--  id   Long AUTO_INCREMENT PRIMARY KEY,
--  nome VARCHAR(20),
--  user VARCHAR(10),
--  senha VARCHAR(20),
--  last_modification TIMESTAMP
--);

INSERT INTO funcionario(id, nome, user, senha) VALUES (1, 'José', 'jose', '123');
INSERT INTO funcionario(id, nome, user, senha) VALUES (2, 'João',  'joao', '123');
INSERT INTO funcionario(id, nome, user, senha) VALUES (3, 'Marcos', 'marcos', '123');
INSERT INTO funcionario(id, nome, user, senha) VALUES (4, 'Patrícia', 'patricia', '123');
INSERT INTO funcionario(id, nome, user, senha) VALUES (5, 'Susi', 'susi', '123');
INSERT INTO funcionario(id, nome, user, senha) VALUES (6, 'André', 'andre', '123');
INSERT INTO funcionario(id, nome, user, senha) VALUES (7, 'Pedro', 'pedro', '123');
INSERT INTO funcionario(id, nome, user, senha) VALUES (8, 'Pedro Silva', 'silva', '123');
INSERT INTO funcionario(id, nome, user, senha) VALUES (9, 'Marco Antônio', 'antonio', '123');
INSERT INTO funcionario(id, nome, user, senha) VALUES (10, 'Jose Gomes', 'gomes', '123');


--CREATE TABLE votacao (
--  id   Long AUTO_INCREMENT PRIMARY KEY,
--  data Date,
--  idrestaurante Integer,
--  idfuncionario Integer,
--  escolhido char(0),
--  last_modification TIMESTAMP
--);


INSERT INTO votacao(id, data, idrestaurante, idfuncionario, escolhido) VALUES (1, '2019-09-26',  2, 3, 0);
INSERT INTO votacao(id, data, idrestaurante, idfuncionario, escolhido) VALUES (2, '2019-09-26',  3, 4, 0);
INSERT INTO votacao(id, data, idrestaurante, idfuncionario, escolhido) VALUES (3, '2019-09-26',  2, 2, 0);

