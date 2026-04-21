package tool;
 
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 
@WebServlet("*.action")
public class FrontController extends HttpServlet {
 
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
 
        PrintWriter out = response.getWriter();
 
        try {
            // Search.action を取得
            String path = request.getServletPath().substring(1);
 
            // Search.action → SearchAction に変換
            String name = path.replace(".a", "A")
                              .replace('/', '.');
 
            System.out.println("★ servlet path -> " + request.getServletPath());
            System.out.println("★ class name -> " + name);
 
            // Actionクラスのインスタンス生成
            Action action = (Action) Class.forName(name)
                    .getDeclaredConstructor()
                    .newInstance();
 
            // execute実行
            action.execute(request, response);
 
        } catch (Exception e) {
            e.printStackTrace(out);
 
            // エラーページへ遷移
            request.getRequestDispatcher("error.jsp")
                   .forward(request, response);
        }
    }
 
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
 
        doGet(request, response);
    }
}