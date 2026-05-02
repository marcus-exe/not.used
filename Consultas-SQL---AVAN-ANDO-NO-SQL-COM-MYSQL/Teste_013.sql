USE sucos_vendas; 
-- UNION -> FAZ A UNIÃO DE DUAS OU MAIS TABELAS
-- É importante que as tabelas que serão unidas tenham o mesmo número e tipo de campo
-- Ele faz automaticamente o DISTINCT
-- UNION ALL -> Não aplica DISTINCT

SELECT DISTINCT BAIRRO FROM tabela_de_clientes;
SELECT DISTINCT BAIRRO FROM tabela_de_vendedores;

-- ////////////////
SELECT DISTINCT BAIRRO FROM tabela_de_clientes
UNION
SELECT DISTINCT BAIRRO FROM tabela_de_vendedores;

-- /////////////////
SELECT DISTINCT BAIRRO FROM tabela_de_clientes
UNION ALL
SELECT DISTINCT BAIRRO FROM tabela_de_vendedores;

-- //////////////// Vamos colocar um alias 
SELECT DISTINCT BAIRRO, NOME, 'Cliente' as TIPO FROM tabela_de_clientes
UNION
SELECT DISTINCT BAIRRO, NOME, 'Vendedor' as TIPO FROM tabela_de_vendedores; 

-- ////////////////Ele só vai escrever as colunas do primeiro select
SELECT DISTINCT BAIRRO, NOME, 'Cliente' as TIPO_CLIENTE FROM tabela_de_clientes
UNION
SELECT DISTINCT BAIRRO, NOME, 'Vendedor' as TIPO_VENDEDOR FROM tabela_de_vendedores; 


SELECT DISTINCT BAIRRO, NOME, 'Cliente' as TIPO_CLIENTE, CPF FROM tabela_de_clientes
UNION
SELECT DISTINCT BAIRRO, NOME, 'Vendedor' as TIPO_VENDEDOR, MATRICULA FROM tabela_de_vendedores; 


-- /////////////////////// JUNTANDO RIGHT AND LEFT JOIN PELO UNION
SELECT tabela_de_vendedores.BAIRRO,
tabela_de_vendedores.NOME,
tabela_de_vendedores.DE_FERIAS,
tabela_de_clientes.BAIRRO,
tabela_de_clientes.NOME
FROM tabela_de_vendedores
RIGHT JOIN tabela_de_clientes
ON tabela_de_vendedores.BAIRRO = tabela_de_clientes.BAIRRO
UNION
SELECT tabela_de_vendedores.BAIRRO,
tabela_de_vendedores.NOME,
tabela_de_vendedores.DE_FERIAS,
tabela_de_clientes.BAIRRO,
tabela_de_clientes.NOME
FROM tabela_de_vendedores
LEFT JOIN tabela_de_clientes
ON tabela_de_vendedores.BAIRRO = tabela_de_clientes.BAIRRO;