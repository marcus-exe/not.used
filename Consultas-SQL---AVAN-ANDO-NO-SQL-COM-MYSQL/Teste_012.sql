USE sucos_vendas;
-- eu so uso a notação tabela.coluna quando há uma repetição dessa coluna nas tabelas

-- por esse banco de dados temos acesso tanto ao bairro dos clientes quanto dos vendedores
SELECT * FROM tabela_de_vendedores;
SELECT * FROM tabela_de_clientes;

-- ///////////////////////
SELECT * FROM tabela_de_vendedores INNER JOIN tabela_de_clientes
ON tabela_de_vendedores.BAIRRO = tabela_de_clientes.BAIRRO;

-- /////////////////////
SELECT tabela_de_vendedores.BAIRRO,
tabela_de_vendedores.NOME,
tabela_de_vendedores.DE_FERIAS,
tabela_de_clientes.BAIRRO,
tabela_de_clientes.NOME
FROM tabela_de_vendedores
INNER JOIN tabela_de_clientes
ON tabela_de_vendedores.BAIRRO = tabela_de_clientes.BAIRRO;

-- //////////////////////////
SELECT tabela_de_vendedores.BAIRRO,
tabela_de_vendedores.NOME,
tabela_de_vendedores.DE_FERIAS,
tabela_de_clientes.BAIRRO,
tabela_de_clientes.NOME
FROM tabela_de_vendedores
LEFT JOIN tabela_de_clientes
ON tabela_de_vendedores.BAIRRO = tabela_de_clientes.BAIRRO;
-- AQUI É POSSÍVEL VER O ESCRITÓRIO QUE NÃO TEM NENHUM CLIENTE


SELECT tabela_de_vendedores.BAIRRO,
tabela_de_vendedores.NOME,
tabela_de_vendedores.DE_FERIAS,
tabela_de_clientes.BAIRRO,
tabela_de_clientes.NOME
FROM tabela_de_vendedores
LEFT JOIN tabela_de_clientes
ON tabela_de_vendedores.BAIRRO = tabela_de_clientes.BAIRRO;
-- AQUI É POSSÍVEL VER O ESCRITÓRIO QUE NÃO TEM NENHUM CLIENTE - NO BAIRRO


SELECT tabela_de_vendedores.BAIRRO,
tabela_de_vendedores.NOME,
tabela_de_vendedores.DE_FERIAS,
tabela_de_clientes.BAIRRO,
tabela_de_clientes.NOME
FROM tabela_de_vendedores
RIGHT JOIN tabela_de_clientes
ON tabela_de_vendedores.BAIRRO = tabela_de_clientes.BAIRRO;
-- AQUI É POSSÍVEL VER QUAL CLIENTE NÃO TEM NENHUM VENDEDOR - NO BAIRRO


-- //////////////// 
SELECT tabela_de_vendedores.BAIRRO,
tabela_de_vendedores.NOME,
tabela_de_vendedores.DE_FERIAS,
tabela_de_clientes.BAIRRO,
tabela_de_clientes.NOME
FROM tabela_de_vendedores
FULL JOIN tabela_de_clientes
ON tabela_de_vendedores.BAIRRO = tabela_de_clientes.BAIRRO;
-- INFELIZMENTE O MYSQL NÃO SUPORTA FULL JOIN -> MAS EU POSSO JUNTAR UM RIGHT E LEFT JOIN PARA SIMULAR ISSO

-- //////////////
SELECT tabela_de_vendedores.BAIRRO,
tabela_de_vendedores.NOME,
tabela_de_vendedores.DE_FERIAS,
tabela_de_clientes.BAIRRO,
tabela_de_clientes.NOME
FROM tabela_de_vendedores, tabela_de_clientes;
-- O CROSS JOIN É UMA ANÁLISE COMBINATÓRIA
