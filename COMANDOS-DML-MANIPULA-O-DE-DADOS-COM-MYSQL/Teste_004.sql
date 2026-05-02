USE vendas_sucos;
ALTER TABLE tabela_de_vendas RENAME Notas;

-- /////////////////
USE vendas_sucos;

CREATE TABLE itens_notas
(NUMERO VARCHAR(5) NOT NULL,
CODIGO VARCHAR(10) NOT NULL, 
QUANTIDADE INT,
PRECO FLOAT, 
PRIMARY KEY (NUMERO, CODIGO));

