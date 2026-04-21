package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // セッション取得
        HttpSession session = request.getSession();

        // ログインユーザー（教員）取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログイン対策
        if (teacher == null) {
            response.sendRedirect("../login.jsp");
            return;
        }

        // 学校コード取得
        String schoolCd = teacher.getSchool().getCd();

        int year = java.time.LocalDate.now().getYear();
        List<Integer> entYearSet = new java.util.ArrayList<>();

        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        request.setAttribute("entYearSet", entYearSet);
        
        // DAO生成
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao();

        // クラス一覧取得
        List<String> classList = classNumDao.filter(teacher.getSchool());

        // 科目一覧取得
        List<Subject> subjectList = subjectDao.filter(schoolCd);

        // リクエストパラメータ取得
        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subjectCd = request.getParameter("subjectCd");
        String testNoStr = request.getParameter("testNo");

        // 画面表示用にセット
        request.setAttribute("classList", classList);
        request.setAttribute("subjectList", subjectList);

        // 入力値保持（再表示用）
        request.setAttribute("entYear", entYearStr);
        request.setAttribute("classNum", classNum);
        request.setAttribute("subjectCd", subjectCd);
        request.setAttribute("testNo", testNoStr);

        // 検索条件が揃っている場合のみ成績データ取得
        if (entYearStr != null && classNum != null && subjectCd != null && testNoStr != null
                && !entYearStr.isEmpty() && !classNum.isEmpty()
                && !subjectCd.isEmpty() && !testNoStr.isEmpty()) {

            int entYear = Integer.parseInt(entYearStr);
            int testNo = Integer.parseInt(testNoStr);

         // Subject取得
         Subject subject = subjectDao.get(subjectCd, teacher.getSchool().getCd());

         // 成績一覧取得
         List<Test> testList = testDao.filter(
             entYear,
             classNum,
             subject,
             testNo,
             teacher.getSchool()
         );

         
            request.setAttribute("testList", testList);

            request.setAttribute("subjectName", subject.getName());
        }

        // JSPへフォワード
        request.getRequestDispatcher("test_regist.jsp").forward(request, response);
    }
}