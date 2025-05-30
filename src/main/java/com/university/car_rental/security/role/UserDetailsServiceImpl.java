package com.university.car_rental.security.role;

import com.university.car_rental.entity.UserEntity;
import com.university.car_rental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        UserEntity user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Could not find user: " + email));

        return new UserDetailsImpl(user);
    }

}
