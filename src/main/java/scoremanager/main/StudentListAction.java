package scoremanager.main;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
 
public class StudentListAction extends Action {
 
    @Override
 
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
 
        HttpSession session = request.getSession();
        // セッションからログインユーザー取得
        Teacher teacher = (Teacher) session.getAttribute("user");
//      ここは仮のログイン
//        if (teacher == null) {
//            teacher = new Teacher();
// 
//            School school = new School();
//            school.setCd("tes");   // 学校コードに変更
// 
//            teacher.setSchool(school);
// 
//            session.setAttribute("user", teacher);
//        }
//        ここまでを最終的にコメントアウト
        
        // リクエストパラメータ
        String entYearStr = "";
        String isAttendStr = "";
        String classNum = "";
        // 変換用変数
        int entYear = 0;
        boolean isAttend = false;
        // 学生リスト
        List<Student> students = null;
        // 現在年取得
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        // DAO初期化
        StudentDao sDao = new StudentDao();
        ClassNumDao cNumDao = new ClassNumDao();
        // エラー格納
        Map<String, String> errors = new HashMap<>();
 
 
        /*
         * リクエストパラメータ取得
         */
 
        entYearStr = request.getParameter("f1");
        classNum = request.getParameter("f2");
        isAttendStr = request.getParameter("f3");
 
        /*
         * 入学年度変換
         */
 
        if (entYearStr != null && !entYearStr.equals("")) {
            entYear = Integer.parseInt(entYearStr);
        }
 
        /*
         * 在学フラグ変換
         */
 
        if (isAttendStr != null) {
            isAttend = true;
        }
 
        /*
         * 入学年度プルダウン生成（10年前～今年）
         */
 
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
 
        /*
         * クラス番号一覧取得
         */
 
        List<String> classNumSet = cNumDao.filter(teacher.getSchool());
 
        /*
         * 学生検索
         */
 
        if (entYear != 0 && classNum != null && !classNum.equals("0")) {
            // 入学年度 + クラス番号指定
            students = sDao.filter(
                    teacher.getSchool(),
                    entYear,
                    classNum,
                    isAttend
            );
        } else if (entYear != 0 && (classNum == null || classNum.equals("0"))) {
            // 入学年度のみ指定
            students = sDao.filter(
                    teacher.getSchool(),
                    entYear,
                    isAttend
            );
        } else if (entYear == 0 && (classNum == null || classNum.equals("0"))) {
            // 指定なし（全件）
            students = sDao.filter(
                    teacher.getSchool(),
                    isAttend
            );
        } else {
            // クラスだけ指定された場合
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            request.setAttribute("errors", errors);
            students = sDao.filter(
                    teacher.getSchool(),
                    isAttend
            );
        }
 
 
        /*
         * リクエストスコープへ格納
         */
 
        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
 
        if (isAttendStr != null) {
            request.setAttribute("f3", true);
        }
        request.setAttribute("students", students);
        request.setAttribute("class_num_set", classNumSet);
        request.setAttribute("ent_year_set", entYearSet);
 
        /*
         * JSPへフォワード
         */
 
        request.getRequestDispatcher("student_list.jsp")
               .forward(request, response);
    }
}