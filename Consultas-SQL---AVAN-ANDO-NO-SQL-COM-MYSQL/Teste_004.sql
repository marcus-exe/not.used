USE sucos_vendas;

-- DISTINCT -> é uma função do SQL que irá mostrar somente coisas distintas

SELECT EMBALAGEM, TAMANHO FROM tabela_de_produtos;
SELECT DISTINCT EMBALAGEM, TAMANHO FROM tabela_de_produtos;
SELECT DISTINCT * FROM tabela_de_produtos WHERE SABOR = 'Laranja'; 

SELECT * FROM tabela_de_clientes;
SELECT DISTINCT BAIRRO FROM tabela_de_clientes WHERE CIDADE = 'Rio de Janeiro';