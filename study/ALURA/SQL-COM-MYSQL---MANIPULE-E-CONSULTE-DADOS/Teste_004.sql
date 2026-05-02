USE sucos;

INSERT INTO tb_produto (
PRODUTO,
NOME,
EMBALAGEM,
TAMANHO, 
SABOR, 
PRECO_LISTA) 
VALUES (
'1037797',
 'Clean - 2 Litros - Laranja',
 'PET',
 '2 Litros',
 'Laranja',
 16.008);
 
 INSERT INTO tb_produto (
PRODUTO,
NOME,
EMBALAGEM,
TAMANHO, 
SABOR, 
PRECO_LISTA) 
VALUES (
'1000889',
 'Sabor da Montanha - 700 ml - Uva',
 'Garrafa',
 '700 ml',
 'Uva',
  6.31);
  
   INSERT INTO tb_produto (
PRODUTO,
NOME,
EMBALAGEM,
TAMANHO, 
SABOR, 
PRECO_LISTA) 
VALUES (
'1004327',
 'ideira do Campo - 1,5 Litros - Melancia',
 'PET',
 '1,5 Litros',
 'Melancia',
  19.51);
  

 SELECT * FROM tb_produto;