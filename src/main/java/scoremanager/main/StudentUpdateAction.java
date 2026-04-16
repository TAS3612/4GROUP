package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

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
        String no = request.getParameter("no");

        // 学生情報取得
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(no);

        // クラス一覧取得（ログインユーザーの学校に紐づく）
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classList = classNumDao.filter(teacher.getSchool());

        // JSPへ渡す
        request.setAttribute("student", student);
        request.setAttribute("classList", classList);

        // 変更画面へ遷移
        request.getRequestDispatcher("student_update.jsp")
               .forward(request, response);
    }
}