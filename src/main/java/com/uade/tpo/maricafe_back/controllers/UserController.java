package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.dto.UserDTO;
import com.uade.tpo.maricafe_back.service.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getUsers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null || size == null) {
            return ResponseEntity.ok(userService.getUsers(PageRequest.of(0, Integer.MAX_VALUE)));
        }
        return ResponseEntity.ok(userService.getUsers(PageRequest.of(page, size)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId) {
        Optional<UserDTO> user = this.userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<UserDTO> user = this.userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<UserDTO> user = this.userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        UserDTO saved = userService.createUser(dto);

        return ResponseEntity
                .created(URI.create("/users/" + saved.getUserId()))
                .body(saved);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Integer userId,
            @RequestBody UserDTO dto) {

        UserDTO updated = userService.updateUser(userId, dto);
        return ResponseEntity.ok(updated); // 200 OK con el usuario actualizado
    }
}
