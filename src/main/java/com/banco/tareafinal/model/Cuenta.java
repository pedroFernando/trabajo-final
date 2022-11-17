package com.banco.tareafinal.model;

import java.math.*;
import java.util.*;

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
public class Cuenta {
	
    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Required(message = "Numero es requerido")
    @Size(min=6, message = "Numero debe tener al menos 6 caracteres")
    private String numero;
    
    @Required(message = "Tipo es requerido")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    
    @Required(message = "Fecha es requerida")
    private Boolean estado;

    @Required(message = "Cliente es requerido")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta")
    private List<Movimiento> movimientos;

    public static enum Tipo {
        AHORRO, CORRIENTE;
    }
    
    public BigDecimal getBalance() {
    	BigDecimal creditos = getMovimientos()
    			.stream()
    			.filter(x -> x.getTipo().equals(Movimiento.Tipo.CREDITO))
    			.map(x -> x.getMonto())
    			.reduce(BigDecimal.ZERO, BigDecimal::add);
    	
    	BigDecimal debitos = getMovimientos()
    			.stream()
    			.filter(x -> x.getTipo().equals(Movimiento.Tipo.DEBITO))
    			.map(x -> x.getMonto())
    			.reduce(BigDecimal.ZERO, BigDecimal::add);
    	
    	return creditos.subtract(debitos);
    }

}
