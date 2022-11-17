package com.banco.tareafinal.model;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Getter
@Setter
@MappedSuperclass
public class Persona {
    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Required(message = "Nombre es requerido")
    private String nombre;

    @Required(message = "Género es requerido")
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Required(message = "Edad es requerido")
    private Integer edad;

    @Required(message = "Identificación es requerido")
    private String identificacion;

    @Required(message = "Dirección es requerido")
    private String direccion;

    @Required(message = "Teléfono es requerido")
    private String telefono;
    
    public static enum Genero {
    	MASCULINO, FEMENINO
    }

}
