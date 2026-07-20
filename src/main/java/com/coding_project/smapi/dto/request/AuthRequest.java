package com.coding_project.smapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Credentials for authenticating an existing user")
public class AuthRequest {

    @Schema(
            description = "Unique username chosen at registration",
            example = "john.doe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @Schema(
            description = "Account password (min 8 characters)",
            example = "P@ssword123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;
}
