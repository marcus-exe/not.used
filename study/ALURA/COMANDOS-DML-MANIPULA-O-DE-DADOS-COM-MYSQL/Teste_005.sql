USE vendas_sucos;

INSERT INTO PRODUTOS (CODIGO, DESCRITOR, SABOR, TAMANHO, EMBALAGEM, PRECO_LISTA)
VALUES ('1040107', 'Light - 350 ml - Melancia', 'Melancia', '350 ml', 'Lata', 4.56);

SELECT * FROM produtos;

INSERT INTO PRODUTOS (CODIGO, DESCRITOR, SABOR, TAMANHO, EMBALAGEM, PRECO_LISTA)
VALUES ('1040108', 'Light - 350 ml - Graviola', 'Graviola', '350 ml', 'Lata', 4.00);

INSERT INTO PRODUTOS (CODIGO, DESCRITOR, SABOR, TAMANHO, EMBALAGEM, PRECO_LISTA)
VALUES ('1040109', 'Light - 350 ml - Acai', 'Açai', '350 ml', 'Lata', 5.60);

-- eu posso omitir essa parte (CODIGO, DESCRITOR, SABOR, TAMANHO, EMBALAGEM, PRECO_LISTA)
-- sem muitos problemas, mas eu preciso declarar as variáveis na ordem correta
-- todavia se eu quiser mudar a ordem em que eu declaro as variáveis, devo mudar primeiramente
--  isso aqui (CODIGO, DESCRITOR, SABOR, TAMANHO, EMBALAGEM, PRECO_LISTA)

INSERT INTO PRODUTOS (CODIGO, DESCRITOR, SABOR, TAMANHO, EMBALAGEM, PRECO_LISTA)
VALUES ('1040110', 'Light - 350 ml - Jaca', 'Jaca', '350 ml', 'Lata', 6.00),
	   ('1040111', 'Light - 350 ml - Manga', 'Manga', '350 ml', 'Lata', 3.50);
       
       
-- /////////////////////////
INSERT INTO CLIENTES (CPF, NOME, ENDERECO, BAIRRO, CIDADE, ESTADO, CEP, DATA_NASCIMENTO,
					   IDADE, SEXO, LIMITE_CREDITO, VOLUME_COMPRA, PRIMEIRA_COMPRA)
VALUES ('1471156710', 'Érica Carvalho', 'R. Iriquitia','Jardins','São Paulo','SP',80012212,19900901,
						27, 'F',  170000, 24500, 0),
		('19290992743','Fernando Cavalcante','R. Dois de Fevereiro','Água Santa','Rio de Janeiro','RJ',22000000, 20000212,
						18,'M',100000,20000,1),
		('2600586709','César Teixeira','Rua Conde de Bonfim','Tijuca','Rio de Janeiro','RJ', 22020001, 20000312,
						18,'M',120000,22000,0);


SELECT * FROM clientes;