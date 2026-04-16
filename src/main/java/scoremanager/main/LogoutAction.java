package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LogoutAction extends Action {
	public void execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		HttpSession session=request.getSession();
//		セッション内に顧客Beenがあるか確認
//		ユーザーがログイン中かどうか判断
		if (session.getAttribute("user")!=null) {
//			セッション属性から顧客Beenを削除
			session.removeAttribute("user");
		}
		request.getRequestDispatcher("logout.jsp").forward(request, response);
	}
}
