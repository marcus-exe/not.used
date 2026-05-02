package com.vinheiria.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "VinheiriaServlet", urlPatterns = {"/vinheiria", "/produtos", "/carrinho"})
public class VinheiriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CHARSET = "UTF-8";
    private static final String CONTENT_TYPE = "text/html;charset=" + CHARSET;

    @Override
    public void init() throws ServletException {
        // Inicialização do servlet
        log("VinheiriaServlet inicializado");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();
        
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        try {
            switch (servletPath) {
                case "/vinheiria":
                    handleHome(request, response, out);
                    break;
                case "/produtos":
                    handleProdutos(request, response, out);
                    break;
                case "/carrinho":
                    handleCarrinho(request, response, out);
                    break;
                default:
                    handleNotFound(response, out);
            }
        } catch (Exception e) {
            handleError(response, out, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        try {
            switch (servletPath) {
                case "/carrinho":
                    handleAddToCarrinho(request, response, out);
                    break;
                default:
                    handleNotFound(response, out);
            }
        } catch (Exception e) {
            handleError(response, out, e);
        }
    }

    private void handleHome(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Vinheiria Agnello - Home</title>");
        out.println("<meta charset='" + CHARSET + "'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Bem-vindo à Vinheiria Agnello</h1>");
        out.println("<nav>");
        out.println("<ul>");
        out.println("<li><a href='" + request.getContextPath() + "/produtos'>Produtos</a></li>");
        out.println("<li><a href='" + request.getContextPath() + "/carrinho'>Carrinho</a></li>");
        out.println("</ul>");
        out.println("</nav>");
        out.println("</body>");
        out.println("</html>");
    }

    private void handleProdutos(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Vinheiria Agnello - Produtos</title>");
        out.println("<meta charset='" + CHARSET + "'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Nossos Produtos</h1>");
        out.println("<div class='produtos'>");
        out.println("<p>Lista de produtos será implementada aqui</p>");
        out.println("</div>");
        out.println("<a href='" + request.getContextPath() + "/vinheiria'>Voltar para Home</a>");
        out.println("</body>");
        out.println("</html>");
    }

    private void handleCarrinho(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        HttpSession session = request.getSession(true);
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Vinheiria Agnello - Carrinho</title>");
        out.println("<meta charset='" + CHARSET + "'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Seu Carrinho</h1>");
        out.println("<div class='carrinho'>");
        out.println("<p>Conteúdo do carrinho será implementado aqui</p>");
        out.println("</div>");
        out.println("<a href='" + request.getContextPath() + "/vinheiria'>Voltar para Home</a>");
        out.println("</body>");
        out.println("</html>");
    }

    private void handleAddToCarrinho(HttpServletRequest request, HttpServletResponse response, PrintWriter out) 
            throws IOException {
        String produtoId = request.getParameter("produtoId");
        HttpSession session = request.getSession(true);
        
        // Lógica para adicionar ao carrinho será implementada aqui
        
        response.sendRedirect(request.getContextPath() + "/carrinho");
    }

    private void handleNotFound(HttpServletResponse response, PrintWriter out) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Página não encontrada</title></head>");
        out.println("<body>");
        out.println("<h1>404 - Página não encontrada</h1>");
        out.println("</body>");
        out.println("</html>");
    }

    private void handleError(HttpServletResponse response, PrintWriter out, Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Erro</title></head>");
        out.println("<body>");
        out.println("<h1>500 - Erro interno do servidor</h1>");
        out.println("<p>Ocorreu um erro: " + e.getMessage() + "</p>");
        out.println("</body>");
        out.println("</html>");
        log("Erro no servlet: " + e.getMessage(), e);
    }

    @Override
    public void destroy() {
        // Limpeza de recursos
        log("VinheiriaServlet destruído");
    }
} 