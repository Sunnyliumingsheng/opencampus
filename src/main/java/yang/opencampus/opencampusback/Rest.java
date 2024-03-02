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
import yang.opencampus.opencampusback.utils.Token;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/")
public class Rest {
    @Autowired
    private Mysql mysqldb;
    @Autowired
    private MongoDB mongo;
    
    
    
    @PostMapping("/hello")
    public String hello() {
        
        return "hello8080";
    }

     @PostMapping("/register") 
     public String register(@RequestParam("email") String email,@RequestParam ("password") String password,@RequestParam ("major") String major,@RequestParam ("admission") int admission,@RequestParam("nickname") String nickname,HttpServletResponse response )
    {     
    User newUser=new User(email,password,major,admission,nickname);
    System.out.println(newUser.toString());
    boolean emailAlreadyRegistered;
    emailAlreadyRegistered=mysqldb.accountHasExist(email);
    if(!emailAlreadyRegistered){
        mysqldb.register(newUser);
        Cookie cookie=new Cookie("token",Token.generateJWT(email));
        response.addCookie(cookie);
        return "registered"; 
    }else{
        return "failed";
    }
////////////////////以上为注册api，输入账号和密码等信息可以进行注册,如果不存在就会注册并提供token
    } 
    @PostMapping("/login")
    public String login(@RequestParam String email,@RequestParam String password,@CookieValue(name = "token",defaultValue ="nothing") String token,HttpServletResponse response) {
        System.out.println(email+password);
        if(token.equals("nothing")){
            if(mysqldb.login(email,password)){//没有token，进行登录
                Cookie cookie=new Cookie("token",Token.generateJWT(email));
                response.addCookie(cookie);
                //登录成功给用户token
                return "login";
            }else{
                //登录失败返回false
                return "failed";
            }
        }else{
            //查找到了toke
            if(Token.checkTokenAndEmail(token,email)){
                //此token是正确的可以直接登录
                return "login";
            }else{
                //此token是过期的或者是错误的又或者是与email不同（可能是一个人注册了两个账号）
                if(mysqldb.login(email,password)){//没有token，进行登录
                    Cookie cookie=new Cookie("token",Token.generateJWT(email));
                    response.addCookie(cookie);
                    //登录成功给用户token
                    return "login";
                }else{
                    //登录失败返回false
                    return "failed";
                }
            }
        }
    }
    @PostMapping("/baseinfo")//给出老师基本信息
    public Baseinfo getbaseinfo(@RequestParam int teacherID) {
        return mongo.getBaseinfoByTeacherID(teacherID);
    }
    @PostMapping("/getComment")//按照认同数量排序给出指定多的评论
    public List<Comment> getComment(@RequestParam int teacherID,@RequestParam int max,@RequestParam int min){
        return mongo.findCommentsByAgreeNum(teacherID, min, max);
    }
    @PostMapping("/isagree")//对评论进行认同或者不认同
    public void postMethodName(@RequestParam String commentID,@RequestParam boolean isagree) {
        mongo.updateComment(isagree,commentID);
    }
    @PostMapping("/check")//检查token是否是对的
    public boolean check(@CookieValue(name = "token",defaultValue ="nothing") String token) {
        return Token.checkToken(token);

    }
    @PostMapping("/selectTeacher")
    public List<Baseinfo> selectTeacher(@RequestParam String teacherName) {
        return mongo.selectTeacherName(teacherName);
    }
    @PostMapping("/selectTeacherAndDept")
    public List<Baseinfo> selectTeacherAndDept(@RequestParam String dept,@RequestParam String teacherName) {
        return mongo.deptAndSelectTeacherName(dept, teacherName);
    }
    @PostMapping("/addComment")
    public void addComment(@CookieValue(name = "token",defaultValue ="nothing") String token,@RequestParam int teacherID,@RequestParam String className,@RequestParam String nickname,@RequestParam int eztopass,@RequestParam int eztohighscore,@RequestParam int useful,@RequestParam boolean willcheck,@RequestParam int recommend,@RequestParam String others){
        System.out.println(eztohighscore);
        String email=Token.tokenGetEmail(token);
        mongo.addComment(teacherID,email,className,nickname,eztopass,eztohighscore,useful,willcheck,recommend,others);
    }
    
    
    

}
