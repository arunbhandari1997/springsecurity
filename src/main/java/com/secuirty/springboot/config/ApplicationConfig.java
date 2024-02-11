package com.secuirty.springboot.config;

import com.secuirty.springboot.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration

public class ApplicationConfig
{
   private final UserRepository userRepository;

   public ApplicationConfig(UserRepository userRepository)
   {
      this.userRepository = userRepository;
   }

   @Bean
   public UserDetailsService userDetailsService(){
      return username->userRepository.findByEmail(username)
                                     .orElseThrow(() -> new UsernameNotFoundException("User name not found"));
   }
   @Bean
   public AuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
      authenticationProvider.setUserDetailsService(userDetailsService());
      authenticationProvider.setPasswordEncoder(passwordEncoder());
      return authenticationProvider;
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
     return
             new BCryptPasswordEncoder();
   }


}