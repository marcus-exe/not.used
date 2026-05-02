USE SUCOS_VENDAS;

-- Aqui temos o seguinte problemas: 15 clientes registrados
-- mas somente 14 notas fiscais foram emitidas
SELECT COUNT(*) FROM tabela_de_clientes;
SELECT CPF, COUNT(*) FROM notas_fiscais
GROUP BY CPF;

-- Então iremos descobrir quem nunca comprou na empresa
SELECT DISTINCT A.CPF, A.NOME, B.CPF FROM tabela_de_clientes A 
INNER JOIN notas_fiscais B 
ON A.CPF = B.CPF;
-- estamos usando o DISTINCT para evitar repetição 

-- utilizando o left join nessa organização, conseguimos achar a pessoa
SELECT DISTINCT A.CPF, A.NOME, B.CPF FROM tabela_de_clientes A 
LEFT JOIN notas_fiscais B 
ON A.CPF = B.CPF;

-- também posso esperar somente poela pessoa
SELECT DISTINCT A.CPF, A.NOME, B.CPF FROM tabela_de_clientes A 
LEFT JOIN notas_fiscais B 
ON A.CPF = B.CPF
WHERE B.CPF IS NULL;

-- a diferença entre left join e right join é a ordem

