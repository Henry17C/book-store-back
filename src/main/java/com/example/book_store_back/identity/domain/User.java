package com.example.book_store_back.identity.domain;

import java.util.Objects;

public class User {

    private UserId id;
    private Email email;
    private FullName fullName;
    private Password password;
    private UserRole role;
    private UserStatus status;
    private AuthProvider provider;

    private User(UserId id, Email email, FullName fullName, Password password,
            UserRole role, UserStatus status, AuthProvider provider) {

        this.id = Objects.requireNonNull(id, "El id del usuario no puede ser nulo");
        this.email = Objects.requireNonNull(email, "El email del usuario no puede ser nulo.");
        this.fullName = Objects.requireNonNull(fullName, "El nombre del usuario no puede ser nulo.");
        this.password = password;
        this.role = Objects.requireNonNull(role, "El rol del usuario no puede ser nulo.");
        this.status = Objects.requireNonNull(status, "El estado del usuario no puede ser nulo.");
        this.provider = Objects.requireNonNull(provider, "El proveedor de registro de usuario no puede ser nulo.");

    }

    // Factory
    public static User registerWithGoogle(UserId id, Email email, FullName fullName) {
        return new User(id, email, fullName, null, UserRole.CUSTOMER, UserStatus.ACTIVE, AuthProvider.GOOGLE);
    }

    public static User registerWithEmail(UserId id, Email email, FullName fullName, Password password) {
        return new User(id, email, fullName, password, UserRole.CUSTOMER, UserStatus.PENDING_VERIFICATION,
                AuthProvider.LOCAL);
    }

    // Validación de la logica de negocio
    public void suspendAccount (){
        this.status= UserStatus.SUSPENDED;
    }

    public void promoteToAdmin(){
        if (this.status == UserStatus.SUSPENDED) {
        throw new IllegalStateException("No se puede promover a un usuario suspendido");
    }
        this.role= UserRole.ADMIN;
    }

    public void verifyEmail() {
        if (this.status != UserStatus.PENDING_VERIFICATION) {
            throw new IllegalStateException("El usuario ya está activo o suspendido");
        }
        this.status = UserStatus.ACTIVE;
    }

    //Getters
    public UserId getUserId(){
        return this.id;
    }

    public Email getEmail(){
        return  this.email;
    }

    public FullName getFullName(){
        return this.fullName;
    }

    public Password getPassword(){
        return this.password;
    }

    public UserRole getUserRole(){
        return this.role;
    }

    public UserStatus getUserStatus(){
        return this.status;
    }

    public AuthProvider getAuthProvider(){
        return this.provider;
    }

}
