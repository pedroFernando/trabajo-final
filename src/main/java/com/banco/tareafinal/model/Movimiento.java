package com.banco.tareafinal.model;

import java.math.*;
import java.time.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Embeddable
@EntityValidator(
    value=com.banco.tareafinal.model.ValidadorMovimientoMonto.class,
    properties= {
        @PropertyValue(name="monto"),
        @PropertyValue(name="cuenta"),
        @PropertyValue(name="tipo")
    }
)
public class Movimiento{

	@Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Required(message = "Fecha es requerido")
    private LocalDate fecha;

    @Required(message = "Tipo es requerido")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Required(message = "Monto es requerido")
    private BigDecimal monto;

    @NotNull(message = "Cuenta es requerida")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Cuenta cuenta;

    public static enum Tipo {
        DEBITO, CREDITO;
    }
    
    public void setCuenta(Cuenta cuenta) {
    	cuenta.getBalance();
    	this.cuenta = cuenta;
    }
    
    public Cuenta getCuenta() {
    	this.cuenta.getBalance();
    	return this.cuenta;
    }

}
