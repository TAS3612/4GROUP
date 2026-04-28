// 編集者：河端
package bean;
 
import java.io.Serializable;
 
public class School implements Serializable {
 
    // 学校コード
    private String cd;
 
    // 学校名
    private String name;
 
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
}