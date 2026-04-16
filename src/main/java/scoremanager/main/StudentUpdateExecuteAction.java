package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        HttpSession session = request.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");

        // フォーム値取得
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class_num");
        int entYear = Integer.parseInt(request.getParameter("ent_year"));
        boolean isAttend = request.getParameter("is_attend") != null;

        // Studentオブジェクト生成
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(teacher.getSchool());

        // 更新処理
        StudentDao dao = new StudentDao();
        dao.save(student);

        // JSPへ渡す
        request.setAttribute("student", student);

        // 完了画面へ
        request.getRequestDispatcher("student_update_done.jsp")
               .forward(request, response);
    }
}