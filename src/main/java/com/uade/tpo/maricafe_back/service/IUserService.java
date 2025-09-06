package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.dto.UserDTO;
import com.uade.tpo.maricafe_back.entity.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserService {
    Page<UserDTO> getUsers(Pageable pageable);
    
    Optional<UserDTO> getUserById(Integer id);
    
    void deleteUserById(Integer id);
    
    UserDTO updateUser(Integer id, UserDTO dto);
    
    Optional<UserDTO> getUserByEmail(String email);
}
