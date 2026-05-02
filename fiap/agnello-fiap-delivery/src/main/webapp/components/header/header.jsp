<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header class="headerContainer">
  <div class="logoDiv">
    <a href="/" class="logo-link">
      <img class="headerImage" src="./assets/images/logo.png" alt="Logo Vinheria Agnello" />
      <h1 class="headerTitle">Vinheria Agnello</h1>
    </a>
  </div>
  <div class="navLinks">
    <a class="headerLink" href="/products">Produtos</a>
    <a class="headerLink" href="#">Sobre Nos</a>
  </div>
  <div class="cartDiv">
    <a class="cartLink" href="/cart">
      <div class="cart-icon-container">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-cart2"
          viewBox="0 0 16 16">
          <path
            d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5M3.14 5l1.25 5h8.22l1.25-5zM5 13a1 1 0 1 0 0 2 1 1 0 0 0 0-2m-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0m9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2m-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0" />
        </svg>
        <span class="cart-items-count">${cartCount > 0 ? cartCount : ''}</span>
      </div>
      <span class="cart-text">Carrinho</span>
    </a>
  </div>
  <div class="mobile-menu-toggle">
    <span></span>
    <span></span>
    <span></span>
  </div>
</header>

<style>
  :root {
    --cor-primaria: #8b0000;
    --cor-secundaria: #2c0000;
    --cor-destaque: #d4af37;
    --cor-texto: #333;
    --cor-borda: #e0e0e0;
    --sombra-suave: 0 4px 15px rgba(0, 0, 0, 0.08);
    --transicao: all 0.3s ease;
  }

  .headerContainer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 5%;
    background-color: white;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.06);
    position: sticky;
    top: 0;
    z-index: 1000;
  }

  .logoDiv {
    display: flex;
    align-items: center;
  }

  .logo-link {
    display: flex;
    align-items: center;
    text-decoration: none;
    color: var(--cor-texto);
    transition: var(--transicao);
  }

  .logo-link:hover {
    opacity: 0.9;
    text-decoration: none;
  }

  .headerImage {
    height: 50px;
    width: auto;
    margin-right: 12px;
  }

  .headerTitle {
    font-size: 1.5rem;
    font-weight: 700;
    color: var(--cor-primaria);
    margin: 0;
    letter-spacing: 0.5px;
  }

  .navLinks {
    display: flex;
    gap: 30px;
  }

  .headerLink {
    color: var(--cor-texto);
    text-decoration: none;
    font-size: 1.35rem;
    font-weight: 500;
    padding: 8px 0;
    position: relative;
    transition: var(--transicao);
  }

  .headerLink:after {
    content: '';
    position: absolute;
    width: 0;
    height: 2px;
    bottom: 0;
    left: 0;
    background-color: var(--cor-destaque);
    transition: var(--transicao);
  }

  .headerLink:hover {
    color: var(--cor-primaria);
    text-decoration: none;
  }

  .headerLink:hover:after {
    width: 100%;
  }

  .cartDiv {
    display: flex;
    align-items: center;
  }

  .cartLink {
    display: flex;
    align-items: center;
    color: var(--cor-texto);
    text-decoration: none;
    padding: 8px 15px;
    border-radius: 30px;
    transition: var(--transicao);
    background-color: #f5f5f5;
  }

  .cartLink:hover {
    background-color: #ebebeb;
    color: var(--cor-primaria);
    text-decoration: none;
  }

  .cart-icon-container {
    position: relative;
    margin-right: 8px;
  }

  .cart-items-count {
    position: absolute;
    top: -8px;
    right: -8px;
    background-color: var(--cor-primaria);
    color: white;
    border-radius: 50%;
    width: 18px;
    height: 18px;
    font-size: 0.7rem;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;
  }

  .cart-text {
    font-weight: 500;
  }

  .mobile-menu-toggle {
    display: none;
    flex-direction: column;
    justify-content: space-between;
    width: 30px;
    height: 21px;
    cursor: pointer;
  }

  .mobile-menu-toggle span {
    display: block;
    height: 3px;
    width: 100%;
    background-color: var(--cor-texto);
    border-radius: 3px;
    transition: var(--transicao);
  }

  @media (max-width: 768px) {
    .headerContainer {
      padding: 15px 4%;
    }

    .navLinks {
      position: fixed;
      top: 80px;
      left: 0;
      right: 0;
      background-color: white;
      flex-direction: column;
      align-items: center;
      padding: 20px 0;
      box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
      transform: translateY(-150%);
      transition: transform 0.3s ease;
      gap: 20px;
      z-index: 999;
    }

    .navLinks.active {
      transform: translateY(0);
    }

    .mobile-menu-toggle {
      display: flex;
    }

    .headerTitle {
      font-size: 1.2rem;
    }

    .headerImage {
      height: 40px;
    }

    .cart-text {
      display: none;
    }

    .cartLink {
      padding: 8px;
    }

    .cart-icon-container {
      margin-right: 0;
    }
  }

  @media (max-width: 480px) {
    .headerTitle {
      display: none;
    }

    .headerImage {
      margin-right: 0;
    }
  }
</style>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    const mobileMenuToggle = document.querySelector('.mobile-menu-toggle');
    const navLinks = document.querySelector('.navLinks');
    
    if (mobileMenuToggle && navLinks) {
      mobileMenuToggle.addEventListener('click', function() {
        navLinks.classList.toggle('active');
        
        // Animar as barras do menu hamb�rguer
        const spans = this.querySelectorAll('span');
        if (navLinks.classList.contains('active')) {
          spans[0].style.transform = 'rotate(45deg) translate(5px, 6px)';
          spans[1].style.opacity = '0';
          spans[2].style.transform = 'rotate(-45deg) translate(5px, -6px)';
        } else {
          spans[0].style.transform = 'none';
          spans[1].style.opacity = '1';
          spans[2].style.transform = 'none';
        }
      });
    }
  });
</script>