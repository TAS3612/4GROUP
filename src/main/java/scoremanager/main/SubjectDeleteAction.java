package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. リクエストパラメータから科目コード(cd)を取得
        String cd = request.getParameter("cd");

        // 2. セッションからログインユーザー情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 3. SubjectDaoを使用して、科目コードと学校コードに合致するデータを取得
        SubjectDao sDao = new SubjectDao();
        // get(String cd, String schoolCd) に合わせて引数を渡す
        Subject subject = sDao.get(cd, teacher.getSchool().getCd());

        // 4. 取得できた場合、JSPで表示するためにリクエスト属性へセット
        if (subject != null) {
            request.setAttribute("cd", subject.getCd());
            request.setAttribute("name", subject.getName());
        }

        // 削除確認画面へフォワード
        request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
    }
}