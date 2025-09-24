package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.dto.ApiResponseDTO;
import com.uade.tpo.maricafe_back.entity.dto.ChangePasswordDTO;
import com.uade.tpo.maricafe_back.entity.dto.UserResponseDTO;
import com.uade.tpo.maricafe_back.entity.dto.UserUpdateDTO;
import com.uade.tpo.maricafe_back.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer userId) {
        Optional<UserResponseDTO> user = this.userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteUser(@PathVariable Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok(ApiResponseDTO.success("Usuario eliminado con éxito"));
    }

    //Modificar el usuario para validar que solo el mismo usuario pueda modificar sus datos
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUser(
            @PathVariable Integer userId,
            @RequestBody UserUpdateDTO dto) {

        UserResponseDTO updated = userService.updateUser(userId, dto);
        return ResponseEntity.ok(ApiResponseDTO.success("Usuario actualizado con éxito", updated));
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<ApiResponseDTO<Void>> changePassword(
            @PathVariable Integer userId,
            @RequestBody ChangePasswordDTO dto
    ) {
        userService.changePassword(userId, dto.getNewPassword());
        return ResponseEntity.ok(ApiResponseDTO.success("Contraseña actualizada con éxito"));
    }
}

