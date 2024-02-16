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
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/") 
public class Rest {
    private Mysql mysqldb;
    private MongoDB mongo;
    
    @Autowired
    public Rest(MongoDB mongo,Mysql mysqldb){
        this.mongo=mongo;
        this.mysqldb=mysqldb;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

     @PostMapping("/register") 
     public String register(@RequestParam String email,@RequestParam String password,HttpServletResponse response,@RequestParam String major,@RequestParam int admission,@RequestParam String nickname )
    {     
    User newUser=new User(email,password,major,admission,nickname);
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
    public boolean login(@RequestParam String email,@RequestParam String password,@CookieValue(name = "token",defaultValue ="nothing") String token,HttpServletResponse response) {
        if(token.equals("nothing")){
            if(mysqldb.login(email,password)){//没有token，进行登录
                Cookie cookie=new Cookie("token",Token.generateJWT(email));
                response.addCookie(cookie);
                //登录成功给用户token
                return true;
            }else{
                //登录失败返回false
                return false;
            }
        }else{
            //查找到了toke
            if(Token.checkTokenAndEmail(token,email)){
                //此token是正确的可以直接登录
                return true;
            }else{
                //此token是过期的或者是错误的又或者是与email不同（可能是一个人注册了两个账号）
                if(mysqldb.login(email,password)){//没有token，进行登录
                    Cookie cookie=new Cookie("token",Token.generateJWT(email));
                    response.addCookie(cookie);
                    //登录成功给用户token
                    return true;
                }else{
                    //登录失败返回false
                    return false;
                }
            }
        }
    }
    @PostMapping("/baseinfo")//给出老师基本信息
    public Baseinfo getbaseinfo(@RequestParam int teacherID) {
        return mongo.getBaseinfoByTeacherID(teacherID);
    }
    @GetMapping("/getComment")//按照认同数量排序给出指定多的评论
    public List<Comment> getComment(@RequestParam int teacherID,@RequestParam int m,@RequestParam int n){
        return mongo.findCommentsByAgreeNum(teacherID, n, m);
    }
    @PostMapping("/isagree")//对评论进行认同或者不认同
    public void postMethodName(@RequestParam String commentID,@RequestParam boolean isagree) {
        mongo.updateComment(isagree,commentID);
    }
    @PostMapping("/check")//检查token是否是对的
    public boolean check(@CookieValue(name = "token",defaultValue ="nothing") String token) {
        return Token.checkToken(token);

    }
    
    
    
    

}
