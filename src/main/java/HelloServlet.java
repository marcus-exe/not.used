import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    public void init(ServletConfig config)
            throws ServletException {}

    public void destroy() {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TEST 1 //
        response.setContentType("text/html");
        response.getWriter().println("<h1>Hello from Tomcat Servlet!</h1>");

        // TEST 2 //
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TEST 2 //
        response.setContentType("text/plain");
        response.getWriter().append("Served at: ").append(request.getContextPath());

        // TEST 3: HEADERS //
        Enumeration<String> headers = request.getHeaderNames();
        StringBuilder totalOutput = new StringBuilder("Output:\n");
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            String headerValue = request.getHeader(headerName);
            totalOutput.append(headerName).append(": ").append(headerValue).append("\n");
        }
        System.out.println(totalOutput);

        // TEST 4: COOKIES //
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName()+": "+cookie.getValue());
        }
        Cookie myCookie = new Cookie("myCookie", "myValue");
        response.addCookie(myCookie);
    }
}