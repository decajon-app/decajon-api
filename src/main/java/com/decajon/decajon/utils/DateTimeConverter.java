package com.decajon.decajon.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter
{
    public static String convertToGuadalajaraTime(Instant utcInstant) {
        // Zona horaria de Guadalajara
        ZoneId guadalajaraZone = ZoneId.of("America/Mexico_City");

        // Convertir Instant (UTC) a ZonedDateTime en la zona horaria de Guadalajara
        ZonedDateTime guadalajaraDateTime = utcInstant.atZone(ZoneId.of("UTC")).withZoneSameInstant(guadalajaraZone);

        // Formatear la fecha para su presentación (ejemplo ISO 8601)
        return guadalajaraDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
}
