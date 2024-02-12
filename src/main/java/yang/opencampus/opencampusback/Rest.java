package yang.opencampus.opencampusback;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/") 
public class Rest {
    @Autowired
    private Mysql mysqldb;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
     @PostMapping("/register") 
     public String register(@RequestParam String email,@RequestParam String password)
{     

    User yang=new User(email,password);
    mysqldb.register(yang);
    return "registered"; 
} 
    @PostMapping("/login")
    public boolean login(@RequestParam String email,@RequestParam String password) {
        return mysqldb.login(email, password);
    }
    @PostMapping("/baseinfo")
    public Baseinfo getbaseinfo(@RequestParam int teacherID) {

        MongoDB mongo=new MongoDB();
        return mongo.getBaseinfoByTeacherID(teacherID);
    }
    
    
    
    

}
