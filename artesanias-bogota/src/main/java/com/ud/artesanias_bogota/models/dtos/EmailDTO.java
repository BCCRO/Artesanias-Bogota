package com.ud.artesanias_bogota.models.dtos;

/**
 * DTO (Data Transfer Object) para manejar la información del correo electrónico.
 * Contiene los datos necesarios para enviar un correo electrónico.
 */
public class EmailDTO {

  // Dirección de correo electrónico del destinatario.
  private String toEmail;

  /**
   * Establece la dirección de correo electrónico del destinatario.
   * 
   * @param toEmail la dirección de correo electrónico a establecer.
   */
  public void setToEmail(String toEmail) {
    this.toEmail = toEmail;
  }

  /**
   * Obtiene la dirección de correo electrónico del destinatario.
   * 
   * @return la dirección de correo electrónico del destinatario.
   */
  public String getToEmail() {
    return toEmail;
  }
}
