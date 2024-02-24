package yang.opencampus.opencampusback.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import yang.opencampus.opencampusback.entity.User;
import yang.opencampus.opencampusback.repository.UserRepository;

@Service
public class Mysql {
    private UserRepository userRepository;

    public Mysql(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(User newUser){
        User user= userRepository.save(newUser);
        return (user !=null);
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
            System.out.println("用户不存在！"+String.valueOf(email));
            return false;
        }
    }
    public boolean accountHasExist(String email){
        return userRepository.existsByEmail(email);
    }
}
