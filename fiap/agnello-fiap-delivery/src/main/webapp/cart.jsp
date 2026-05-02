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
  <title>Carrinho</title>
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

    .product-container {
      display: flex;
      flex-direction: column;
      gap: 25px;
      margin-bottom: 40px;
    }

    .product {
      display: flex;
      background-color: white;
      border-radius: var(--raio-borda);
      overflow: hidden;
      box-shadow: var(--sombra-suave);
      border: 1px solid var(--cor-borda);
      transition: var(--transicao);
    }

    .product:hover {
      transform: translateY(-3px);
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
    }

    .product-image {
      width: 200px;
      height: 220px;
      background-color: #f5f5f5;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 15px;
      border-right: 1px solid var(--cor-borda);
    }

    .product-image img {
      max-width: 85%;
      max-height: 85%;
      object-fit: contain;
    }

    .product-details {
      flex: 1;
      padding: 25px;
      display: flex;
      flex-direction: column;
    }

    .product-details h1 {
      font-size: 1.4rem;
      font-weight: 600;
      color: var(--cor-primaria);
      margin-bottom: 10px;
    }

    .price {
      font-size: 1.6rem;
      font-weight: 700;
      color: var(--cor-secundaria);
      margin-bottom: 15px;
    }

    .stars {
      color: var(--cor-destaque);
      font-size: 1.2rem;
      letter-spacing: -1px;
      margin-bottom: 15px;
    }

    .description {
      color: #666;
      font-size: 1rem;
      margin-bottom: 20px;
    }

    .quantityControl {
      display: flex;
      align-items: center;
      margin-top: auto;
    }

    .quantityControlButton {
      background-color: #f0f0f0;
      border: 1px solid var(--cor-borda);
      width: 40px;
      height: 40px;
      font-size: 1.2rem;
      font-weight: bold;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      transition: var(--transicao);
    }

    .quantityControlButton:first-child {
      border-radius: 20px 0 0 20px;
    }

    .quantityControlButton:last-child {
      border-radius: 0 20px 20px 0;
    }

    .quantityControlButton:hover {
      background-color: #e0e0e0;
    }

    .quantityControl input {
      width: 60px;
      height: 40px;
      text-align: center;
      font-size: 1rem;
      border: 1px solid var(--cor-borda);
      border-left: none;
      border-right: none;
    }

    .add-to-cart {
      background-color: var(--cor-primaria);
      color: white;
      border: none;
      border-radius: 30px;
      padding: 16px 30px;
      font-size: 1.1rem;
      font-weight: 600;
      cursor: pointer;
      transition: var(--transicao);
      display: block;
      margin: 0 auto 3rem auto;
      margin-top: 3rem;
      min-width: 250px;
      box-shadow: 0 4px 8px rgba(139, 0, 0, 0.2);
    }

    .add-to-cart:hover {
      background-color: var(--cor-secundaria);
      box-shadow: 0 6px 12px rgba(139, 0, 0, 0.3);
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

    /* Resumo do pedido */
    .order-summary {
      background-color: #f9f9f9;
      border-radius: var(--raio-borda);
      padding: 25px;
      margin-top: 30px;
      border: 1px solid var(--cor-borda);
    }

    .order-summary h2 {
      font-size: 1.3rem;
      color: var(--cor-primaria);
      margin-bottom: 20px;
      padding-bottom: 10px;
      border-bottom: 1px solid var(--cor-borda);
    }

    .summary-item {
      display: flex;
      justify-content: space-between;
      margin-bottom: 12px;
    }

    .summary-total {
      display: flex;
      justify-content: space-between;
      font-weight: 700;
      font-size: 1.2rem;
      margin-top: 15px;
      padding-top: 15px;
      border-top: 1px solid var(--cor-borda);
    }

    /* Conteúdo do carrinho e mensagem de vazio */
    .cart-content, .empty-cart-message {
      display: none;
    }

    .cart-content.active, .empty-cart-message.active {
      display: block;
    }

    @media (max-width: 768px) {
      .product {
        flex-direction: column;
      }

      .product-image {
        width: 100%;
        height: 200px;
        border-right: none;
        border-bottom: 1px solid var(--cor-borda);
      }

      .title {
        font-size: 1.8rem;
      }
    }

    @media (max-width: 480px) {
      .product-details h1 {
        font-size: 1.2rem;
      }

      .price {
        font-size: 1.4rem;
      }

      .quantityControl {
        margin-top: 15px;
      }
    }
  </style>
</head>
<body>
  <jsp:include page="components/header/header.jsp"></jsp:include>
  <main>
    <h1 class="title">Meu Carrinho</h1>
    
    <div id="cart-content" class="cart-content active">
      <c:if test="${not empty cart}">
        <form action="finalCart" method="post" id="cart-form">
          <div class="product-container" id="product-container">
            <c:forEach var="cartItem" items="${cart}">
              <div class="product" id="product-${cartItem.id}">
                <div class="product-image">
                  <img src="${cartItem.image}" alt="${cartItem.title}">
                </div>
                <div class="product-details">
                  <h1>${cartItem.title}</h1>
                  <div class="stars">
                    <span>★★★★☆</span>
                  </div>
                  <p class="description">${cartItem.tag}</p>
                  <h2 class="price">R$ <span class="item-price" data-price="${cartItem.price}">${cartItem.price}</span></h2>
                  
                  <div class="quantityControl">
                    <button class="quantityControlButton" type="button" onclick="decrease(${cartItem.id})">-</button>
                    <input type="text" id="quantity-${cartItem.id}" name="quantity-${cartItem.id}"
                      value="${cartItem.quantity}" readonly class="item-quantity" data-id="${cartItem.id}">
                    <button class="quantityControlButton" type="button" onclick="increase(${cartItem.id})">+</button>
                  </div>
                  
                  <input type="hidden" name="productId-${cartItem.id}" value="${cartItem.id}" />
                  <input type="hidden" name="title-${cartItem.id}" value="${cartItem.title}" />
                  <input type="hidden" name="image-${cartItem.id}" value="${cartItem.image}" />
                  <input type="hidden" name="tag-${cartItem.id}" value="${cartItem.tag}" />
                  <input type="hidden" name="price-${cartItem.id}" value="${cartItem.price}" />
                </div>
              </div>
            </c:forEach>
          </div>
          
          <div class="order-summary">
            <h2>Resumo do Pedido</h2>
            <div class="summary-item">
              <span>Subtotal</span>
              <span>R$ <span id="subtotal">0,00</span></span>
            </div>
            <div class="summary-item">
              <span>Frete</span>
              <span>R$ <span id="frete">15,00</span></span>
            </div>
            <div class="summary-total">
              <span>Total</span>
              <span>R$ <span id="total">0,00</span></span>
            </div>
          </div>
          
          <button class="add-to-cart" type="submit">Finalizar Compra</button>
        </form>
      </c:if>
    </div>
    
    <div id="empty-cart-message" class="empty-cart-message">
      <div class="empty-cart">
        <div class="empty-cart-icon">🛒</div>
        <p>Seu carrinho está vazio</p>
        <p>Adicione produtos para continuar comprando</p>
        <a href="products" class="add-to-cart" style="text-decoration: none; display: inline-block; margin-top: 20px;">
          Ver Produtos
        </a>
      </div>
    </div>
  </main>
  
  <footer>
    <jsp:include page="components/footer/footer.jsp"></jsp:include>
  </footer>
  
  <script>
    // Verificar se o carrinho está vazio ao carregar a página
    document.addEventListener('DOMContentLoaded', function() {
      checkEmptyCart();
      updateOrderSummary();
    });
    
    // Função para verificar se o carrinho está vazio
    function checkEmptyCart() {
      const productContainer = document.getElementById('product-container');
      const cartContent = document.getElementById('cart-content');
      const emptyCartMessage = document.getElementById('empty-cart-message');
      
      if (!productContainer || productContainer.children.length === 0) {
        cartContent.classList.remove('active');
        emptyCartMessage.classList.add('active');
      } else {
        cartContent.classList.add('active');
        emptyCartMessage.classList.remove('active');
      }
    }
    
    // Função para formatar números como moeda
    function formatCurrency(value) {
      return parseFloat(value).toFixed(2).replace('.', ',');
    }
    
    // Função para calcular o subtotal
    function calculateSubtotal() {
      let subtotal = 0;
      const items = document.querySelectorAll('.item-quantity');
      
      items.forEach(item => {
        const id = item.dataset.id;
        const quantity = parseInt(item.value);
        const priceElement = document.querySelector(`.item-price[data-price]`);
        
        if (priceElement) {
          const price = parseFloat(priceElement.dataset.price);
          subtotal += price * quantity;
        }
      });
      
      return subtotal;
    }
    
    // Função para atualizar o resumo do pedido
    function updateOrderSummary() {
      const subtotal = calculateSubtotal();
      const freteValue = parseFloat(document.getElementById('frete').innerText.replace(',', '.'));
      const total = subtotal + freteValue;
      
      document.getElementById('subtotal').innerText = formatCurrency(subtotal);
      document.getElementById('total').innerText = formatCurrency(total);
    }
    
    // Função para aumentar a quantidade
    function increase(productId) {
      let quantityInput = document.getElementById("quantity-" + productId);
      if (quantityInput) {
        quantityInput.value = parseInt(quantityInput.value) + 1;
        updateOrderSummary();
      } else {
        console.error("Input de quantidade não encontrado para o produto com ID: " + productId);
      }
    }
    
    // Função para diminuir a quantidade e remover o produto se a quantidade for 1
    function decrease(productId) {
      let quantityInput = document.getElementById("quantity-" + productId);
      if (quantityInput) {
        let current = parseInt(quantityInput.value);
        if (current > 1) {
          quantityInput.value = current - 1;
          updateOrderSummary();
        } else {
          // Remover o produto do carrinho quando a quantidade for 1
          removeProduct(productId);
        }
      } else {
        console.error("Input de quantidade não encontrado para o produto com ID: " + productId);
      }
    }
    
    // Função para remover o produto do carrinho
    function removeProduct(productId) {
      // Encontrar o produto no DOM e removê-lo
      let productElement = document.getElementById("product-" + productId);
      if (productElement) {
        productElement.remove(); // Remove o item do carrinho na página
        updateOrderSummary();
        
        // Verificar se o carrinho está vazio após a remoção
        checkEmptyCart();
      }
    }
  </script>
  <script src="./resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>