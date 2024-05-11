package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.model.User;
import com.example.demo.DTO.UserDto;
import com.example.demo.model.Role;
import com.example.demo.repository.UserRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<User> getAllClients(Pageable page) {
        return userRepository.findByRole(Role.CLIENT, page);
    }

    public Page<User> getAllAdmins(Pageable page) {
        return userRepository.findByRole(Role.ADMIN , page);
    }

    public UserDto getUser(int id) {
        User user = userRepository.findById(id).orElseThrow();
        return new UserDto(id, user.getName(), user.getPhone(), user.getEmail(), user.getAddress(), user.getCompany_name()
        );
    }

    // get user instance bt email
    
    public User getUserInstance(String email) {
        return userRepository.findByEmail(email);
    }

    public void switchStatus(int id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setStatus(!user.getStatus());
        userRepository.save(user);
    }

    public void store(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = new User.UserBuilder(userDto.getName(), userDto.getPhone(), userDto.getEmail(),
                 userDto.getPassword(), userDto.getAddress(), Role.ADMIN)
                .status(true)
                .created_at(new Timestamp(System.currentTimeMillis()))
                .build();
        userRepository.save(user);
    }

    public void storeClient(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = new User.UserBuilder(userDto.getName(), userDto.getPhone(), userDto.getEmail(),
                userDto.getPassword(), userDto.getAddress(), Role.CLIENT)
                .company_name(userDto.getCompany_name())
                .status(true)
                .created_at(new Timestamp(System.currentTimeMillis()))
                .build();
            
        userRepository.save(user);
    }

    public void update(int id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCompany_name(userDto.getCompany_name());
        userRepository.save(user);
    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return new UserDto(user.getId(), user.getName(), user.getPhone(), user.getEmail(), user.getAddress(), user.getCompany_name());
    }
    

    public UserDto findByPhone(String phone) {
        User user = userRepository.findByPhone(phone);
        return new UserDto(user.getId(), user.getName(), user.getPhone(), user.getEmail(), user.getAddress(), user.getCompany_name());
    }

    public User findByEmailInstance(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public Boolean isPhoneExist(String phone) {
        return userRepository.findByPhone(phone) != null;
    }

}
