// 編集者：河端
package bean;
 
public class User {
 
    // 認証済みフラグ
    // true: 認証済み
    private boolean isAuthenticated;
 
    // getter
    public boolean isAuthenticated() {
        return isAuthenticated;
    }
 
    // setter
    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
}