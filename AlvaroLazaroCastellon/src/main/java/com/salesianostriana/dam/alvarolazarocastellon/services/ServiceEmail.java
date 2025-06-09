package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Email;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class ServiceEmail {

    @Autowired
    private JavaMailSender jms;

    @Autowired
    private TemplateEngine templateEngine;

    private final Locale locale = Locale.getDefault();

    public Email generateEmail(List<Juego> juegos, List<Modelo> modelos) {
        final Context ctx = new Context();
        ctx.setVariable("juegos", juegos);
        ctx.setVariable("modelos", modelos);
        ctx.setVariable("fecha", LocalDate.now());
        final String html = templateEngine.process("mail", ctx);

        Email email = new Email();
        email.setDestination("alvarolazaro101@gmail.com");
        email.setSubject("Novedades");
        email.setBody(html);

        return email;
    }

    public void sendEmail(Email email) {
        try {
            MimeMessage mimeMessage = jms.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email.getDestination());
            helper.setSubject(email.getSubject());

            helper.setText(email.getBody(), true);
            helper.setFrom("retrosgames@gmail.com");

            jms.send(mimeMessage);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
