package com.ud.artesanias_bogota.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una respuesta de error del servidor.
 * Contiene información básica sobre el error, como el código de estado y un mensaje descriptivo.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServerErrorResponse {

    // Código de estado HTTP asociado al error (por ejemplo, 500 para error interno del servidor).
    private int statusCode;

    // Mensaje descriptivo sobre el error ocurrido.
    private String message;
}
