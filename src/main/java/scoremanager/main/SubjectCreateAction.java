package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action; // tool.Action をインポート

// ：HttpServlet ではなく Action を継承する
public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // 登録画面（作成した subject_create.jsp）を表示する
        request.getRequestDispatcher("subject_create.jsp").forward(request, response);
    }
}