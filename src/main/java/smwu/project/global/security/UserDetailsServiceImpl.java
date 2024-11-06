package smwu.project.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import smwu.project.domain.Repository.UserRepository;
import smwu.project.domain.entity.User;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.SecurityErrorCode;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(username).orElseThrow(() ->
                new CustomException(SecurityErrorCode.USER_NOT_FOUND));

        return new UserDetailsImpl(user);
    }
}
