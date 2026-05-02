-- conversão de dados
-- fonte: https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-format

SELECT current_timestamp() AS RESULTADO;

SELECT CONCAT('O dia de hoje é : ', CURRENT_TIMESTAMP()) AS RESULTADO;

-- ano
SELECT CONCAT('O dia de hoje é : ',
 DATE_FORMAT(CURRENT_TIMESTAMP(), '%y')) AS RESULTADO;
 -- mes/ano
SELECT CONCAT('O dia de hoje é : ',
 DATE_FORMAT(CURRENT_TIMESTAMP(), '%m/%y')) AS RESULTADO;
 -- dia/mes/ano
SELECT CONCAT('O dia de hoje é : ',
 DATE_FORMAT(CURRENT_TIMESTAMP(), '%d/%m/%Y')) AS RESULTADO;
 -- dia da semana, dia/mes/ano
SELECT CONCAT('O dia de hoje é : ',
 DATE_FORMAT(CURRENT_TIMESTAMP(), '%W, %d/%m/%Y')) AS RESULTADO;
 
 -- CONVERTENDO ALGUÉM EM STR E FAZENDO UM SUBSTRING
SELECT SUBSTRING(CONVERT(23.3, CHAR),1 ,1) AS RESULTADO;


-- /////////////////////////////
SELECT CONCAT('O cliente ', TC.NOME, ' faturou ', 
CAST(SUM(INF.QUANTIDADE * INF.preco) AS char (20))
 , ' no ano ', CAST(YEAR(NF.DATA_VENDA) AS char (20))) AS SENTENCA FROM notas_fiscais NF
INNER JOIN itens_notas_fiscais INF ON NF.NUMERO = INF.NUMERO
INNER JOIN tabela_de_clientes TC ON NF.CPF = TC.CPF
WHERE YEAR(DATA_VENDA) = 2016
GROUP BY TC.NOME, YEAR(DATA_VENDA);

