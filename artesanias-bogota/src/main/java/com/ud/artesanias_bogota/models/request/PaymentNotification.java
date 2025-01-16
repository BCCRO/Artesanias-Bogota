package com.ud.artesanias_bogota.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la notificación de pago recibida desde un sistema externo (por ejemplo, MercadoPago).
 * Contiene información relevante sobre el pago y sus metadatos.
 */
@Builder // Proporciona un patrón de construcción para la creación de instancias.
@Data // Genera automáticamente métodos getter, setter, equals, hashCode y toString.
@NoArgsConstructor // Genera un constructor vacío necesario para frameworks y serialización.
@AllArgsConstructor // Genera un constructor con todos los campos de la clase.
public class PaymentNotification {

    // Acción realizada (por ejemplo, "payment.created").
    private String action;

    // Versión de la API desde la que se envió la notificación.
    @JsonProperty("api_version")
    private String apiVersion;

    // Datos adicionales sobre la notificación.
    private Data data;

    // Fecha en la que se creó la notificación.
    @JsonProperty("date_created")
    private String dateCreated;

    // Identificador único de la notificación.
    private Long id;

    // Indica si la notificación se envió en modo live (producción).
    @JsonProperty("live_mode")
    private boolean liveMode;

    // Tipo de la notificación (por ejemplo, "payment").
    private String type;

    // Identificador del usuario asociado a la notificación.
    @JsonProperty("user_id")
    private Long userId;

    /**
     * Clase interna que representa los datos específicos de la notificación.
     * Incluye información como el identificador del objeto relacionado (por ejemplo, un pago).
     */
    public static class Data {
        // Identificador único del dato relacionado con la notificación (por ejemplo, un pago).
        private Long id;

        /**
         * Obtiene el identificador del dato relacionado.
         * 
         * @return el identificador del dato.
         */
        public Long getId() {
            return id;
        }

        /**
         * Establece el identificador del dato relacionado.
         * 
         * @param id el identificador del dato a establecer.
         */
        public void setId(Long id) {
            this.id = id;
        }
    }
}