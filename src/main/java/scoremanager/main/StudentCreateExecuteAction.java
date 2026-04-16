package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 入力値取得
        String entYearStr = request.getParameter("ent_year");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class_num");

        // 入力チェック
        if (entYearStr == null || entYearStr.isEmpty()
                || no == null || no.isEmpty()
                || name == null || name.isEmpty()
                || classNum == null || classNum.isEmpty()) {

            request.setAttribute("error", "未入力項目があります。");
            RequestDispatcher rd =
                    request.getRequestDispatcher("student_create.jsp");
            rd.forward(request, response);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);

        StudentDao dao = new StudentDao();

        // 重複チェック
        Student oldStudent = dao.get(no);

        if (oldStudent != null) {
            request.setAttribute("error", "その学生番号は既に登録されています。");
            RequestDispatcher rd =
                    request.getRequestDispatcher("student_create.jsp");
            rd.forward(request, response);
            return;
        }

        // 学生生成
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(true);
        student.setSchool(teacher.getSchool());

        // 登録
        dao.save(student);

        // 完了画面へ
        RequestDispatcher rd =
                request.getRequestDispatcher("student_create_done.jsp");
        rd.forward(request, response);
    }
}