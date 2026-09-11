package com.example.book_store_back.identity.infraestructure.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_store_back.identity.application.dtos.SyncFirebaseUserCommand;
import com.example.book_store_back.identity.application.dtos.UserResult;
import com.example.book_store_back.identity.application.usecases.GetCurrentUserUseCase;
import com.example.book_store_back.identity.application.usecases.SyncFirebaseUserUseCase;
import com.example.book_store_back.identity.infraestructure.api.dto.response.UserResponse;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final SyncFirebaseUserUseCase syncFirebaseUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    public AuthController(SyncFirebaseUserUseCase syncFirebaseUserUseCase,
            GetCurrentUserUseCase getCurrentUserUseCase) {
        this.syncFirebaseUserUseCase = syncFirebaseUserUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    @PostMapping("/sync")
    public ResponseEntity<UserResponse> syncUser(@AuthenticationPrincipal Jwt jwt) {

        String uid = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("name");
        String picture = jwt.getClaimAsString("picture");

        SyncFirebaseUserCommand command = new SyncFirebaseUserCommand(uid, email, name, picture);
        UserResult result = syncFirebaseUserUseCase.execute(command);

        UserResponse response = new UserResponse(result.id(), result.email(), result.fullName(), result.role(),
                result.status(), result.picture());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {

        String uid = jwt.getSubject();
        UserResult result = getCurrentUserUseCase.execute(uid);

        UserResponse response = new UserResponse(
                result.id(),
                result.email(),
                result.fullName(),
                result.role(),
                result.status(),
                result.picture());
        return ResponseEntity.ok(response);
    }

}
