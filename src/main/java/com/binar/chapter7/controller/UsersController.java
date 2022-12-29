package com.binar.chapter7.controller;

import com.binar.chapter7.model.Users;
import com.binar.chapter7.model.request.UsersRequest;
import com.binar.chapter7.model.response.UsersResponse;
import com.binar.chapter7.repository.UsersRepository;
import com.binar.chapter7.service.UsersServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    UsersServiceImpl usersService;

    @Operation(summary = "add new users to system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "request success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponse.class)))
    })
    @PostMapping("/add_user")
    public ResponseEntity addusers(@RequestBody UsersRequest addRequest) {
        try {
            Users users = new Users();
            users.setId(addRequest.getId());
            users.setUsername(addRequest.getUsername());
            users.setEmail(addRequest.getEmail());
            users.setPassword(addRequest.getPassword());
            usersService.addUser(users);
            return ResponseEntity.status(HttpStatus.OK).body("add success (CODE 200)");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("add failed (CODE 502), caused by : " + e.getMessage());
        }
    }

    @Operation(summary = "update user from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "request success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponse.class)))
    })
    @PutMapping("/update_user")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize(value = "hasAuthority('CUSTOMER')")
    public ResponseEntity updateUsers(@RequestBody UsersRequest usersRequest) {
            usersService.updateUser(usersRequest);
            Boolean usernameExist = usersRepository.existsByUsername(usersRequest.getUsername());
            if (Boolean.TRUE.equals(usernameExist)) {
                logger.info("Error: Username is already taken ! ");
            }
            Boolean emailExist = usersRepository.existsByEmail(usersRequest.getEmail());
            if (Boolean.TRUE.equals(emailExist)) {
                logger.info("Error: Email is already taken ! ");
            }
            if (usernameExist == false) {
            return ResponseEntity.status(HttpStatus.OK).body("update success (CODE 200)");
        } else
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("update failed (CODE 502)");
    }

    @Operation(summary = "delete user from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "request success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponse.class)))
    })
    @DeleteMapping("/delete_user")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize(value = "hasAuthority('CUSTOMER')")
    public ResponseEntity deleteUsers(@RequestParam("id") Integer id) {
        try {
            usersService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("update success (CODE 200)");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("update failed (CODE 502), caused by : " + e.getMessage());
        }
    }

}
