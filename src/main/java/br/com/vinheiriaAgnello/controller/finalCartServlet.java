package br.com.vinheiriaAgnello.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.vinheiriaAgnello.classes.Product;

/**
 * Servlet implementation class middleCartServlet
 */
@WebServlet("/finalCart")
public class finalCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @SuppressWarnings("unchecked")
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tente obter os produtos do carrinho de uma sessão ou de um atributo anterior
        List<Product> products = (List<Product>) request.getSession().getAttribute("cart");

        // Verifique se os produtos estão disponíveis na sessão
        if (products == null) {
            products = new ArrayList<>();  // Caso contrário, crie uma lista vazia
        }
        
        // Passa os produtos para o JSP
        request.setAttribute("products", products);
        
        // Redireciona para o final-cart.jsp
        request.getRequestDispatcher("/final-cart.jsp").forward(request, response);
    }

    @SuppressWarnings("unchecked")
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tente obter os produtos do carrinho de uma sessão ou de um atributo anterior
        List<Product> products = (List<Product>) request.getSession().getAttribute("cart");

        // Verifique se os produtos estão disponíveis na sessão
        if (products == null) {
            products = new ArrayList<>();  // Caso contrário, crie uma lista vazia
        }
        
        // Passa os produtos para o JSP
        request.setAttribute("products", products);
        
        // Redireciona para o final-cart.jsp
        request.getRequestDispatcher("/final-cart.jsp").forward(request, response);
    }
}
