/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.utilerias;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author JMTN
 */
public class EnviarMail {
    public void enviarCorreo(String destinatario, String asunto, String mensaje){
        try {
            Properties p = new Properties();
            
            p.setProperty("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
                                            //cuenta de gmail
            p.setProperty("mail.smtp.user", "pract.proy.jmtn.wad.313@gmail.com");
            p.setProperty("mail.smtp.auth", "true");
            
            Session s = Session.getDefaultInstance(p);
            MimeMessage elMensaje = new MimeMessage(s);
                                                                //destinatario
            elMensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            elMensaje.setSubject(asunto);
            elMensaje.setText(mensaje);
            
            Transport t = s.getTransport("smtp");
                                                      //CONTRASEÑA DEL CORREO
            t.connect(p.getProperty("mail.smtp.user"),"PruebaCorreoProyectoPracticasWAD3CM13");
            t.sendMessage(elMensaje, elMensaje.getAllRecipients());
            t.close();
            
            //myaccount.google.com/lesssecureapps
            /*
            de VC WEBEX ESCOM17 a Todos:    9:03 AM
            myaccount.google.com/lesssecureapps
            */
            
        } catch (AddressException ex) {
            Logger.getLogger(EnviarMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EnviarMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        EnviarMail email = new EnviarMail();
        String destinatario = "max.55@live.com.mx";
        String asunto = "Registro satisfactorio";
        String texto = "Su registro fue satisfactorio...";
        email.enviarCorreo(destinatario, asunto, texto);
    }
}
