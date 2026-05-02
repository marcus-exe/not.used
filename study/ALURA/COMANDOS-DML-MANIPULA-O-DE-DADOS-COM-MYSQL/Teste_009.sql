--  COMANDO DELETE
DELETE FROM TABLENAME WHERE TAMANHO = '12' AND RATE = 10;
/* coloquei esse exemplo na lista pois não achei o link para colocar os itens extras e acho
que não vale a pena o trabalho */

-- APAGAR AS NOTAS FISCAIS CUJOS CLIENTES SÃO MENORES DE IDADE
DELETE A FROM
(NOTAS AS A
INNER JOIN CLIENTES B 
ON A.CPF = B.CPF)
WHERE B.IDADE <= 18;
