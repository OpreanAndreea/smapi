package com.coding_project.smapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Authentication response containing a signed JWT access token")
public class AuthResponse {

    @Schema(
            description = "Signed JWT token. Include as `Authorization: Bearer <token>` on all subsequent requests.",
            example = "<jwt-token>"
    )
    private String token;
}
