package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.dto.UserResponseDTO;
import com.uade.tpo.maricafe_back.entity.dto.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Page<UserResponseDTO> getUsers(Pageable pageable);
    
    List<UserResponseDTO> getAllUsers();
    
    Optional<UserResponseDTO> getUserById(Integer id);
    
    void deleteUserById(Integer id);
    
    UserResponseDTO updateUser(Integer id, UserUpdateDTO dto);
    
    Optional<UserResponseDTO> getUserByEmail(String email);
    
    void changePassword(Integer id, String currentPassword, String newPassword);
}
