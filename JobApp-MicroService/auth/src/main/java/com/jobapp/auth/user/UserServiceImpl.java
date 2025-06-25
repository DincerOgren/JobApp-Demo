package com.jobapp.auth.user;

import com.jobapp.auth.exceptions.BadCredentialsException;
import com.jobapp.auth.exceptions.UserAlreadyExistException;
import com.jobapp.auth.models.*;
import com.jobapp.auth.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequest) {
        String email = userRequest.getEmail();
        if (emailExists(email)) {
            throw new UserAlreadyExistException(email);
        }
        String encryptedPass = passwordEncoder.encode(userRequest.getPassword());


        Set<Role> roles = new HashSet<>();

       // if (strRoles==null || strRoles.isEmpty()){
            Role role = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            roles.add(role);
        //}

        User savedUser = userRepository.save(new User(
                userRequest.getName(),
                encryptedPass,
                userRequest.getEmail(),
                roles
        ));

        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );

        } catch (AuthenticationException ex){
            throw new BadCredentialsException("Invalid email or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt=jwtUtil.generateJwtToken(user);
        String name = jwtUtil.getName(jwt);
        Set<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new LoginResponseDTO(
            user.getUsername(),
            name,
            jwt,
            roles
        );
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));

        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO updateRequest) {
        return null;
    }

    @Override
    public void changePassword(Long userId, ChangePasswordDTO passwordDTO) {

    }

    @Override
    public void deleteUser(Long id) {

    }

    private boolean emailExists(String email)
    {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        return user != null;
    }
}
