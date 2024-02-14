package yang.opencampus.opencampusback;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import yang.opencampus.opencampusback.entity.Baseinfo;
import yang.opencampus.opencampusback.entity.Comment;
import yang.opencampus.opencampusback.entity.User;
import yang.opencampus.opencampusback.service.MongoDB;
import yang.opencampus.opencampusback.service.Mysql;
import yang.opencampus.opencampusback.service.Token;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/") 
public class Rest {
    @Autowired
    private Mysql mysqldb;
    private MongoDB mongo;
    @Autowired
    public Rest(MongoDB mongo){
        this.mongo=mongo;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

     @PostMapping("/register") 
     public String register(@RequestParam String email,@RequestParam String password,HttpServletResponse response)
    {     

    User newUser=new User(email,password);
    boolean succesed;
    succesed=mysqldb.register(newUser);
    if(succesed){
        Cookie cookie=new Cookie("JWT",Token.generateJWT(email));
        response.addCookie(cookie);
        return "registered"; 
    }else{
        return "failed";
    }

    } 
    @PostMapping("/login")
    public boolean login(@RequestParam String email,@RequestParam String password) {
        return mysqldb.login(email, password);
    }
    @PostMapping("/baseinfo")
    public Baseinfo getbaseinfo(@RequestParam int teacherID) {
       
        return mongo.getBaseinfoByTeacherID(teacherID);
    }
    @GetMapping("/getComment")
    public List<Comment> getComment(@RequestParam int teacherID,@RequestParam int m,@RequestParam int n){
        return mongo.findCommentsByAgreeNum(teacherID, n, m);
    }
    @PostMapping("/isagree")
    public void postMethodName(@RequestParam String commentID,@RequestParam boolean isagree) {
        mongo.updateComment(isagree,commentID);
    }
    @PostMapping("/check")
    public boolean check(@RequestParam String JWT) {
        return Token.checkToken( JWT);

    }
    
    
    
    

}
