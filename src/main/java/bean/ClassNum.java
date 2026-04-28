// 編集者：河端
package bean;
 
import java.io.Serializable;
 
public class ClassNum implements Serializable {
 
   
    // クラス名
    private String classNum;
 
    // 学校
    private School school;
 
    // getter / setter
    public String getClassNum() {
        return classNum;
    }
 
    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
 
    public School getSchool() {
        return school;
    }
 
    public void setSchool(School school) {
        this.school = school;
    }
}