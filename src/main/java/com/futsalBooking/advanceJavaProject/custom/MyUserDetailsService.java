package com.futsalBooking.advanceJavaProject.custom;

import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.repository.UsersServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersServiceRepository usersServiceRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userOp = usersServiceRepository.findByEmail(username);
        if(!userOp.isPresent()) {
            userOp=usersServiceRepository.findByPhoneNumber(username);
        }
        Users users = userOp.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new MyUserDetails(users);
    }
}
