USE sucos_vendas;

-- USO DO LIMIT
-- USADO SEMPRE NO FIM DOS ARGUMENTOS
-- LIMIT 4 -> 4 PRIMEIRAS LINHAS
-- LIMIT 2,3 -> A PARTIR DA SEGUNDA LINHA, PEGUE 3 LINHAS

SELECT * FROM tabela_de_produtos;

-- COMANDOS EQUIVALENTES
SELECT * FROM tabela_de_produtos limit 4;
SELECT * FROM tabela_de_produtos limit 0,4;

SELECT * FROM tabela_de_produtos limit 2,5;

SELECT * FROM notas_fiscais WHERE DATA_VENDA = '2017-01-01' limit 10;