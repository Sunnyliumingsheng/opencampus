package yang.opencampus.opencampusback.entity;



import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
public class User {
////////////此仅做测试用
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.major="major";
        this.admission=1;
        this.id=22;
        this.nickname="yangming";
    }
    public User( String email, String password, String major, int admission ,String nickname) {
        this.email = email;
        this.password = password;
        this.major = major;
        this.admission = admission;
        this.nickname=nickname;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 根据数据库自动生成主键
    private int id;
    private String email;
    private String password;
    private String major;
    private int admission;
    private String nickname;

    public User() {
    }
    @Override
    public String toString() {
        return "User [ email=" + email + ", password=" + password + ", major=" + major + ", admission="
                + admission + ", nickname=" + nickname + "]";
    }
    
}
