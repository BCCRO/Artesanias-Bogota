package com.ud.artesanias_bogota.models.dtos;

/**
 * DTO (Data Transfer Object) para manejar la información de las facturas.
 * Contiene los datos necesarios para crear y gestionar facturas.
 */
public class FacturaDTO {

    // Documento del usuario asociado a la factura.
    private String documentoUsuario;

    /**
     * Constructor vacío de la clase FacturaDTO.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public FacturaDTO() {
    }

    /**
     * Obtiene el documento del usuario asociado a la factura.
     * 
     * @return el documento del usuario.
     */
    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    /**
     * Establece el documento del usuario asociado a la factura.
     * 
     * @param documentoUsuario el documento del usuario a establecer.
     */
    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }
}
