package com.example.BookStoredemo2.controller;

import com.example.BookStoredemo2.entity.UserEntity;
import com.example.BookStoredemo2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user",new UserEntity());
        return "login";
    }


    @PostMapping("/login")
    public String processLogin(@ModelAttribute("user") UserEntity userEntity, Model model){
        Optional<UserEntity> existsUser = userRepository.findByEmail(userEntity.getEmail());
        if(existsUser.isPresent()){
            if(passwordEncoder.matches(userEntity.getPassword(), existsUser.get().getPassword()) && userEntity.getUsername().equals(existsUser.get().getUsername())){
                // tạo phiên đăng nhập
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userEntity.getEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                return "redirect:/homepage";
            }
            else {
                model.addAttribute("error", "Username or Password is not correct ");
            }
        }
        else {
            model.addAttribute("error","Username or Password is not correct");
        }
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") UserEntity userEntity, Model model ){
        Optional<UserEntity> existsUser = userRepository.findByEmail(userEntity.getEmail());
        if(existsUser.isPresent()){
            model.addAttribute("error","Account is available");
            return "/register";
        }
        else {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userRepository.save(userEntity);
            return "redirect:/login";

        }
    }

}
