package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        School school = teacher.getSchool();

        // ===== 1. 入力値取得 =====
        String studentNo = request.getParameter("f4");

        // ===== 2. 共通データ作成（再表示用） =====
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        List<String> classList = classNumDao.filter(school);
        List<Subject> subjectList = subjectDao.filter(school.getCd());

        List<Integer> entYearList = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year - 10; i <= year; i++) {
            entYearList.add(i);
        }

        // JSPの変数名に合わせてセット
        request.setAttribute("entYearList", entYearList);
        request.setAttribute("classList", classList);
        request.setAttribute("subjectList", subjectList);

        // ===== 3. 入力チェック =====
        if (studentNo == null || studentNo.isEmpty()) {
            request.setAttribute("error", "学生番号を入力してください");
            request.getRequestDispatcher("test_list.jsp").forward(request, response);
            return;
        }

        // ===== 4. 学生情報と成績の取得 =====
        // ① 学生の氏名を取得する (STUDENTテーブルから取得)
        StudentDao sDao = new StudentDao();
        Student target = sDao.get(studentNo); 

        // ② 成績リストを取得する (TESTテーブルから取得)
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> list = dao.filter(target != null ? target : new Student());

        // ===== 5. 検索状態の保持 =====
        request.setAttribute("f4", studentNo);

        // ===== 6. 判定と遷移 =====
        if (list == null || list.isEmpty()) {
            request.setAttribute("error", "学生情報が存在しませんでした");
            // 失敗時は元の入力画面(test_list.jsp)へ
            request.getRequestDispatcher("test_list.jsp").forward(request, response);
        } else {
            request.setAttribute("tests_student", list);
            // 氏名を表示するためにセット
            request.setAttribute("target_student", target);
            // 結果画面へ
            request.getRequestDispatcher("test_list_student.jsp").forward(request, response);
        }
    }
}