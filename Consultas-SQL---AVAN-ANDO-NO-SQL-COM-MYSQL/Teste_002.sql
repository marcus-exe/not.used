USE sucos_vendas;

-- ALGUNS TESTES COM OPERAÇÕES LÓGICAS UTILIZANDO SQL
SELECT * FROM tabela_de_produtos WHERE SABOR = 'Manga' OR TAMANHO = '470 ml'; 
SELECT * FROM tabela_de_produtos WHERE SABOR = 'Manga' AND TAMANHO = '470 ml'; 
SELECT * FROM tabela_de_produtos WHERE NOT(SABOR = 'Manga' OR TAMANHO = '470 ml'); 
SELECT * FROM tabela_de_produtos WHERE NOT(SABOR = 'Manga' AND TAMANHO = '470 ml'); 
SELECT * FROM tabela_de_produtos WHERE NOT(SABOR = 'Manga' AND NOT(TAMANHO = '470 ml')); 
SELECT * FROM tabela_de_produtos WHERE SABOR = 'Manga' AND NOT(TAMANHO = '470 ml'); 

-- UTLIZANDO O OPERADOR IN PARA FAZER UMA LISTAGEM
SELECT * FROM tabela_de_produtos WHERE SABOR IN ('Laranja', 'Manga');
SELECT * FROM tabela_de_produtos WHERE SABOR = 'Laranja' or SABOR = 'Manga';

-- ALGUNS TESTES MAIS COMPLEXOS UTILIZANDO O IN
SELECT * FROM tabela_de_clientes WHERE CIDADE IN ('Rio de Janeiro', 'São Paulo') AND IDADE >= 20;

-- LEMBRE-SE DE UTILIZAR PARÊNTESES PARA DEIXAR A SUA CONSULTA MAIS CLARA
SELECT * FROM tabela_de_clientes WHERE CIDADE IN ('Rio de Janeiro', 'São Paulo') AND (IDADE >= 19 AND IDADE <=25);

