package scoremanager.main;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");

        // クラス一覧取得
        ClassNumDao dao = new ClassNumDao();
        List<String> classList = dao.filter(teacher.getSchool());

        // 入学年度リスト生成（今年±10年）
        int currentYear = Year.now().getValue();
        List<Integer> entYearList = new ArrayList<>();

        for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            entYearList.add(i);
        }

        // JSPへ渡す
        request.setAttribute("classList", classList);
        request.setAttribute("entYearList", entYearList);

        // 画面遷移
        RequestDispatcher rd =
                request.getRequestDispatcher("student_create.jsp");
        rd.forward(request, response);
    }
}