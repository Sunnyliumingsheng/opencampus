package yang.opencampus.opencampusback;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class Mysql {
    @Autowired
    private UserRepository userRepository;
    public Mysql(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @SuppressWarnings("null")
    @Transactional
    public void register(User newUser){
        userRepository.save(newUser);
    }
    public boolean login(String email,String password){
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        // 检查是否找到了用户并验证密码
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                // 登录成功
                System.out.println("登录成功！");
                return true;
            } else {
                // 密码错误
                System.out.println("密码错误！");
                return false;
            }
        } else {
            // 用户不存在
            System.out.println("用户不存在！");
            return false;
        }
    }
}
