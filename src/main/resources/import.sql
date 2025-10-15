-- Insere uma pessoa que é APENAS cliente
INSERT INTO Pessoa (nome, cpf, dataNascimento) VALUES ('João Cliente', '12345678901', '1990-05-10');
INSERT INTO Cliente (id_pessoa, permitirMarketing) VALUES (1, True);
INSERT INTO Usuario (email, senha, role) VALUES ('joao@cliente.com', '9od+0XAJyHTOE1yZNMikC/9swjRVO1Dkt+Rc7+nQxH6c5EnscBiykIJk+81XMWsEE3emhPDIv89kwSqCQqd7TA==', 'Cliente'); -- Senha '123456'

-- Insere uma pessoa que é APENAS funcionário
INSERT INTO Pessoa (nome, cpf, dataNascimento) VALUES ('Maria Funcionario', '98765432109', '1985-08-20');
INSERT INTO Funcionario (id_pessoa, salario, dataAdmissao) VALUES (2, 5000.00, '2020-01-15');
INSERT INTO Usuario (email, senha, role) VALUES ('maria@funcionario.com', '9od+0XAJyHTOE1yZNMikC/9swjRVO1Dkt+Rc7+nQxH6c5EnscBiykIJk+81XMWsEE3emhPDIv89kwSqCQqd7TA==', 'Funcionario'); -- Senha '123456'

-- Insere uma pessoa que é cliente E funcionário
INSERT INTO Pessoa (nome, cpf, dataNascimento) VALUES ('Carlos Cliente e Funcionario', '11122233344', '1995-03-15');
INSERT INTO Cliente (id_pessoa, permitirMarketing) VALUES (3, False);
INSERT INTO Funcionario (id_pessoa, salario, dataAdmissao) VALUES (3, 6500.00, '2022-06-01');
INSERT INTO Usuario (email, senha, role) VALUES ('carlos@cliente.com', '9od+0XAJyHTOE1yZNMikC/9swjRVO1Dkt+Rc7+nQxH6c5EnscBiykIJk+81XMWsEE3emhPDIv89kwSqCQqd7TA==', 'Cliente'); -- Senha '123456'
INSERT INTO Usuario (email, senha, role) VALUES ('carlos@funcionario.com', '9od+0XAJyHTOE1yZNMikC/9swjRVO1Dkt+Rc7+nQxH6c5EnscBiykIJk+81XMWsEE3emhPDIv89kwSqCQqd7TA==', 'Funcionario'); -- Senha '123456'

-- Inserindo Marcas
INSERT INTO Marca (nome, cnpj) VALUES ('Fender', '11.111.111/0001-11');
INSERT INTO Marca (nome, cnpj) VALUES ('Gibson', '22.222.222/0001-22');
INSERT INTO Marca (nome, cnpj) VALUES ('Ibanez', '33.333.333/0001-33');

-- Inserindo Cores
INSERT INTO Cor (nome, codigoHexadecimal) VALUES ('Sunburst', '#FDB813');
INSERT INTO Cor (nome, codigoHexadecimal) VALUES ('Black', '#000000');
INSERT INTO Cor (nome, codigoHexadecimal) VALUES ('Olympic White', '#FDFDFD');

-- Inserindo Braços
INSERT INTO Braco (formato, madeira, numeroDeTrastes) VALUES ('Modern C', 'Maple', 22);
INSERT INTO Braco (formato, madeira, numeroDeTrastes) VALUES ('SlimTaper', 'Mahogany', 22);
INSERT INTO Braco (formato, madeira, numeroDeTrastes) VALUES ('Wizard III', 'Maple', 24);

-- Inserindo Captadores
INSERT INTO Captador (marca, modelo, posicao) VALUES ('Fender', 'V-Mod II Single-Coil', 'Braço');
INSERT INTO Captador (marca, modelo, posicao) VALUES ('Fender', 'V-Mod II Single-Coil', 'Meio');
INSERT INTO Captador (marca, modelo, posicao) VALUES ('Fender', 'V-Mod II Single-Coil', 'Ponte');
INSERT INTO Captador (marca, modelo, posicao) VALUES ('Gibson', 'BurstBucker 61R', 'Braço');
INSERT INTO Captador (marca, modelo, posicao) VALUES ('Gibson', 'BurstBucker 61T', 'Ponte');
INSERT INTO Captador (marca, modelo, posicao) VALUES ('Ibanez', 'Quantum (H)', 'Braço');
INSERT INTO Captador (marca, modelo, posicao) VALUES ('Ibanez', 'Quantum (S)', 'Meio');
INSERT INTO Captador (marca, modelo, posicao) VALUES ('Ibanez', 'Quantum (H)', 'Ponte');

