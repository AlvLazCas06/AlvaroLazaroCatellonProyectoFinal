package com.salesianostriana.dam.alvarolazarocastellon.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageItem {

    private int page;
    private boolean actual;

}
