package com.example.book_store_back.identity.infraestructure.persistence.mappers;

import org.springframework.stereotype.Component;

import com.example.book_store_back.identity.domain.Email;
import com.example.book_store_back.identity.domain.FullName;
import com.example.book_store_back.identity.domain.Password;
import com.example.book_store_back.identity.domain.ProfilePictureUrl;
import com.example.book_store_back.identity.domain.User;
import com.example.book_store_back.identity.domain.UserId;
import com.example.book_store_back.identity.infraestructure.persistence.entities.UserEntity;

@Component 
public class UserMapper {
    
    public UserEntity toEntity(User user) {
        if (user == null) return null;
        
        return new UserEntity(
                user.getUserId().value(),
                user.getEmail().value(),
                user.getFullName().value(),
                user.getPassword() != null ? user.getPassword().hash() : null,
                user.getUserRole(),
                user.getUserStatus(),
                user.getAuthProvider(),
                user.getProfilePictureUrl() != null ? user.getProfilePictureUrl().value() : null
        );
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        UserId id = new UserId(entity.getId());
        Email email = new Email(entity.getEmail());
        FullName fullName = new FullName(entity.getFullName());
        Password password = entity.getPassword() != null ? new Password(entity.getPassword()) : null;
        ProfilePictureUrl picture = entity.getPictureUrl() != null ? new ProfilePictureUrl(entity.getPictureUrl()) : null;

        return User.reconstitute(id, email, fullName, password, picture, entity.getRole(), entity.getStatus(), entity.getProvider());
    }
}