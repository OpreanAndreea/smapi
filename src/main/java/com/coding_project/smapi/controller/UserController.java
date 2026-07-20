package com.coding_project.smapi.controller;

import com.coding_project.smapi.dto.request.AuthRequest;
import com.coding_project.smapi.dto.request.RegisterRequest;
import com.coding_project.smapi.dto.response.AuthResponse;
import com.coding_project.smapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User registration and login — returns a signed JWT token for use on all other endpoints")
public class UserController {

    @Autowired
    private UserService userService;

    // -------------------------------------------------------------------------
    // POST /auth/register
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and returns a JWT access token so the user can immediately make authenticated requests."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully — JWT token returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body or username already taken",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content)
    })
    @PostMapping("/register")
    public AuthResponse register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New user registration details", required = true,
                    content = @Content(schema = @Schema(implementation = RegisterRequest.class)))
            @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    // -------------------------------------------------------------------------
    // POST /auth/login
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Authenticate a user",
            description = "Validates credentials and returns a signed JWT Bearer token. " +
                          "Pass the returned token as `Authorization: Bearer <token>` on all secured endpoints."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authentication successful — JWT token returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Malformed request body",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid username or password",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content)
    })
    @PostMapping("/login")
    public AuthResponse login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User credentials", required = true,
                    content = @Content(schema = @Schema(implementation = AuthRequest.class)))
            @RequestBody AuthRequest request) {
        return userService.login(request);
    }
}
