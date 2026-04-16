package bean;

import java.io.Serializable;
 
public class Subject implements Serializable {
 
    // 学校コード
    private String cd;
 
    // 学校名
    private String name;
    
    private String school;
 
    // getter / setter
    public String getCd() {
        return cd;
    }
 
    public void setCd(String cd) {
        this.cd = cd;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getSchool() {
        return school;
    }
 
    public void setSchool(String School) {
        this.school = School;
    }
}