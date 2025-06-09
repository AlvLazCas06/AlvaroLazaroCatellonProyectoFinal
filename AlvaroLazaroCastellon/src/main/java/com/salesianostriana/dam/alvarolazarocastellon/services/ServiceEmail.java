package com.salesianostriana.dam.alvarolazarocastellon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class ServiceEmail {

    @Autowired
    private JavaMailSender javaMailSender;

}
