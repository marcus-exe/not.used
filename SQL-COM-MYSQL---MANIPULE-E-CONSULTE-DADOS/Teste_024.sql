USE sucos;
SELECT * FROM tabela_de_vendedores;
SELECT * FROM tabela_de_vendedores WHERE YEAR(DATA_ADMISSAO) < 2016 AND DE_FERIAS = 1;