package com.coding_project.smapi.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
