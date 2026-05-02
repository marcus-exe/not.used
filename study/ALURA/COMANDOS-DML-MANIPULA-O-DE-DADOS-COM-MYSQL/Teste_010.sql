
-- vai criar uma table igual a que eu desejo
CREATE TABLE `produtos2` (
  `CODIGO` varchar(10) NOT NULL,
  `DESCRITOR` varchar(100) DEFAULT NULL,
  `SABOR` varchar(50) DEFAULT NULL,
  `TAMANHO` varchar(50) DEFAULT NULL,
  `EMBALAGEM` varchar(50) DEFAULT NULL,
  `PRECO_LISTA` float DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
);

SELECT * FROM produtos2;
-- vai copiar tudo de produtos e jogar em produtos 2
INSERT INTO produtos2 SELECT * FROM produtos;
-- vai apagar tudo da tabela
DELETE FROM produtos2;