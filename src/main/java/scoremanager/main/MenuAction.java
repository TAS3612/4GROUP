package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class MenuAction extends Action {
    @Override
    public void execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        // 1. セッションの取得（falseを指定して、なければnullを返す）
        HttpSession session = request.getSession(false);

        // 2. 認証チェック
        // セッションがない、またはセッションに "teacher" が保存されていない場合
        if (session == null || session.getAttribute("user") == null) {
            // ログイン画面へ強制リダイレクト
            response.sendRedirect("Login.action");
            return;
        }

        // 3. メインメニュー画面（menu.jsp）を表示
        // 認証が通っていれば、フォワードして画面を表示する
        request.getRequestDispatcher("menu.jsp").forward(request, response);
    }
}