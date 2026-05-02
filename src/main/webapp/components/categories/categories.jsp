<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="br.com.vinheiriaAgnello.classes.Categories"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<section class="categories-section">
  <div class="categories-container">
    <div class="section-header">
      <h2 class="section-title">Vinhos para todos os momentos</h2>
      <p class="section-subtitle">Explore nossa seleçãpo exclusiva de vinhos premium</p>
    </div>
    
    <%
    Categories[] categories = {
      new Categories("Tintos", "assets/images/vinho-tinto.jpg"),
      new Categories("Brancos", "assets/images/vinho-branco.jpg"),
      new Categories("Ros�s", "assets/images/vinho-rose.jpg"),
      new Categories("Espumantes", "assets/images/vinho-espumante.jpg"),
      new Categories("Licorosos", "assets/images/vinho-licoroso.jpg"),
      new Categories("Sobremesa", "assets/images/vinho-sobremesa.jpg"),
    };
    request.setAttribute("categories", categories);
    %>
    
    <div class="categories-grid">
      <c:forEach var="category" items="${categories}">
        <a href="/products?category=${category.name}" class="category-card">
          <div class="card-image-container">
            <img class="card-image" src="${category.image}" alt="${category.name}" />
            <div class="card-overlay"></div>
          </div>
          <div class="card-content">
            <h3 class="card-title">${category.name}</h3>
            <div class="card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8"/>
              </svg>
            </div>
          </div>
        </a>
      </c:forEach>
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
  --cor-texto-subtitulo: #777;
  --cor-borda: #e0e0e0;
  --cor-fundo-claro: #f9f9f9;
  --sombra-suave: 0 4px 15px rgba(0, 0, 0, 0.08);
  --sombra-hover: 0 20px 30px rgba(0, 0, 0, 0.15);
  --transicao: all 0.3s ease;
  --raio-borda: 12px;
}

.categories-section {
  position: relative;
  padding: 80px 0 100px;
  background-color: var(--cor-fundo-claro);
  margin-top: 0;
  clear: both;
  display: block;
}

.categories-container {
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 30px;
}

.section-header {
  text-align: center;
  margin-bottom: 60px;
  position: relative;
}

.section-title {
  font-size: 2.8rem;
  font-weight: 700;
  color: var(--cor-texto);
  margin-bottom: 15px;
  position: relative;
  display: inline-block;
}

.section-title::after {
  content: '';
  position: absolute;
  bottom: -15px;
  left: 50%;
  transform: translateX(-50%);
  width: 80px;
  height: 3px;
  background: var(--cor-destaque);
}

.section-subtitle {
  font-size: 1.2rem;
  color: var(--cor-texto-subtitulo);
  max-width: 700px;
  margin: 20px auto 0;
}

.categories-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
}

.category-card {
  position: relative;
  border-radius: var(--raio-borda);
  overflow: hidden;
  box-shadow: var(--sombra-suave);
  transition: var(--transicao);
  text-decoration: none;
  background-color: white;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.category-card:hover {
  transform: translateY(-10px);
  box-shadow: var(--sombra-hover);
}

.card-image-container {
  position: relative;
  width: 100%;
  height: 300px;
  overflow: hidden;
}

.card-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.category-card:hover .card-image {
  transform: scale(1.1);
}

.card-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to top, rgba(0,0,0,0.6) 0%, rgba(0,0,0,0) 60%);
  transition: var(--transicao);
}

.category-card:hover .card-overlay {
  background: linear-gradient(to top, rgba(139,0,0,0.7) 0%, rgba(0,0,0,0) 70%);
}

.card-content {
  padding: 25px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: white;
  position: relative;
  z-index: 2;
}

.card-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--cor-texto);
  margin: 0;
  transition: var(--transicao);
}

.category-card:hover .card-title {
  color: var(--cor-primaria);
}

.card-icon {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: var(--cor-fundo-claro);
  border-radius: 50%;
  color: var(--cor-texto);
  transition: var(--transicao);
}

.category-card:hover .card-icon {
  background-color: var(--cor-primaria);
  color: white;
  transform: translateX(5px);
}

/* Responsividade */
@media (max-width: 1200px) {
  .categories-grid {
    grid-template-columns: repeat(3, 1fr);
    gap: 25px;
  }
  
  .card-image-container {
    height: 280px;
  }
}

@media (max-width: 992px) {
  .categories-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .section-title {
    font-size: 2.5rem;
  }
}

@media (max-width: 768px) {
  .categories-section {
    padding: 60px 0 80px;
  }
  
  .section-header {
    margin-bottom: 40px;
  }
  
  .section-title {
    font-size: 2.2rem;
  }
  
  .section-subtitle {
    font-size: 1.1rem;
  }
  
  .card-image-container {
    height: 250px;
  }
}

@media (max-width: 576px) {
  .categories-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .card-image-container {
    height: 220px;
  }
  
  .card-content {
    padding: 20px;
  }
  
  .card-title {
    font-size: 1.3rem;
  }
}
</style>