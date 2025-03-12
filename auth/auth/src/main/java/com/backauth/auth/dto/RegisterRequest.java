package com.backauth.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
    private String rol; // Por ejemplo: "ROLE_VENDEDOR", "ROLE_ADMINISTRADOR", "ROLE_COMPRADOR"
}
