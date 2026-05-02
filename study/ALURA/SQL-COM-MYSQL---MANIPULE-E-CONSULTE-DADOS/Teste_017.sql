 -- WHERE -> representa uma forma de condicão para realização de um certo comando 

-- aqui estou procurando por um produto em específico
SELECT * FROM tb_produto WHERE PRODUTO = '544931';
 
 -- aqui estou procurando por quem esteja em Rio de Janeiro na minha tabela
SELECT * FROM tbcliente WHERE CIDADE = 'Rio de Janeiro';

-- aqui estou procurando por produtos com sabor limão
SELECT * FROM tb_produto WHERE SABOR = 'Limão';

-- aqui estou mudando o nome do sabor dos produtos -> de limão para cítricos
UPDATE tb_produto SET SABOR = 'Cítricos' WHERE SABOR = 'Limão';

-- aqui estou procurando por produtos com sabor Cítricos
SELECT * FROM tb_produto WHERE SABOR = 'Cítricos';

-- HÁ A POSSIBILIDADE DE FAZER FILTROS COMPOSTOS TAMBÉM