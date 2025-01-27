package com.ud.inventario_module.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la respuesta a una solicitud de registro de usuario.
 * Incluye detalles como el estado de la solicitud, un mensaje y la información básica del usuario registrado.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    // Código de estado de la solicitud (por ejemplo, 200 para éxito, 400 para error).
    private int statusCode;

    // Mensaje descriptivo sobre el resultado de la solicitud.
    private String message;

    // Identificador único del usuario registrado.
    private String userId;

    // Nombre del usuario registrado.
    private String userName;
}
