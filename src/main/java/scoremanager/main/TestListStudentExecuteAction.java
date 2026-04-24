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

        request.setAttribute("entYearList", entYearList);
        request.setAttribute("classList", classList);
        request.setAttribute("subjectList", subjectList);

        // ===== 3. 入力チェック =====
        if (studentNo == null || studentNo.isEmpty()) {
            request.setAttribute("error", "学生番号を入力してください");
            request.getRequestDispatcher("test_list.jsp").forward(request, response);
            return;
        }

        // ===== 4. 学生情報の取得 =====
        StudentDao sDao = new StudentDao();
        Student target = sDao.get(studentNo); 

        // ===== 5. 判定と遷移のロジック =====
        if (target == null) {
            // A. 学生そのものが存在しない場合
            request.setAttribute("error1", "学生情報が存在しませんでした");
            // 入力画面(test_list.jsp)へ戻る
            request.getRequestDispatcher("test_list.jsp").forward(request, response);
            return;
        }

        // --- 学生が存在する場合の処理 ---
        // JSPに表示するためにセット（成績の有無に関わらず表示される）
        request.setAttribute("target_student", target);
        request.setAttribute("f4", studentNo);

        // 成績リストを取得
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> list = dao.filter(target);

        if (list == null || list.isEmpty()) {
            // B. 学生はいるが、成績データがない場合
            request.setAttribute("error", "成績情報が存在しませんでした");
        } else {
            // C. 成績データがある場合
            request.setAttribute("tests_student", list);
        }

        // 学生さえ見つかっていれば、結果表示用のJSP(test_list_student.jsp)へ
        request.getRequestDispatcher("test_list_student.jsp").forward(request, response);
    }
}