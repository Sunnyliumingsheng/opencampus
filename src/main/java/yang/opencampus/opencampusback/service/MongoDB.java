package yang.opencampus.opencampusback.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import yang.opencampus.opencampusback.entity.Baseinfo;
import yang.opencampus.opencampusback.entity.Comment;
import yang.opencampus.opencampusback.repository.BaseinfoRepository;
import yang.opencampus.opencampusback.repository.CommentRepository;
import yang.opencampus.opencampusback.repository.MongoDBRepository;

@Service
@EnableMongoRepositories
public class MongoDB {
    private BaseinfoRepository baseinfoRepository;
    private CommentRepository commentRepository;

    public MongoDB(BaseinfoRepository baseinfoRepository,CommentRepository commentRepository){
        this.baseinfoRepository = baseinfoRepository;
        this.commentRepository=commentRepository;
    }
    
    public Baseinfo getBaseinfoByTeacherID(int id){
        return baseinfoRepository.getBaseinfoByTeacherID(id);
    }

    @Autowired
    private MongoDBRepository mongoDBRepository;
    public void updateComment(boolean isAgree,String commentID){
        mongoDBRepository.updateComment(isAgree, commentID);
    }
    public List<Comment> findCommentsByAgreeNum(int teacherID,int n,int m){
        return mongoDBRepository.findCommentsByAgreeNum(teacherID, n, m);
    }
    public List<Baseinfo> selectTeacherName(String teacherName){
        if(!teacherName.equals(""))
        return mongoDBRepository.findByNameContaining(teacherName);
        else{
            List<Baseinfo> nulllist =new ArrayList<Baseinfo>();
            return nulllist;
        }
    }
    public List<Baseinfo> deptAndSelectTeacherName(String dept,String teacherName){
        if(!teacherName.equals(""))
        return mongoDBRepository.findByNameContainingAndDept(dept,teacherName);
        else{
            List<Baseinfo> nulllist =new ArrayList<Baseinfo>();
            return nulllist;
        }
    }
    public void addComment(int teacherid, String userEmail,String className, String nickname, int EZtoPass, int EZtoHighScore, int useful,
    boolean willCheck, int recommend, String others){
        Comment newComment=new Comment(teacherid,userEmail,className,nickname,EZtoPass,EZtoHighScore,useful,willCheck,recommend,others);
        commentRepository.save(newComment);
    }
}