-- Inserindo Pontes
INSERT INTO Ponte (marca, modelo) VALUES ('Fender', '2-Point Synchronized Tremolo');
INSERT INTO Ponte (marca, modelo) VALUES ('Gibson', 'ABR-1 Tune-O-Matic');
INSERT INTO Ponte (marca, modelo) VALUES ('Ibanez', 'Edge-Zero II tremolo bridge');

-- Inserindo Tarrachas
INSERT INTO Tarracha (marca, material, modelo) VALUES ('Fender', 'Aço', 'Standard Cast/Sealed');
INSERT INTO Tarracha (marca, material, modelo) VALUES ('Grover', 'Aço', 'Rotomatics');
INSERT INTO Tarracha (marca, material, modelo) VALUES ('Ibanez', 'Aço', 'Ibanez machine heads');


-- Inserindo Guitarras (parte específica)
-- Guitarra 1: Fender Stratocaster
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_meio, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('A clássica Stratocaster com melhorias modernas.', 'Fender Stratocaster American Professional II', 12500.00, 10, true, 1, 'Stratocaster', 'Alder', 3.5, 6, 1, 1, 2, 3, 1, 1, 1);

-- Guitarra 2: Gibson Les Paul
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('O som icônico do rock and roll.', 'Gibson Les Paul Standard 60s', 18900.00, 5, true, 2, 'Les Paul', 'Mahogany', 4.2, 6, 2, 2, 5, 2, 2, 2);

-- Guitarra 3: Ibanez RG
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_meio, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Velocidade e precisão para o metal moderno.', 'Ibanez RG550', 7800.00, 8, true, 3, 'RG', 'Basswood', 3.2, 6, 3, 6, 7, 8, 3, 3, 3);

-- Guitarra 4: Fender Telecaster
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('O som estalado e clássico da Telecaster.', 'Fender Telecaster Player Series', 6800.00, 12, true, 1, 'Telecaster', 'Alder', 3.6, 6, 1, 1, 3, 2, 1, 1);

-- Guitarra 5: Gibson SG Standard
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Leve, potente e pronta para o rock.', 'Gibson SG Standard', 14500.00, 7, true, 2, 'SG', 'Mahogany', 3.1, 6, 2, 4, 5, 1, 2, 2);

-- Guitarra 6: Ibanez S Series
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_meio, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Corpo fino, leve e som versátil.', 'Ibanez S671ALB', 8200.00, 9, true, 3, 'S', 'Nyatoh', 2.9, 6, 3, 6, 7, 8, 2, 3, 3);

-- Guitarra 7: Fender Jazzmaster
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Visual vintage e som único, ideal para indie e alternativo.', 'Fender Jazzmaster Vintera 60s', 9500.00, 6, true, 1, 'Jazzmaster', 'Alder', 3.8, 6, 1, 1, 3, 3, 1, 1);

-- Guitarra 8: Gibson Les Paul Custom
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('O ápice do luxo e do som, a "Black Beauty".', 'Gibson Les Paul Custom', 25000.00, 3, true, 2, 'Les Paul Custom', 'Mahogany', 4.5, 6, 2, 4, 5, 2, 2, 2);

-- Guitarra 9: Ibanez AZ
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_meio, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Versatilidade moderna para todos os estilos.', 'Ibanez AZ2402', 13500.00, 5, true, 3, 'AZ', 'Alder', 3.3, 6, 3, 6, 7, 8, 1, 3, 3);

-- Guitarra 10: Fender Stratocaster (Black)
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_meio, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('A icônica Stratocaster em um acabamento preto clássico.', 'Fender Stratocaster Player (Black)', 6500.00, 15, true, 1, 'Stratocaster', 'Alder', 3.5, 6, 1, 1, 2, 3, 2, 1, 1);

