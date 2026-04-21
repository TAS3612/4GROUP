package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ===== 未ログイン対策 =====
        if (teacher == null || teacher.getSchool() == null) {
            response.sendRedirect("../login.jsp");
            return;
        }

        // ===== パラメータ取得 =====
        String subjectCd = request.getParameter("subjectCd");
        String testNoStr = request.getParameter("testNo");
        String classNum = request.getParameter("classNum"); // ★追加

        if (subjectCd == null || testNoStr == null || classNum == null) {
            response.sendRedirect("TestRegist.action");
            return;
        }

        int testNo = Integer.parseInt(testNoStr);

        // ===== 科目取得 =====
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, teacher.getSchool().getCd());

        // ===== DAO =====
        TestDao testDao = new TestDao();

        // ===== エラー管理 =====
        List<String> errorList = new ArrayList<>();

        // ===== 登録用リスト =====
        List<Test> testList = new ArrayList<>();

        // ===== 配列取得 =====
        String[] studentNos = request.getParameterValues("studentNo");
        String[] points = request.getParameterValues("point");

        // null対策
        if (studentNos == null || points == null) {
            response.sendRedirect("TestRegist.action");
            return;
        }

        // ===== ループ処理 =====
        for (int i = 0; i < studentNos.length; i++) {

            String studentNo = studentNos[i];
            String pointStr = points[i];

            // 空欄はスキップ（要件）
            if (pointStr == null || pointStr.isEmpty()) {
                continue;
            }

            int point;

            try {
                point = Integer.parseInt(pointStr);

                if (point < 0 || point > 100) {
                    errorList.add("学生番号 " + studentNo + "：0～100の範囲で入力してください");
                    continue;
                }

            } catch (NumberFormatException e) {
                errorList.add("学生番号 " + studentNo + "：数値で入力してください");
                continue;
            }

            // ===== Testオブジェクト作成 =====
            Test test = new Test();

            Student student = new Student();
            student.setNo(studentNo);
            student.setClassNum(classNum);
            student.setSchool(teacher.getSchool());
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(teacher.getSchool());
            test.setNo(testNo);
            test.setPoint(point);

            testList.add(test);
        }

        // ===== エラーがある場合 =====
        if (!errorList.isEmpty()) {

            request.setAttribute("errors", errorList);

            // 再表示用データ（ここ重要）
            request.setAttribute("subjectCd", subjectCd);
            request.setAttribute("testNo", testNo);
            request.setAttribute("classNum", classNum);

            request.getRequestDispatcher("test_regist.jsp").forward(request, response);
            return;
        }

        // ===== DB保存 =====
        boolean result = testDao.save(testList);

        if (!result) {
            request.setAttribute("error", "登録に失敗しました");
            request.getRequestDispatcher("test_regist.jsp").forward(request, response);
            return;
        }

        // ===== 成功 =====
        request.getRequestDispatcher("test_regist_done.jsp").forward(request, response);
    }
}