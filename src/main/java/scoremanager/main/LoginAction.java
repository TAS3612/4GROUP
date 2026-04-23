package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginAction extends Action {
    @Override
    public void execute(
    	    HttpServletRequest request, HttpServletResponse response
    	) throws Exception {

    	    // セッションを取得（存在しなければ null を返す）
    	    HttpSession session = request.getSession(false);

    	    // セッションが存在する場合のみ、破棄（ログアウト処理）
    	    if (session != null) {
    	        session.invalidate(); // セッション内のデータ消える
    	    }

    	    // ログイン画面（login.jsp）を表示
    	    request.getRequestDispatcher("login.jsp").forward(request, response);
    	}
}