-- Guitarra 11: Gibson Les Paul (White)
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Uma Les Paul com um visual clean e moderno.', 'Gibson Les Paul Studio (White)', 11000.00, 8, true, 2, 'Les Paul Studio', 'Mahogany', 4.0, 6, 2, 4, 5, 3, 2, 2);

-- Guitarra 12: Ibanez RG (Sunburst)
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_meio, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Uma RG com um toque clássico no acabamento.', 'Ibanez RG421HPAM', 5500.00, 10, true, 3, 'RG', 'Nyatoh', 3.1, 6, 3, 6, 7, 8, 1, 3, 3);

-- Guitarra 13: Fender Jaguar
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Escala curta e circuito complexo para timbres únicos.', 'Fender Jaguar Player Series', 7200.00, 7, true, 1, 'Jaguar', 'Alder', 3.7, 6, 1, 1, 3, 1, 1, 1);

-- Guitarra 14: Gibson Flying V
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Design radical e som agressivo para o metal.', 'Gibson Flying V', 15500.00, 4, true, 2, 'Flying V', 'Mahogany', 3.4, 6, 2, 4, 5, 2, 2, 2);

-- Guitarra 15: Ibanez 7 Cordas
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Explore novas profundezas com 7 cordas.', 'Ibanez RG7421', 4900.00, 11, true, 3, 'RG7', 'Meranti', 3.6, 7, 3, 6, 8, 3, 3, 3);

-- Guitarra 16: Fender Stratocaster HSS
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_meio, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('A versatilidade da Strat com a potência de um humbucker na ponte.', 'Fender Stratocaster Player HSS', 6950.00, 18, true, 1, 'Stratocaster HSS', 'Alder', 3.6, 6, 1, 1, 2, 8, 3, 1, 1);

-- Guitarra 17: Gibson ES-335
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('A semi-acústica definitiva para blues, jazz e rock.', 'Gibson ES-335', 22000.00, 5, true, 2, 'ES-335', 'Maple/Poplar/Maple', 3.9, 6, 2, 4, 5, 1, 2, 2);

-- Guitarra 18: Ibanez Artcore
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Guitarra semi-acústica com ótimo custo-benefício.', 'Ibanez AS73', 4500.00, 13, true, 3, 'Artcore', 'Linden', 3.8, 6, 2, 6, 8, 1, 2, 3);

-- Guitarra 19: Fender Mustang
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Pequena, confortável e cheia de atitude.', 'Fender Mustang 90', 5800.00, 9, true, 1, 'Mustang', 'Alder', 3.2, 6, 1, 1, 3, 3, 1, 1);

-- Guitarra 20: Gibson Explorer
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Outro design icônico e agressivo da Gibson.', 'Gibson Explorer', 16000.00, 4, true, 2, 'Explorer', 'Mahogany', 4.1, 6, 2, 4, 5, 3, 2, 2);

-- Guitarra 21: Ibanez Iceman
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Visual único e som pesado, famosa por Paul Stanley.', 'Ibanez Iceman PS120', 9800.00, 6, true, 3, 'Iceman', 'Mahogany', 4.0, 6, 2, 6, 8, 2, 2, 3);

-- Guitarra 22: Fender Stratocaster (7 Cordas)
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_meio, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Uma Stratocaster para a era moderna do metal.', 'Fender Stratocaster 7-String', 15000.00, 3, true, 1, 'Stratocaster 7', 'Alder', 3.9, 7, 1, 1, 2, 8, 1, 1, 1);

-- Guitarra 23: Gibson Firebird
INSERT INTO Guitarra (descricao, nome, preco, quantidade, status, id_marca, modelo, madeira, peso, numeroDeCordas, id_braco, id_captador_braco, id_captador_ponte, id_cor, id_ponte, id_tarracha) 
VALUES ('Design "reverse" e mini-humbuckers para um timbre brilhante.', 'Gibson Firebird', 17500.00, 4, true, 2, 'Firebird', 'Mahogany/Walnut', 4.0, 6, 2, 4, 5, 1, 2, 2);