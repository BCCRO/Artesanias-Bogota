package com.ud.login_module.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentNotification {

    private String action;
    @JsonProperty("api_version")
    private String apiVersion;
    private Data data;
    @JsonProperty("date_created")
    private String dateCreated;
    private Long id;
    @JsonProperty("live_mode")
    private boolean liveMode;
    private String type;
    @JsonProperty("user_id")
    private Long userId;

    // Getters y setters
    public static class Data {
        private Long id;

        // Getters y setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
