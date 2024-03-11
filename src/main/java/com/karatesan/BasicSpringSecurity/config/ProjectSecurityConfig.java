package com.karatesan.BasicSpringSecurity.config;

import com.karatesan.BasicSpringSecurity.filter.AuthoritiesLoggingAfterFilter;
import com.karatesan.BasicSpringSecurity.filter.CsrfCookieFilter;
import com.karatesan.BasicSpringSecurity.filter.RequestValidationBeforeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //najnowsze rozwiazanie z dokumentacji springa,
    //torzymy bean z corsConfigurationSource, ktora zawiera to co ptrzeba nam
    //nastepnie w security filterChain wrzucamy z corsa jako lambda
    //cors blokuje (przegladarka) requesty z obcego origina (np. inne url),
    //dlatego musimy ustawic preflight headery, ktore mowia co sie moze laczyc z naszym api
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        //By default, browsers restrict certain headers in cross-origin requests for security reasons.
        // The Authorization header is one of those headers that is not automatically included in cross-origin requests.
        //Without this configuration, when a request is made from a different origin (e.g., from http://localhost:4200 to your server),
        // the browser may block the client-side JavaScript code from accessing the Authorization header in the response
        // due to the same-origin policy.
        configuration.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        //nie trzeba tego nizej robic, spring domyslnei to doda  .ignoringRequestMatchers("/contact","/register")
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
                //mowimy springowi, zeby nie tworzyl jseassionid, ze sami to ogarniemy
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors((cors)->cors.configurationSource(corsConfigurationSource()))
                .csrf((csrf)-> csrf
                        .csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/contact","/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                //.csrf((csrf) -> csrf.configure()) -- domyslna konfiguracja
                //.csrf(AbstractHttpConfigurer::disable) - disable csrf
                //dodajemy filter po wskazanym filtrze
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new RequestValidationBeforeFilter(),BasicAuthenticationFilter.class)//dodajemy nasz filter przed filtrem authenticujacym
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(),BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//                        .requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT","VIEWBALANCE")
//                        .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//                        .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
                        .requestMatchers("/myAccount").hasRole("USER")
                        .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/myLoans").hasRole("USER")
                        .requestMatchers("/myCards").hasRole("USER")
                        .requestMatchers("/user").authenticated()
                        .requestMatchers("/notices", "/contact","/register").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }


}
    /*
// NoOpPasswordEncoder nie jest rekomendowany do produkcji,
    //ten encoder nic nie robi, do testow gdy w bazie mamy zapisane prawdzie haslo
    //spring wymaga jakiegos encoder wiec jak chcemy testowac todajemy tego, a on gowno rob
@Bean
public PasswordEncoder passwordEncoder(){
    return NoOpPasswordEncoder.getInstance();
}

    //jdbcuserdetailsmanager pobiera z bazy danych uzytkownika do weryfikacji,
    //zeby to dzialalo musimy miec przygotowana tebele z users i authorities w konkretny sposob
    //mamy tam username i password, nie ma weryfikacji po mailu
    //JdbcUserDetailsManager jest ok w mniejszych aplikacjiach
    //wykomentowalem to, zeby dzialal moj custom userdetailsservice
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//    }

    /*
        @Bean
        public InMemoryUserDetailsManager userDetailsService(){

        Podejscie 1
        bean tworzacy in memory user details, jest to prosty koncept, rozwiniecie tego z usera tworzonego w appliation.properties
        user jest przechowywany w pamieci aplikacji, nie korzystac w produkcji

            UserDetails admin = User.withDefaultPasswordEncoder()
                    .username("admin")
                    .authorities("admin")
                    .password("12345").build();
            UserDetails user = User.withDefaultPasswordEncoder()
                    .username("user")
                    .authorities("read")
                    .password("12345").build();
            return new InMemoryUserDetailsManager(admin,user);


        Podejscie 2
Wymaga bean z passwordEncoder

        UserDetails admin = User.withUsername("admin")
                .authorities("admin")
                .password("12345").build();
        UserDetails user = User.withUsername("user")
                .authorities("read")
                .password("12345").build();
        return new InMemoryUserDetailsManager(admin,user);

    }


    */