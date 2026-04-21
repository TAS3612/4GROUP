package scoremanager.main;

import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. リクエストパラメータから科目コード(cd)を取得
        String cd = request.getParameter("cd");

        // 2. セッションからログインユーザー情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 3. SubjectDaoをインスタンス化
        SubjectDao sDao = new SubjectDao();
        
        // 4. 削除処理を実行
        // 既存のget/filter/saveと同様の引数構成（cd, school_cd）で削除を依頼
        sDao.delete(cd, teacher.getSchool().getCd());

        // 5. 削除完了画面へフォワード
        request.getRequestDispatcher("subject_delete_done.jsp").forward(request, response);
    }
}