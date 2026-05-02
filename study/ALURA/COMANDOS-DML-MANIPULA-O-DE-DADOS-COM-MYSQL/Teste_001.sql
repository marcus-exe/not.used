-- criar banco
CREATE DATABASE vendas_sucos;
-- criar banco com set de charcteres especiais
CREATE DATABASE vendas_sucos2 DEFAULT CHARACTER SET utf8;
-- usar uma cláusula para criar o banco
CREATE DATABASE IF NOT EXISTS vendas_sucos2;
-- apagar o banco, podemos usar o workbench também 
DROP DATABASE IF EXISTS vendas_sucos2;