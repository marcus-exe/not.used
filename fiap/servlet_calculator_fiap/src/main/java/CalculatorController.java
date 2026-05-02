import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CalculatorController")
public class CalculatorController extends HttpServlet {
    public CalculatorController() {
        super();
    }
    public void init(ServletConfig config)
            throws ServletException {}

    public void destroy() {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int result = 0;

        if (request.getParameter("operacao").equals("soma")) {

            result = Integer.parseInt(request.getParameter("valor1")) + Integer.parseInt(request.getParameter("valor2"));

        } else if (request.getParameter("operacao").equals("subtracao")) {

            result = Integer.parseInt(request.getParameter("valor1")) - Integer.parseInt(request.getParameter("valor2"));

        } else if (request.getParameter("operacao").equals("multiplicacao")) {

            result = Integer.parseInt(request.getParameter("valor1")) * Integer.parseInt(request.getParameter("valor2"));

        } else if (request.getParameter("operacao").equals("divisao")) {

            result = Integer.parseInt(request.getParameter("valor1")) / Integer.parseInt(request.getParameter("valor2"));
        }


        request.setAttribute("answer", result);
        System.out.println("Resultado: " + result);
        request.getRequestDispatcher("answer.jsp").forward(request, response);
    }
}
