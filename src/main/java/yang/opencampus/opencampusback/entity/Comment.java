package yang.opencampus.opencampusback.entity;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Document(collection = "comments")
@Data
public class Comment {
    @Id
    private String _id;
    private int teacherID;
    private String userEmail;
    private String className;
    private String nickname;
    private int EZtoPass;
    private int EZtoHighScore;
    private int useful;
    private boolean willCheck;
    private int recommend;
    private String others;
    private int agreenum;

    public Comment(int teacherid, String userEmail,String className, String nickname, int EZtoPass, int EZtoHighScore, int useful,
            boolean willCheck, int recommend, String others) {
        this.teacherID = teacherid;
        this.userEmail = userEmail;
        this.nickname = nickname;
        this.EZtoPass = EZtoPass;
        this.EZtoHighScore = EZtoHighScore;
        this.useful = useful;
        this.willCheck = willCheck;
        this.recommend = recommend;
        this.others = others;
        this.agreenum=0;
        this.className=className;
    }
    public Comment() {
    }
}
