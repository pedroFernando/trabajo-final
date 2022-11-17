package com.banco.tareafinal.model;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Getter
@Setter
@Entity
@EntityValidator(
    value=com.banco.tareafinal.model.ValidadorCedula.class,
    properties= {
        @PropertyValue(name="identificacion")
    }
)
public class Cliente extends Persona {

    @Column(length=6)
    @Required(message = "Clave es requerida")
    private String clave;
    
    @Required
    private Boolean estado;

    @OneToMany(mappedBy = "cliente")
    private List<Cuenta> cuentas;

}
