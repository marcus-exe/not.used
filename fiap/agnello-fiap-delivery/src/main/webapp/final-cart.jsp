<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="icon" type="image" href="./assets/images/logo.png" />
  <link href="./resources/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="index.css">
  <title>Pedido Finalizado - Agnello</title>
  <style>
    :root {
      --cor-primaria: #8b0000;
      --cor-secundaria: #2c0000;
      --cor-destaque: #d4af37;
      --cor-texto: #333;
      --cor-borda: #e0e0e0;
      --sombra-suave: 0 4px 15px rgba(0, 0, 0, 0.08);
      --transicao: all 0.3s ease;
      --raio-borda: 16px;
      --cor-sucesso: #28a745;
      --cor-fundo-claro: #f9f9f9;
    }

    body {
      background-color: white;
      color: var(--cor-texto);
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    main {
      max-width: 1200px;
      margin: 0 auto;
      padding: 30px 20px 60px;
    }

    .title {
      position: relative;
      text-align: center;
      color: var(--cor-primaria);
      font-size: 2.2rem;
      font-weight: 600;
      margin-bottom: 40px;
      padding-bottom: 15px;
    }

    .title:after {
      content: '';
      position: absolute;
      bottom: 0;
      left: 50%;
      transform: translateX(-50%);
      width: 80px;
      height: 3px;
      background-color: var(--cor-destaque);
    }

    .success-message {
      text-align: center;
      margin-bottom: 40px;
      padding: 20px;
      background-color: rgba(40, 167, 69, 0.1);
      border-radius: var(--raio-borda);
      border: 1px solid rgba(40, 167, 69, 0.2);
    }

    .success-icon {
      font-size: 3rem;
      color: var(--cor-sucesso);
      margin-bottom: 15px;
    }

    .success-text {
      font-size: 1.2rem;
      color: var(--cor-sucesso);
      margin-bottom: 5px;
    }

    .success-subtext {
      font-size: 1rem;
      color: #666;
    }

    .order-details {
      background-color: var(--cor-fundo-claro);
      border-radius: var(--raio-borda);
      padding: 25px;
      margin-bottom: 30px;
      border: 1px solid var(--cor-borda);
    }

    .order-details h3 {
      color: var(--cor-primaria);
      font-size: 1.4rem;
      margin-bottom: 15px;
      padding-bottom: 10px;
      border-bottom: 1px solid var(--cor-borda);
    }

    .order-info {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;
      margin-bottom: 20px;
    }

    .order-info-item {
      flex: 1;
      min-width: 200px;
    }

    .order-info-label {
      font-weight: 600;
      color: #555;
      margin-bottom: 5px;
    }

    .order-info-value {
      font-size: 1.1rem;
    }

    .finalproductsContainer {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: 25px;
      margin-bottom: 40px;
    }

    .product-summary {
      background-color: white;
      border-radius: var(--raio-borda);
      overflow: hidden;
      box-shadow: var(--sombra-suave);
      border: 1px solid var(--cor-borda);
      padding: 20px;
      display: flex;
      flex-direction: column;
      transition: var(--transicao);
    }

    .product-summary:hover {
      transform: translateY(-3px);
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
    }

    .product-summary img {
      width: 100%;
      height: 200px;
      object-fit: contain;
      background-color: #f5f5f5;
      border-radius: 8px;
      padding: 10px;
      margin-bottom: 15px;
    }

    .product-summary p {
      margin-bottom: 8px;
      line-height: 1.5;
    }

    .product-summary p:last-child {
      margin-top: 10px;
      font-size: 1.2rem;
      color: var(--cor-primaria);
      font-weight: 700;
      border-top: 1px solid var(--cor-borda);
      padding-top: 10px;
    }

    .order-total {
      background-color: white;
      border-radius: var(--raio-borda);
      padding: 25px;
      margin-top: 20px;
      border: 1px solid var(--cor-borda);
      box-shadow: var(--sombra-suave);
    }

    .order-total h3 {
      color: var(--cor-primaria);
      font-size: 1.4rem;
      margin-bottom: 15px;
      padding-bottom: 10px;
      border-bottom: 1px solid var(--cor-borda);
    }

    .total-row {
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
    }

    .total-row.grand-total {
      font-size: 1.3rem;
      font-weight: 700;
      color: var(--cor-primaria);
      margin-top: 15px;
      padding-top: 15px;
      border-top: 1px solid var(--cor-borda);
    }

    .action-buttons {
      display: flex;
      justify-content: center;
      gap: 20px;
      margin-top: 40px;
    }

    .btn-action {
      background-color: var(--cor-primaria);
      color: white;
      border: none;
      border-radius: 30px;
      padding: 14px 30px;
      font-size: 1.1rem;
      font-weight: 600;
      cursor: pointer;
      transition: var(--transicao);
      min-width: 200px;
      text-align: center;
      text-decoration: none;
      display: inline-block;
      box-shadow: 0 4px 8px rgba(139, 0, 0, 0.2);
    }

    .btn-action:hover {
      background-color: var(--cor-secundaria);
      box-shadow: 0 6px 12px rgba(139, 0, 0, 0.3);
      color: white;
      text-decoration: none;
    }

    .btn-secondary {
      background-color: #6c757d;
    }

    .btn-secondary:hover {
      background-color: #5a6268;
    }

    .empty-cart {
      text-align: center;
      padding: 50px 0;
      font-size: 1.2rem;
      color: #666;
    }

    .empty-cart-icon {
      font-size: 3rem;
      margin-bottom: 20px;
      color: #ccc;
    }

    @media (max-width: 768px) {
      .finalproductsContainer {
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
      }
      
      .title {
        font-size: 1.8rem;
      }
      
      .action-buttons {
        flex-direction: column;
        align-items: center;
      }
      
      .btn-action {
        width: 100%;
        max-width: 300px;
      }
    }

    @media (max-width: 480px) {
      .finalproductsContainer {
        grid-template-columns: 1fr;
      }
      
      .product-summary img {
        height: 180px;
      }
    }
  </style>
</head>
<body>
  <jsp:include page="components/header/header.jsp"></jsp:include>
  <main>
    <div class="success-message">
      <div class="success-icon">✓</div>
      <h2 class="success-text">Pedido Finalizado com Sucesso!</h2>
      <p class="success-subtext">Obrigado por escolher a Agnello Vinhos. Seu pedido foi recebido e está sendo processado.</p>
    </div>
    
    <div class="order-details">
      <h3>Detalhes do Pedido</h3>
      <div class="order-info">
        <div class="order-info-item">
          <div class="order-info-label">Número do Pedido</div>
          <div class="order-info-value">#${orderNumber != null ? orderNumber : '12345'}</div>
        </div>
        <div class="order-info-item">
          <div class="order-info-label">Data</div>
          <div class="order-info-value">${orderDate != null ? orderDate : fn:substring(pageContext.session.creationTime, 0, 10)}</div>
        </div>
        <div class="order-info-item">
          <div class="order-info-label">Status</div>
          <div class="order-info-value">Aguardando pagamento</div>
        </div>
      </div>
    </div>
    
    <h2 class="title">Itens do Pedido</h2>
    
    <!-- Verifica se a lista de produtos não está vazia -->
    <c:if test="${not empty products}">
      <div class="finalproductsContainer">
        <c:forEach var="product" items="${products}">
          <div class="product-summary">
            <img src="${product.image}" alt="Produto: ${product.title}" />
            <p><strong>Produto:</strong> ${product.title}</p>
            <p><strong>Categoria:</strong> ${product.tag}</p>
            <p><strong>Preço Unitário:</strong> R$ ${product.price}</p>
            <p><strong>Quantidade:</strong> ${product.quantity}</p>
            
            <!-- Cálculo do preço total por produto -->
            <c:set var="rawPrice" value="${product.price}" />
            <c:set var="rawQuantity" value="${product.quantity}" />
            <c:set var="priceWithoutSymbol" value="${fn:replace(rawPrice, 'R$', '')}" />
            <c:set var="priceFormatted" value="${fn:replace(priceWithoutSymbol, ',', '.')}" />
            <c:set var="unitPrice" value="${priceFormatted != null ? priceFormatted : '0'}" />
            <c:set var="quantity" value="${rawQuantity != null ? rawQuantity : '0'}" />
            <c:set var="totalPrice" value="${unitPrice * quantity}" />
            <fmt:formatNumber value="${totalPrice}" type="number" pattern="#,##0.00" var="formattedTotal" />
            
            <p><strong>Total:</strong> R$ ${fn:replace(formattedTotal, '.', ',')}</p>
          </div>
        </c:forEach>
      </div>
      
      <!-- Calculando o total geral -->
      <c:set var="grandTotal" value="0" />
      <c:forEach var="product" items="${products}">
        <c:set var="rawPrice" value="${product.price}" />
        <c:set var="rawQuantity" value="${product.quantity}" />
        <c:set var="priceWithoutSymbol" value="${fn:replace(rawPrice, 'R$', '')}" />
        <c:set var="priceFormatted" value="${fn:replace(priceWithoutSymbol, ',', '.')}" />
        <c:set var="unitPrice" value="${priceFormatted != null ? priceFormatted : '0'}" />
        <c:set var="quantity" value="${rawQuantity != null ? rawQuantity : '0'}" />
        <c:set var="totalPrice" value="${unitPrice * quantity}" />
        <c:set var="grandTotal" value="${grandTotal + totalPrice}" />
      </c:forEach>
      
      <div class="order-total">
        <h3>Resumo do Pedido</h3>
        <div class="total-row">
          <span>Subtotal</span>
          <fmt:formatNumber value="${grandTotal}" type="number" pattern="#,##0.00" var="formattedGrandTotal" />
          <span>R$ ${fn:replace(formattedGrandTotal, '.', ',')}</span>
        </div>
        <div class="total-row">
          <span>Frete</span>
          <span>R$ 15,00</span>
        </div>
        <div class="total-row grand-total">
          <span>Total</span>
          <fmt:formatNumber value="${grandTotal + 15}" type="number" pattern="#,##0.00" var="formattedFinalTotal" />
          <span>R$ ${fn:replace(formattedFinalTotal, '.', ',')}</span>
        </div>
      </div>
      
      <div class="action-buttons">
        <a href="products" class="btn-action">Continuar Comprando</a>
        <a href="#" class="btn-action btn-secondary">Acompanhar Pedido</a>
      </div>
    </c:if>
    
    <c:if test="${empty products}">
      <div class="empty-cart">
        <div class="empty-cart-icon">🛒</div>
        <p>Nenhum item foi encontrado para este pedido.</p>
        <a href="products" class="btn-action" style="display: inline-block; margin-top: 20px;">
          Ver Produtos
        </a>
      </div>
    </c:if>
  </main>
  
  <footer>
    <jsp:include page="components/footer/footer.jsp"></jsp:include>
  </footer>
  
  <script src="./resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>