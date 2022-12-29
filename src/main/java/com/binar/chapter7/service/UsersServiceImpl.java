package com.binar.chapter7.service;

import com.binar.chapter7.model.Users;
import com.binar.chapter7.model.request.UsersRequest;
import com.binar.chapter7.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Users> getUser() {
        return usersRepository.getUsers();
    }

    @Override
    public Users addUser(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public void updateUser(UsersRequest usersRequest) {
        Users update = usersRepository.getUsersById(usersRequest.getId());
        if (update == null) {
            logger.info("User not registered yet, please try again");
        } else
            update.setUsername(usersRequest.getUsername());
            update.setEmail(usersRequest.getEmail());
            update.setPassword(passwordEncoder.encode(usersRequest.getPassword()));
            usersRepository.updateUser(update.getUsername(), update.getEmail(), update.getPassword());
    }

    @Override
    public void deleteUser(Integer id) {
     usersRepository.deleteById(id);
    }
}
