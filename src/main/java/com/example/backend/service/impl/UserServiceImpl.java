package com.example.backend.service.impl;

import com.example.backend.models.Account;
import com.example.backend.models.user.User;
import com.example.backend.payload.request.user.EditUserRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    private static final String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(regexPattern);
    ;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public ResponseEntity<?> editUser(EditUserRequest editUserRequest, Long id) {

        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            if (!Objects.isNull(editUserRequest.getFirstName()) && !editUserRequest.getFirstName().isEmpty()) {
                user.setFirstName(editUserRequest.getFirstName());
            }
            if (!Objects.isNull(editUserRequest.getLastName()) && !editUserRequest.getLastName().isEmpty()) {
                user.setLastName(editUserRequest.getLastName());
            }
            if (!Objects.isNull(editUserRequest.getDateOfBirth()) && editUserRequest.getDateOfBirth() != null) {
                user.setDateOfBirth(editUserRequest.getDateOfBirth());
            }
            if (!Objects.isNull(editUserRequest.getContactAddress()) && !editUserRequest.getContactAddress().isEmpty()) {
                user.setContactAddress(editUserRequest.getContactAddress());
            }
            if (!Objects.isNull(editUserRequest.getPersonalEmail()) && !editUserRequest.getPersonalEmail().isEmpty() && editUserRequest.getPersonalEmail().matches(regexPattern)) {
                user.setPersonalEmail(editUserRequest.getPersonalEmail());
            }
            if (!Objects.isNull(editUserRequest.getPhoneNumber()) && !editUserRequest.getPhoneNumber().isEmpty()) {
                user.setPhoneNumber(editUserRequest.getPhoneNumber());
            }
            if (!Objects.isNull(editUserRequest.getGender()) && editUserRequest.getGender() != null) {
                user.setGender(editUserRequest.getGender());
            }
            if (!Objects.isNull(editUserRequest.getIdentityCard()) && !editUserRequest.getIdentityCard().isEmpty()) {
                user.setIdentityCard(editUserRequest.getIdentityCard());
            }
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
        } else return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
    }
}
