package com.coding_project.smapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health", description = "Basic health-check endpoint — does not require authentication")
public class WelcomeController {

    @Operation(
            summary = "API health check",
            description = "Returns a plain-text message confirming that the Smart Management API is running. " +
                          "This endpoint is publicly accessible and requires no JWT token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "API is running",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Smart Task API is running!"))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @GetMapping("/")
    public String welcome() {
        return "Smart Task API is running!";
    }
}
