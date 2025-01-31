package com.ud.report_module.models.dtos;

import java.util.Date;

/**
 * DTO (Data Transfer Object) para representar los datos de una factura.
 * Se utiliza para transferir información entre diferentes capas de la aplicación.
 */
public class FacturaDTO {

    // Identificador único de la factura
    private Long id;

    // Fecha y hora de emisión de la factura
    private Date fechaEmision;

    // Total de la factura
    private Long total;

    // Total de impuestos aplicados en la factura
    private Long totalImpuesto;

    // Total de descuentos aplicados en la factura
    private Long totalDescuento;

    // Documento del usuario asociado a la factura
    private String documentoUsuario;

    // Identificador de la transacción asociada
    private Long transaccionId;

    /**
     * Constructor vacío
     */
    public FacturaDTO() {
    }

    /**
     * Constructor con parámetros
     * 
     * @param id Identificador de la factura
     * @param fechaEmision Fecha y hora de emisión de la factura
     * @param total Total de la factura
     * @param totalImpuesto Total de impuestos de la factura
     * @param totalDescuento Total de descuentos de la factura
     * @param documentoUsuario Documento del usuario asociado
     * @param transaccionId Identificador de la transacción asociada
     */
    public FacturaDTO(Long id, Date fechaEmision, Long total, Long totalImpuesto, Long totalDescuento, String documentoUsuario, Long transaccionId) {
        this.id = id;
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.totalImpuesto = totalImpuesto;
        this.totalDescuento = totalDescuento;
        this.documentoUsuario = documentoUsuario;
        this.transaccionId = transaccionId;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(Long totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    public Long getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(Long totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public Long getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Long transaccionId) {
        this.transaccionId = transaccionId;
    }
}