package scoremanager.main;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        // セッション取得
        HttpSession session = request.getSession();

        // ログインユーザー取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータから学生番号取得
        String cd = request.getParameter("cd");

        // 学生情報取得
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(cd, teacher.getSchool().getCd());

        // クラス一覧取得（ログインユーザーの学校に紐づく）
        
        // JSPへ渡す
        request.setAttribute("subject", subject);
        // 変更画面へ遷移
        request.getRequestDispatcher("subject_update.jsp")
               .forward(request, response);
    }
}