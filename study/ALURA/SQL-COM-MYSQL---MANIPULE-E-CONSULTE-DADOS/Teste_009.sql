USE sucos;

UPDATE tabela_de_vendedores SET PERCENTUAL_COMISSAO= 0.11
WHERE MATRICULA= '00236' ;

UPDATE tabela_de_vendedores SET NOME= 'José Geraldo da Fonseca Junior'
WHERE MATRICULA= '00233' ;

SELECT * FROM tabela_de_vendedores;  