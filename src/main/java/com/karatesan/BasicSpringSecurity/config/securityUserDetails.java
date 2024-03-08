/*
package com.karatesan.BasicSpringSecurity.config;

import com.karatesan.BasicSpringSecurity.model.Customer;
import com.karatesan.BasicSpringSecurity.repositories.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


//majac to klase DaoAuthenticationProvider bedzie szukal stworzonych przez developera klas ktore sa userdetailsservice
//czyli bedzie mia ldo wyboru to tutaj oraz userDetailsService z pliku ProjectSecurityConfig
//majac do wyboru dwa stworzone przez developera nie ogarnie, ktory wybrac i wywali error. Trzeba zostawic jedna implementacje userdetailsservice
//uzywamy tego
@Service
public class securityUserDetails implements UserDetailsService {

    CustomerRepository customerRepository;

    public securityUserDetails(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String userName;
        String password;
        List<GrantedAuthority> authorities;
        List<Customer> customer = customerRepository.findByEmail(username);
        if(customer.size() ==0){
            throw new UsernameNotFoundException("user details not found for the user " + username);
        }
        else{
            userName = customer.get(0).getEmail();
            password = customer.get(0).getPassword();
            authorities = new ArrayList<>();
            authorities.add((new SimpleGrantedAuthority(customer.get(0).getRole())));
        }
        return new User(username,password,authorities);


    }
}
*/