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

        // パラメータから科目コード取得
        String cd = request.getParameter("cd");

        // 科目情報取得
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(cd, teacher.getSchool().getCd());

        
        if (subject == null) {
            // DBに存在しない場合、エラーメッセージをセット
            request.setAttribute("errors", "科目が存在していません");
            
            // subject_update.jsp内でnull参照エラーが起きないよう、
            // 空のインスタンス、またはコードだけセットしたインスタンスを渡す
            Subject emptySubject = new Subject();
            emptySubject.setCd(cd); // 表示用にコードだけ入れておく
            request.setAttribute("subject", emptySubject);
        } else {
            // 正常に取得できた場合
            request.setAttribute("subject", subject);
        }
        // --------------------------

        // 変更画面へ遷移
        request.getRequestDispatcher("subject_update.jsp")
               .forward(request, response);
    }
}