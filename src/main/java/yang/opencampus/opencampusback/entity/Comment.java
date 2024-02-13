package yang.opencampus.opencampusback.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Document(collection = "comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field(name="_id",targetType = FieldType.OBJECT_ID)
    private String CommentID;
    private int teacherID;
    private int userid;
    private String nickname;
    private String EZtopass;
    private String EZtoHighScore;
    private String Useful;
    private String willCheck;
    private String recommend;
    private String others;
    private int agreenum;

    public Comment(int teacherid, int userid, String nickname, String eZtopass, String eZtoHighScore, String useful,
            String willCheck, String recommend, String others,String CommentID) {
        this.CommentID =CommentID;
        this.teacherID = teacherid;
        this.userid = userid;
        this.nickname = nickname;
        EZtopass = eZtopass;
        EZtoHighScore = eZtoHighScore;
        Useful = useful;
        this.willCheck = willCheck;
        this.recommend = recommend;
        this.others = others;
        this.agreenum=0;
    }
    public Comment() {
    }
}
