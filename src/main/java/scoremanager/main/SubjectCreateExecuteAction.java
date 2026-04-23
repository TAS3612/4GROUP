package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");
        String schoolCd = teacher.getSchool().getCd();
        
        SubjectDao dao = new SubjectDao();
        boolean hasError = false;

        if (cd == null || cd.length() != 3) {
            request.setAttribute("error_code", "科目コードは3文字で入力してください");
            hasError = true;
        } else if (dao.get(cd, schoolCd) != null) {
            request.setAttribute("error_code", "科目コードが重複しています");
            hasError = true;
        }

        if (hasError) {
            request.setAttribute("cd", cd);
            request.setAttribute("name", name);
            request.getRequestDispatcher("subject_create.jsp").forward(request, response);
        } else {
            Subject subject = new Subject();
            subject.setSchool(schoolCd); 
            subject.setCd(cd);
            subject.setName(name);
            
            dao.save(subject);
            request.getRequestDispatcher("subject_create_done.jsp").forward(request, response);
        }
    }
}