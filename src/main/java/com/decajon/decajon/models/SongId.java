package com.decajon.decajon.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


/**
 * Clase necesaria para que Springboot pueda interpretar los Id's compuestos
 * Como los de esta tabla que se compone por 3 llaves foráneas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SongId implements Serializable
{
    private Long groupId;
    private Long artistId;
    private Long genreId;

    @Override
    public boolean equals(Object o)
    {
        // el objeto mismo
        if(this == o)
        {
            return true;
        }

        // Si el objeto recibido es nulo o de otra clase
        if(o == null || this.getClass() != o.getClass())
        {
            return false;
        }

        // si paso las validaciones
        SongId that = (SongId) o; // Conversion del obj recibido a la clase

        // comparar todos los atributos de la clase
        return  Objects.equals(groupId, that.groupId) &&
                Objects.equals(artistId, that.artistId) &&
                Objects.equals(genreId, that.genreId);
    }

    /**
     * Para hacer que el objeto sea almacenable en estructuras
     * @return
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(groupId, artistId, genreId);
    }
}
