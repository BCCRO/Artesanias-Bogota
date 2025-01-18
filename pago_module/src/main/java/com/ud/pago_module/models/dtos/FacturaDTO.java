package com.ud.pago_module.models.dtos;

/**
 * DTO (Data Transfer Object) para representar los datos de una factura.
 * Se utiliza para transferir información entre diferentes capas de la aplicación.
 */
public class FacturaDTO {

    // Documento del usuario asociado a la factura.
    private String documentoUsuario;

    /**
     * Constructor por defecto.
     */
    public FacturaDTO() {
    }

    /**
     * Obtiene el documento del usuario asociado a la factura.
     *
     * @return El documento del usuario.
     */
    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    /**
     * Establece el documento del usuario asociado a la factura.
     *
     * @param documentoUsuario El documento del usuario.
     */
    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }
}