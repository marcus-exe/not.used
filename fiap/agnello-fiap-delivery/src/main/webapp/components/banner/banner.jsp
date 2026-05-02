<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<section class="hero-banner">
  <div class="banner-container">
    <div class="banner-image-container">
      <img class="banner-image" alt="Interior da Vinheria Agnello" src="assets/images/homeImage.jpg" />
      <div class="banner-overlay"></div>
    </div>
    
    <div class="banner-content">
      <div class="welcome-message">
        <h1 class="banner-title">Bem-vindo à <span>Vinheria Agnello</span></h1>
        <p class="banner-subtitle">Tradição e excelência em vinhos desde 1995</p>
        <div class="banner-cta">
          <a href="/products" class="btn-primary">Explorar Vinhos</a>
          <a href="#" class="btn-secondary">Sobre Nós</a>
        </div>
      </div>
      
      <div class="owner-message">
        <div class="message-bubble">

          <p>Olá a todos! Meu nome é Giulio Agnello e é um prazer imenso recebê-los em minha vinheiria. Gostaria de ajudá-los a escolher o vinho perfeito para o seu próximo momento. Posso auxiliar em algo?</p>

        </div>
        <img class="owner-image" alt="Giulio Agnello" src="assets/images/banner-oldman.png" />
      </div>
    </div>
  </div>
</section>

<style>
:root {
  --cor-primaria: #8b0000;
  --cor-secundaria: #2c0000;
  --cor-destaque: #d4af37;
  --cor-texto: #333;
  --cor-texto-claro: #ffffff;
  --cor-texto-subtitulo: #f0f0f0;
  --cor-borda: #e0e0e0;
  --cor-fundo-claro: #FFF3E3;
  --sombra-suave: 0 4px 15px rgba(0, 0, 0, 0.15);
  --transicao: all 0.3s ease;
  --raio-borda: 12px;
}

.hero-banner {
  position: relative;
  width: 100%;
  overflow: hidden;
  display: block;
  clear: both;
}

.banner-container {
  position: relative;
  width: 100%;
  height: 600px;
  display: flex;
  align-items: center;
}

.banner-image-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}

.banner-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

.banner-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, rgba(0,0,0,0.7) 0%, rgba(0,0,0,0.4) 50%, rgba(0,0,0,0.2) 100%);
  z-index: 2;
}

.banner-content {
  position: relative;
  z-index: 3;
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.welcome-message {
  max-width: 600px;
}

.banner-title {
  font-size: 3.2rem;
  font-weight: 700;
  margin-bottom: 15px;
  line-height: 1.2;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
  color: var(--cor-texto-claro);
}

.banner-title span {
  color: var(--cor-destaque);
  display: block;
}

.banner-subtitle {
  font-size: 1.4rem;
  margin-bottom: 30px;
  font-weight: 300;
  text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3);
  color: var(--cor-texto-subtitulo);
}

.banner-cta {
  display: flex;
  gap: 20px;
  margin-top: 30px;
}

.btn-primary, .btn-secondary {
  display: inline-block;
  padding: 14px 28px;
  border-radius: 50px;
  font-weight: 600;
  font-size: 1.1rem;
  text-decoration: none;
  transition: var(--transicao);
  text-align: center;
}

.btn-primary {
  background-color: var(--cor-primaria);
  color: white;
  border: 2px solid var(--cor-primaria);
}

.btn-primary:hover {
  background-color: var(--cor-secundaria);
  border-color: var(--cor-secundaria);
  transform: translateY(-3px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

.btn-secondary {
  background-color: transparent;
  color: white;
  border: 2px solid white;
}

.btn-secondary:hover {
  background-color: rgba(255, 255, 255, 0.1);
  transform: translateY(-3px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

.owner-message {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  max-width: 450px;
}

.owner-image {
  width: 180px;
  height: auto;
  margin-top: -10px;
  filter: drop-shadow(3px 3px 5px rgba(0, 0, 0, 0.3));
  animation: slight-bounce 3s ease-in-out infinite;
  z-index: 5;
}

.message-bubble {
  position: relative;
  background-color: var(--cor-fundo-claro);
  border-radius: 20px;
  padding: 25px;
  margin-bottom: 20px;
  max-width: 400px;
  box-shadow: var(--sombra-suave);
  animation: fade-in 1s ease-out;
}

.message-bubble::after {
  content: '';
  position: absolute;
  bottom: -15px;
  right: 30px;
  width: 30px;
  height: 30px;
  background-color: var(--cor-fundo-claro);
  clip-path: polygon(0 0, 100% 0, 50% 100%);
}

.message-bubble p {
  color: var(--cor-texto);
  font-size: 1.1rem;
  line-height: 1.6;
  margin: 0;
}

@keyframes slight-bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

@keyframes fade-in {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Ajuste para garantir que o banner n�o sobreponha outros elementos */
main {
  display: flex;
  flex-direction: column;
}

/* Garantir que as categorias fiquem abaixo do banner */
main > section:nth-child(2) {
  margin-top: 0;
  padding-top: 0;
}

/* Responsividade */
@media (max-width: 1200px) {
  .banner-title {
    font-size: 2.8rem;
  }
  
  .banner-subtitle {
    font-size: 1.3rem;
  }
  
  .owner-message {
    max-width: 400px;
  }
}

@media (max-width: 992px) {
  .banner-container {
    height: auto;
    min-height: 550px;
  }
  
  .banner-content {
    flex-direction: column;
    justify-content: center;
    text-align: center;
    padding: 60px 30px;
  }
  
  .welcome-message {
    margin-bottom: 50px;
  }
  
  .banner-cta {
    justify-content: center;
  }
  
  .banner-title span {
    display: inline;
  }
  
  .owner-message {
    align-items: center;
  }
  
  .message-bubble::after {
    right: 50%;
    transform: translateX(50%);
  }
}

@media (max-width: 768px) {
  .banner-title {
    font-size: 2.4rem;
  }
  
  .banner-subtitle {
    font-size: 1.2rem;
  }
  
  .banner-cta {
    flex-direction: column;
    gap: 15px;
    width: 100%;
    max-width: 300px;
    margin: 30px auto 0;
  }
  
  .btn-primary, .btn-secondary {
    width: 100%;
  }
  
  .message-bubble {
    padding: 20px;
    max-width: 350px;
  }
  
  .message-bubble p {
    font-size: 1rem;
  }
  
  .owner-image {
    width: 150px;
  }
}

@media (max-width: 576px) {
  .banner-container {
    min-height: 650px;
  }
  
  .banner-title {
    font-size: 2rem;
  }
  
  .banner-subtitle {
    font-size: 1.1rem;
  }
  
  .message-bubble {
    max-width: 300px;
  }
  
  .owner-image {
    width: 130px;
  }
}
</style>