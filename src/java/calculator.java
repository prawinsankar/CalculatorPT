
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author shubham
 */
@WebServlet(urlPatterns = {"/calculator"})
public class calculator extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calculator", "root", "");
            java.sql.Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement("insert into numbers values(?,?,?,?)");

            String n1 = request.getParameter("txt1");
            String n2 = request.getParameter("txt2");
            String op = request.getParameter("op");

            ps.setInt(1, Integer.valueOf(request.getParameter("txt1")));
            ps.setInt(2, Integer.valueOf(request.getParameter("txt2")));
            ps.setString(3, request.getParameter("op"));

            switch (op) {
                case "Addition":
                    out.println("Answer = " + (Integer.parseInt(n1) + Integer.parseInt(n2)));
                    ps.setInt(4,(Integer.parseInt(n1) + Integer.parseInt(n2)));

                    break;
                case "Subtraction":
                    out.println("Answer = " + (Integer.parseInt(n1) - Integer.parseInt(n2)));
                    ps.setInt(4, (Integer.parseInt(n1) - Integer.parseInt(n2)));

                    break;
                case "multiplication":
                    out.println("Answer = " + (Integer.parseInt(n1) * Integer.parseInt(n2)));
                    ps.setInt(4, (Integer.parseInt(n1) * Integer.parseInt(n2)));

                    break;
                default:
                    out.println("Answer = " + (Integer.parseInt(n1) / Integer.parseInt(n2)));
                    ps.setInt(4, (Integer.parseInt(n1) / Integer.parseInt(n2)));

                    break;

            }
            ps.executeUpdate();
            out.println("Data inserted Successfully...");
            out.println("</html></body>");
            st.close();
            con.close();

        } catch (Exception e) {
            out.println("ERROR " + e);
        }

    }
}
