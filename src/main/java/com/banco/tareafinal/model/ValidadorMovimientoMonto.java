package com.banco.tareafinal.model;

import java.math.*;

import org.openxava.util.*;
import org.openxava.validators.*;

import com.banco.tareafinal.model.Movimiento.*;

import lombok.*;

@SuppressWarnings("serial")
@Getter @Setter
public class ValidadorMovimientoMonto implements IValidator{

	BigDecimal monto;
	Cuenta cuenta;
	Tipo tipo;
	
	public void validate(Messages errors) throws Exception {
		if (tipo.equals(Movimiento.Tipo.CREDITO)) {
			cuenta.getBalance();
			return;
		} else {
			BigDecimal saldo = cuenta.getBalance();
			if (saldo.compareTo(monto) < 0) {
				errors.add(String.format("El monto ingresado $%s es superior al saldo de la cuenta $%s", monto, saldo));
			}
		}
	}

}
