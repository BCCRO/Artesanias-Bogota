package com.ud.pago_module.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una notificación de pago recibida desde un sistema externo (por ejemplo, MercadoPago).
 * Contiene los detalles de la notificación y la información asociada.
 */
@Builder
@Data // Genera automáticamente métodos getter, setter, equals, hashCode y toString.
@NoArgsConstructor // Genera un constructor sin argumentos.
@AllArgsConstructor // Genera un constructor con todos los argumentos.
public class PaymentNotification {

    // Acción realizada (por ejemplo, "payment.created").
    private String action;

    // Versión de la API utilizada.
    @JsonProperty("api_version")
    private String apiVersion;

    // Detalles específicos del dato asociado a la notificación.
    private Data data;

    // Fecha en que se creó la notificación.
    @JsonProperty("date_created")
    private String dateCreated;

    // Identificador de la notificación.
    private Long id;

    // Indica si el sistema está en modo live (producción) o sandbox (pruebas).
    @JsonProperty("live_mode")
    private boolean liveMode;

    // Tipo de evento asociado a la notificación (por ejemplo, "payment").
    private String type;

    // Identificador del usuario asociado al evento.
    @JsonProperty("user_id")
    private Long userId;

    /**
     * Clase interna que representa los datos específicos de la notificación.
     */
    public static class Data {

        // Identificador único de los datos asociados a la notificación.
        private Long id;

        /**
         * Obtiene el identificador de los datos.
         *
         * @return El identificador de los datos.
         */
        public Long getId() {
            return id;
        }

        /**
         * Establece el identificador de los datos.
         *
         * @param id El identificador de los datos.
         */
        public void setId(Long id) {
            this.id = id;
        }
    }
}