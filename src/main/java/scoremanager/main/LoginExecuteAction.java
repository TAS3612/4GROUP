package scoremanager.main;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginExecuteAction extends Action {
	public void execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		HttpSession session=request.getSession();

		String id=request.getParameter("id");
		String password=request.getParameter("password");

		TeacherDao tdao=new TeacherDao();
		Teacher teacher=tdao.login(id, password);
		
		if (teacher!=null) {
//			セッションIDの発行
			teacher.setAuthenticated(true);
			session.setAttribute("user", teacher);
			response.sendRedirect("Menu.action");
		}else {
			request.setAttribute("errors", "ログインに失敗しました。IDまたはパスワードが正しくありません");
            request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
	}
}
