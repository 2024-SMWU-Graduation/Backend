package smwu.project.domain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smwu.project.global.response.Response;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {
    @GetMapping
    public ResponseEntity<Response<Void>> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of("로드밸런서 Health Check API"));
    }
}
