package br.com.vinheiriaAgnello.controller;

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
 * Servlet implementation class cartServlet
 */
@WebServlet("/cart")
public class cartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recupera a sessão do usuário
        HttpSession session = request.getSession();
        
        // Recupera o carrinho da sessão
        @SuppressWarnings("unchecked")
		List<Product> cart = (List<Product>) session.getAttribute("cart");
        
        // Se o carrinho não existir, cria uma nova lista
        if (cart == null) {
            cart = new ArrayList<>();
        }
        
        // Passa o carrinho para o JSP para exibição
        request.setAttribute("cart", cart);
        
        // Encaminha a requisição para o JSP de carrinho
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obter os parâmetros do produto
        String productId = request.getParameter("productId");
        String title = request.getParameter("title");
        String image = request.getParameter("image");
        String tag = request.getParameter("tag");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");

        // Criar o objeto Product
        Product productItem = new Product(title, image, tag, price, productId, quantity);
        
        // Recuperar o carrinho da sessão
        List<Product> cart = (List<Product>) request.getSession().getAttribute("cart");

        // Se o carrinho não existir, cria uma nova lista
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Adicionar o produto ao carrinho
        cart.add(productItem);

        // Armazenar o carrinho na sessão
        request.getSession().setAttribute("cart", cart);

        // Redirecionar para o carrinho
        response.sendRedirect("finalCart");
    }
}

