package com.karatesan.BasicSpringSecurity.config;

import com.karatesan.BasicSpringSecurity.model.Authority;
import com.karatesan.BasicSpringSecurity.model.Customer;
import com.karatesan.BasicSpringSecurity.repositories.CustomerRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SecurityUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

private final PasswordEncoder passwordEncoder;
private final CustomerRepository customerRepository;

    public SecurityUsernamePasswordAuthenticationProvider(PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String username = authentication.getName();
       String password = authentication.getCredentials().toString();
       List<Customer> customer = customerRepository.findByEmail(username);
       if(!customer.isEmpty()){
           if(passwordEncoder.matches(password,customer.get(0).getPassword())){
               return new UsernamePasswordAuthenticationToken(username,password,getGrantedAuthority(customer.get(0).getAuthorities()));
           }
           else{
               throw new BadCredentialsException("Invalid password!");
           }
       }
       else{
           throw new BadCredentialsException("No user registered with this details");
       }
    }

   private List<GrantedAuthority> getGrantedAuthority(Set<Authority> authorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Authority authority : authorities){
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
   }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
