-- usaremos agora o comando UPDATE

SELECT * FROM produtos;

UPDATE produtos SET PRECO_LISTA = 5 WHERE CODIGO = '1000889';

UPDATE produtos SET
EMBALAGEM = 'PET',
TAMANHO = '1 Litro',
DESCRITOR = 'Sabor da Montanha - 1 Litro - UVA'
WHERE CODIGO = '1000889';

SELECT * FROM produtos WHERE SABOR = 'Maracujá';

-- aumentar em 10% o preço dos produtos com sabor maracujá
UPDATE produtos SET PRECO_LISTA = PRECO_LISTA * 1.10 WHERE SABOR = 'Maracujá';

-- //////////////////////
UPDATE clientes SET
ENDERECO = 'R. Jorge Emilio 23',
BAIRRO = 'Santo Amaro',
CIDADE = 'São Paulo',
ESTADO = 'SP',
CEP = '8833223'
WHERE CPF = '19290992743';
