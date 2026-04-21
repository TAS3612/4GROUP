package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    public void execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        // ===== セッション取得 =====
        HttpSession session = request.getSession();

        // ===== ユーザー情報取得 =====
        Teacher teacher = (Teacher) session.getAttribute("user");

        // nullチェック（保険）
        if (teacher == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        School school = teacher.getSchool();

        // ===== クラス一覧取得 =====
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classList = classNumDao.filter(school);

        // ===== 科目一覧取得 =====
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(school.getCd());

        // ===== 入学年度リスト作成 =====
        List<Integer> entYearList = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = year - 10; i <= year; i++) {
            entYearList.add(i);
        }

        // ===== リクエストにセット =====
        request.setAttribute("classList", classList);
        request.setAttribute("subjectList", subjectList);
        request.setAttribute("entYearList", entYearList);

        // ===== 画面遷移 =====
        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }
}