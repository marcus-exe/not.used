<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="icon" type="image" href="./assets/images/logo.png" />
  <link href="./resources/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="index.css">
  <title>Agnello - Vinhos</title>
  <style>
    :root {
      --cor-primaria: #8b0000;
      --cor-secundaria: #2c0000;
      --cor-destaque: #d4af37;
      --cor-texto: #333;
      --cor-borda: #e0e0e0;
      --sombra-suave: 0 4px 15px rgba(0, 0, 0, 0.08);
      --transicao: all 0.3s ease;
      --raio-borda: 32px;
    }

    body {
      background-color: white;
      color: var(--cor-texto);
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .productsTitleDiv {
      position: relative;
      margin-bottom: 50px;
    }

    .productsTitleDiv img {
      width: 100%;
      height: 300px;
      object-fit: cover;
    }

    .productsTitleDiv h1 {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      color: white;
      font-size: 2.5rem;
      font-weight: 600;
      text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
    }

    .produtos-container {
      max-width: 1800px;
      margin: 0 auto;
      padding: 0 20px 60px;
    }

    .produtos-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(380px, 1fr));
      gap: 60px;
    }

    .produto-card {
      background-color: #f5f5f5;
      border-radius: var(--raio-borda);
      overflow: hidden;
      box-shadow: var(--sombra-suave);
      transition: var(--transicao);
      height: 100%;
      display: flex;
      flex-direction: column;
      border: 1px solid var(--cor-borda);
    }

    .produto-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 10px 25px rgba(0, 0, 0, 0.12);
    }

    .produto-imagem {
      height: 280px;
      overflow: hidden;
      position: relative;
      background-color: white;
      border-top-left-radius: var(--raio-borda);
      border-top-right-radius: var(--raio-borda);
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .produto-imagem img {
      max-width: 85%;
      max-height: 85%;
      object-fit: contain;
      transition: var(--transicao);
    }

    .produto-card:hover .produto-imagem img {
      transform: scale(1.05);
    }

    .produto-info {
      padding: 25px;
      display: flex;
      flex-direction: column;
      flex-grow: 1;
      background-color: white;
      border-top: 1px solid var(--cor-borda);
    }

    .produto-titulo {
      font-size: 1.25rem;
      font-weight: 600;
      margin-bottom: 8px;
      color: var(--cor-primaria);
    }

    .produto-origem {
      color: #666;
      font-size: 0.95rem;
      margin-bottom: 18px;
      display: block;
    }

    .produto-preco-avaliacao {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
    }

    .produto-preco {
      font-size: 1.4rem;
      font-weight: 700;
      color: var(--cor-secundaria);
    }

    .produto-avaliacao {
      display: flex;
      align-items: center;
    }

    .estrelas {
      color: var(--cor-destaque);
      letter-spacing: -1px;
      margin-right: 5px;
    }

    .total-avaliacoes {
      font-size: 0.85rem;
      color: #777;
    }

    .btn-adicionar {
      background-color: var(--cor-primaria);
      color: white;
      border: none;
      border-radius: 30px;
      padding: 14px 20px;
      font-size: 1rem;
      font-weight: 600;
      cursor: pointer;
      transition: var(--transicao);
      width: 100%;
      margin-top: auto;
      box-shadow: 0 4px 8px rgba(139, 0, 0, 0.2);
    }

    .btn-adicionar:hover {
      background-color: var(--cor-secundaria);
      box-shadow: 0 6px 12px rgba(139, 0, 0, 0.3);
    }

    @media (max-width: 768px) {
      .productsTitleDiv h1 {
        font-size: 2rem;
      }
      
      .produtos-grid {
        grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
        gap: 30px;
      }
      
      .produto-imagem {
        height: 250px;
      }
    }

    @media (max-width: 480px) {
      .productsTitleDiv img {
        height: 220px;
      }
      
      .produtos-grid {
        grid-template-columns: 1fr;
      }
      
      .produto-card {
        max-width: 320px;
        margin: 0 auto;
      }
    }
  </style>
  <script type="text/javascript">
    <c:if test="${productAdded}">
      alert("Produto adicionado ao carrinho com sucesso!");
    </c:if>
  </script>
</head>

<body>
  <jsp:include page="components/header/header.jsp"></jsp:include>
  <main>
    <div class="productsTitleDiv">
      <img src="./assets/images/products.jpg" alt="products banner image">
      <h1>Produtos</h1>
    </div>

    <section class="produtos-container">
      <div class="produtos-grid">
        <c:forEach var="product" items="${products}">
          <div class="produto-card">
            <div class="produto-imagem">
              <img src="${product.image}" alt="${product.title}">
            </div>
            <div class="produto-info">
              <h2 class="produto-titulo">${product.title}</h2>
              <span class="produto-origem">${product.tag}</span>
              <div class="produto-preco-avaliacao">
                <span class="produto-preco">R$ ${product.price}</span>
                <div class="produto-avaliacao">
                  <span class="estrelas">★★★★☆</span>
                  <span class="total-avaliacoes">(24)</span>
                </div>
              </div>
              <form action="products" method="post">
                <input type="hidden" name="title" value="${product.title}" />
                <input type="hidden" name="image" value="${product.image}" />
                <input type="hidden" name="tag" value="${product.tag}" />
                <input type="hidden" name="price" value="${product.price}" />
                <input type="hidden" name="id" value="${product.id}" />
                <button type="submit" class="btn-adicionar">Adicionar ao Carrinho</button>
              </form>
            </div>
          </div>
          <div class="produto-card">
            <div class="produto-imagem">
              <img src="${product.image}" alt="${product.title}">
            </div>
            <div class="produto-info">
              <h2 class="produto-titulo">${product.title}</h2>
              <span class="produto-origem">${product.tag}</span>
              <div class="produto-preco-avaliacao">
                <span class="produto-preco">R$ ${product.price}</span>
                <div class="produto-avaliacao">
                  <span class="estrelas">★★★★☆</span>
                  <span class="total-avaliacoes">(24)</span>
                </div>
              </div>
              <form action="products" method="post">
                <input type="hidden" name="title" value="${product.title}" />
                <input type="hidden" name="image" value="${product.image}" />
                <input type="hidden" name="tag" value="${product.tag}" />
                <input type="hidden" name="price" value="${product.price}" />
                <input type="hidden" name="id" value="${product.id}" />
                <button type="submit" class="btn-adicionar">Adicionar ao Carrinho</button>
              </form>
            </div>
          </div>
          <div class="produto-card">
            <div class="produto-imagem">
              <img src="${product.image}" alt="${product.title}">
            </div>
            <div class="produto-info">
              <h2 class="produto-titulo">${product.title}</h2>
              <span class="produto-origem">${product.tag}</span>
              <div class="produto-preco-avaliacao">
                <span class="produto-preco">R$ ${product.price}</span>
                <div class="produto-avaliacao">
                  <span class="estrelas">★★★★☆</span>
                  <span class="total-avaliacoes">(24)</span>
                </div>
              </div>
              <form action="products" method="post">
                <input type="hidden" name="title" value="${product.title}" />
                <input type="hidden" name="image" value="${product.image}" />
                <input type="hidden" name="tag" value="${product.tag}" />
                <input type="hidden" name="price" value="${product.price}" />
                <input type="hidden" name="id" value="${product.id}" />
                <button type="submit" class="btn-adicionar">Adicionar ao Carrinho</button>
              </form>
            </div>
          </div>
        </c:forEach>
      </div>
    </section>
  </main>
  <footer>
    <jsp:include page="components/footer/footer.jsp"></jsp:include>
  </footer>
  <script src="./resources/js/bootstrap.bundle.min.js"></script>
</body>

</html>