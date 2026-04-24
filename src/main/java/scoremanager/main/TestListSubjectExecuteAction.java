package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    public void execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        HttpSession session = request.getSession();

        // ===== ユーザー取得 =====
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        School school = teacher.getSchool();

        // ===== 入力値取得 =====
        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subjectCd = request.getParameter("subject");

        // ===== 共通データ（再表示用） =====
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();

        List<String> classList = classNumDao.filter(school);
        List<Subject> subjectList = subjectDao.filter(school.getCd());

        List<Integer> entYearList = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year - 10; i <= year; i++) {
            entYearList.add(i);
        }

        request.setAttribute("classList", classList);
        request.setAttribute("subjectList", subjectList);
        request.setAttribute("entYearList", entYearList);

        // ===== 入力チェック（代替フロー①） =====
        if (entYearStr == null || entYearStr.isEmpty()
            || classNum == null || classNum.isEmpty()
            || subjectCd == null || subjectCd.isEmpty()) {

            request.setAttribute("error", "入学年度とクラスと科目を選択してください");

            request.getRequestDispatcher("test_list.jsp")
                   .forward(request, response);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);

        // ===== DAOで検索 =====
        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> list =
            dao.filter(entYear, classNum, subjectCd, school);

        // ===== データ0件（代替フロー②） =====
        if (list == null || list.isEmpty()) {

            request.setAttribute("error", "学生情報が存在しませんでした");

            request.getRequestDispatcher("test_list_subject.jsp")
                   .forward(request, response);
            return;
        }

        // ===== 正常表示 =====
        request.setAttribute("list", list);
        request.setAttribute("selectedEntYear", entYear);
        request.setAttribute("selectedClassNum", classNum);
        request.setAttribute("selectedSubject", subjectCd);

        request.getRequestDispatcher("test_list_subject.jsp")
               .forward(request, response);
    }
}