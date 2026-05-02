package br.com.vinheiriaAgnello.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.vinheiriaAgnello.classes.Product;

/**
 * Servlet implementation class productsServlet
 */
@WebServlet("/products")
public class productsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product [] products = {
        		new Product("Concha Y Toro Reservado", "./assets/images/concha.webp", "Chileno", "25,00", "1", "0"),
        		new Product("Casillero del Diablo", "./assets/images/casillero.jpg", "Chileno", "40,00",  "2", "0"),
        		new Product("Pata Negra", "./assets/images/pata-negra.png", "Espanhol", "45,00", "3", "0"),
        		new Product("Concha Y Toro Reservado", "./assets/images/concha.webp", "Chileno", "25,00", "4", "0")
        };

        
        request.setAttribute("products", products);

        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/products.jsp");
        dispatcher.forward(request, response);
    }
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    String title = request.getParameter("title");
	    String image = request.getParameter("image");
	    String tag = request.getParameter("tag");
	    String price = request.getParameter("price");
	    String id = request.getParameter("id");
	    String quantity = "1";
	    
	    
        Product selectedProduct = new Product(title, image, tag, price, id, quantity);
        
        // Obtendo a sessão do usuário
        HttpSession session = request.getSession();
        
        // Recuperando o carrinho da sessão (caso ele já exista)
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        
        // Se o carrinho não existir, cria uma nova lista
        if (cart == null) {
            cart = new ArrayList<>();
        }
        
        // Adiciona o produto à lista (carrinho)
        cart.add(selectedProduct);
        
        // Armazena a lista atualizada na sessão
        session.setAttribute("cart", cart);	
        
        
        request.setAttribute("productAdded", true);
        
        // retornando todos os produtos
        
        Product [] products = {
        		new Product("Concha Y Toro Reservado", "./assets/images/concha.webp", "Chileno", "25,00", "1", "0"),
        		new Product("Casillero del Diablo", "./assets/images/casillero.jpg", "Chileno", "40,00",  "2", "0"),
        		new Product("Pata Negra", "./assets/images/pata-negra.png", "Espanhol", "45,00", "3", "0"),
        		new Product("Concha Y Toro Reservado", "./assets/images/concha.webp", "Chileno", "25,00", "4", "0")
        };
        
        request.setAttribute("products", products);        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/products.jsp");
        dispatcher.forward(request, response);
	}
	
}
