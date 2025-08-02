package com.futsalBooking.advanceJavaProject.controller.jwtController;

import com.futsalBooking.advanceJavaProject.custom.MyUserDetailsService;
import com.futsalBooking.advanceJavaProject.jwtConfig.JwtService;
import com.futsalBooking.advanceJavaProject.model.Role;
import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.repository.RoleServiceRepository;
import com.futsalBooking.advanceJavaProject.repository.UsersServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtAuthService {

    @Autowired
    private UsersServiceRepository usersServiceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleServiceRepository roleServiceRepository;

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtService JwtService;

    public JwtAuthResponse register(AuthRegisterRequest request) {
        System.out.println(request.getRole());
        Users user =new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        List<Role> roles = new ArrayList<>();
        Role role;

        List<Users> users = usersServiceRepository.findAll();

        if(users.isEmpty()){
            List<Role> role1=roleServiceRepository.findAll();
            roles.addAll(role1);
        }else{
            if(request.getRole().equals("ROLE_OWNER")){
                System.out.println(request.getRole());
                Optional<Role> roleExists = roleServiceRepository.findByRole("ROLE_OWNER");
                if(roleExists.isPresent()) {
                    role = roleExists.get();
                    roles.add(role);
                }
            }
            if(request.getRole().equals("ROLE_USER")){
                System.out.println(request.getRole());
                Optional<Role> roleExists = roleServiceRepository.findByRole("ROLE_USER");
                if(roleExists.isPresent()) {
                    role = roleExists.get();
                    roles.add(role);
                }
        }

        }

        user.setRoles(roles);
        usersServiceRepository.save(user);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getPhoneNumber());
        String token=jwtService.generateToken(userDetails);

        return new JwtAuthResponse(token);
    }

    public JwtAuthResponse authenticate(JwtAuthRequest request) {
        System.out.println("authenticate:"+request.getPassword());
        System.out.println("authenticate:"+request.getEmailOrMobile());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmailOrMobile(), request.getPassword()));
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getEmailOrMobile());

        String token = jwtService.generateToken(userDetails);
        System.out.println("the token is:"+token);
        return new JwtAuthResponse(token);
    }
}
