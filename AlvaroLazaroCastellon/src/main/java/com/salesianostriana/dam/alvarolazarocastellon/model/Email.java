package com.salesianostriana.dam.alvarolazarocastellon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private String destination;
    private String subject;
    private String body;

}
