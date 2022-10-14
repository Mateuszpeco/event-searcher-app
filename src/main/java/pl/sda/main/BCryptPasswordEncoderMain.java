package pl.sda.main;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderMain {
    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("Password123@"));//zamienia surowe hasło na postać zahashowaną
//        System.out.println(encoder.matches("Password123@", "$2a$10$mFK6JPBzUMMx4tHgH79kC.jE0FU07SjlU8FnbDDrINDdjnJchIKSu"));
    }

    public static void validatePassword() {
        String passwd = "aaZZa44@";
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        System.out.println(passwd.matches(pattern));

    }
}
