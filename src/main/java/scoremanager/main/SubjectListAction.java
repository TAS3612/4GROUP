package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        
        // 教員の所属校コードを取得
        String schoolCd = teacher.getSchool().getCd();
        
        SubjectDao dao = new SubjectDao();
        // filterメソッドを使用してリストを取得
        List<Subject> list = dao.filter(schoolCd);
        
        request.setAttribute("subjects", list);
        request.getRequestDispatcher("subject_list.jsp").forward(request, response);
    }
}