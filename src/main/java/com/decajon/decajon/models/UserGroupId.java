/**
 * Al utilizar una clave compuesta debemos generar una clase que contenga todos los id's de las
 * clases involucradas.
 *
 * La clase debe estar anotada con @Embeddable para indicar que esta clase se
 * va a utilizar como clave primaria en una entidad.
 *
 * Implementa Serializable y se sobreescriben los métodos equals() y hash()
 * para que Hibernate pueda identificar esta llave primaria compuesta.
 */
package com.decajon.decajon.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserGroupId implements Serializable
{
    private Long userId;
    private Long groupId;

    /**
     * Este metodo es necesario para las comparaciones de objetos de esta misma clase.
     * Se modifica el comportamiento para que pueda comparar a través de las dos id's que estamos
     * guardadndo. Si esto no se hace, Hibernate puede tener comportamientos inesperados.
     */
    @Override
    public boolean equals(Object o)
    {
        if(this == o) // <--- Esto indica si el objeto es él mismo
        {
            return true;
        }

        if(o == null || this.getClass() != o.getClass()) // <-- Si el objeto recibido es nulo o no es del tipo
        {
            return false;
        }

        UserGroupId that = (UserGroupId) o; // <-- Se realiza la conversión del objeto recibido a la clase

        return Objects.equals(userId, that.userId) &&
                Objects.equals(groupId, that.groupId); // <-- Se compara si son iguales y retorna el resultado
    }

    /**
     * Para cuando el objeto se almacena en alguna estructura como un Hashmap o un Hashset.
     * Ya que esas estructuras almacenan los objetos por su código hash, necesitamos que esta clase
     * sea capaz de generar un hash code basado en las dos id's que almacena.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(userId, groupId); // <-- Necesario para cuando se guarde el objeto en un hashmap
    }
}
