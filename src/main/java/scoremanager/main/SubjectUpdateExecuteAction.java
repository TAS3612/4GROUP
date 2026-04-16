package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. フォーム値（科目コードと新しい科目名）を取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        // 2. Subjectオブジェクトを生成して値をセット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        // Subject.javaの仕様（String型）に合わせて学校コードをセット
        subject.setSchool(teacher.getSchool().getCd());

        // 3. 更新処理
        SubjectDao sDao = new SubjectDao();
        // saveメソッドにsubjectオブジェクトを渡して保存を実行
        sDao.save(subject);

        // 4. 完了画面へ遷移
        // 科目更新用の完了画面（または一覧画面）へ飛ばします
        request.getRequestDispatcher("subject_update_done.jsp")
               .forward(request, response);
    }
}