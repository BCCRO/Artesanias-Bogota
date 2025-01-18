package com.ud.login_module.models.dtos;

/**
 * DTO (Data Transfer Object) para representar un correo electrónico de destino.
 * Se utiliza para transferir información sobre el destinatario de un correo.
 */
public class EmailDTO {

  // Dirección de correo electrónico del destinatario.
  private String toEmail;

  /**
   * Establece la dirección de correo electrónico del destinatario.
   *
   * @param toEmail La dirección de correo electrónico a establecer.
   */
  public void setToEmail(String toEmail) {
    this.toEmail = toEmail;
  }

  /**
   * Obtiene la dirección de correo electrónico del destinatario.
   *
   * @return La dirección de correo electrónico.
   */
  public String getToEmail() {
    return toEmail;
  }
}