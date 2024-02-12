package yang.opencampus.opencampusback;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Document(collection = "comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String CommentID;
    private int teacherid;
    private int userid;
    private String nickname;
    private String EZtopass;
    private String EZtoHighScore;
    private String Useful;
    private String willCheck;
    private String recommend;
    private String others;
    private int agree;

    public Comment(int teacherid, int userid, String nickname, String eZtopass, String eZtoHighScore, String useful,
            String willCheck, String recommend, String others) {
        this.teacherid = teacherid;
        this.userid = userid;
        this.nickname = nickname;
        EZtopass = eZtopass;
        EZtoHighScore = eZtoHighScore;
        Useful = useful;
        this.willCheck = willCheck;
        this.recommend = recommend;
        this.others = others;
        this.agree=0;
    }
    public Comment() {
    }
}
