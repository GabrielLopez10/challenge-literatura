package com.aluracursos.litealura.utils;

import java.util.HashMap;
import java.util.Map;

public class IdiomaUtils {

    private static final Map<String, String> idiomasMap = new HashMap<>();

    static {
        idiomasMap.put("en", "Inglés");
        idiomasMap.put("es", "Español");
        idiomasMap.put("eo", "Esperanto");
        idiomasMap.put("de", "Alemán");
        idiomasMap.put("la", "Latín");
        idiomasMap.put("fi", "Fines");
    }

    public static String obtenerNombreIdioma(String codigoIdioma) {
        return idiomasMap.getOrDefault(codigoIdioma, "Desconocido");
    }
}
