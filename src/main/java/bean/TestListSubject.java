package bean;

import java.util.HashMap;
import java.util.Map;

public class TestListSubject {

    private int entYear;              // 入学年度
    private String studentNo;         // 学籍番号
    private String studentName;       // 学生名
    private String classNum;          // クラス番号
    private Map<Integer, Integer> points = new HashMap<>(); // 回数 → 点数

    // ===== 入学年度 =====
    public int getEntYear() {
        return entYear;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    // ===== 学籍番号 =====
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    // ===== 学生名 =====
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // ===== クラス番号 =====
    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    // ===== 点数Map =====
    public Map<Integer, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }

    // ===== 特定回の点数取得 =====
    public Integer getPoint(int key) {
        return points.get(key);
    }

    // ===== 点数追加 =====
    public void putPoint(int key, int value) {
        this.points.put(key, value);
    }
}