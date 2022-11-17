package com.banco.tareafinal.model;

import java.util.*;

import org.openxava.util.*;
import org.openxava.validators.*;

import lombok.*;

@SuppressWarnings("serial")
@Getter @Setter
public class ValidadorCedula implements IValidator{

	String identificacion;
	
	public void validate(Messages errors) throws Exception {
		if (validar(identificacion)) {
			return;
		} else {
			errors.add(String.format("La identificación %s es incorrecta", identificacion));
		}
	}
	
	private static final int NUM_PROVINCIAS = 24;

    public static Boolean validar(String identificacion) {
        try {
            identificacion = identificacion.trim();
            if (identificacion.length() < 10) {
                return Boolean.FALSE;
            }
            if (identificacion.equals("9999999999999") || identificacion.equals("9999999999")) {
                return Boolean.TRUE;
            }
            if (identificacion.length() == 10) {
                return validaCedula(identificacion);
            }
            switch (identificacion.charAt(2)) {
                case '6':
                    if (validaRucP(identificacion)) {
                        return Boolean.TRUE;
                    } else {
                        return validaCedula(identificacion);
                    }
                case '9':
                    return true;
                default:
                    return validaCedula(identificacion);
            }
        } catch (NumberFormatException ne) {
            return Boolean.FALSE;
        }

    }

    public static Boolean validar(String identificacion, String tipoIdent) {
        if (tipoIdent.trim().equalsIgnoreCase("P")) {
            return Boolean.TRUE;
        }
        return validar(identificacion);
    }

    private static Boolean validaRucP(String ruc) {
        if (ruc.length() != 13) {
            return false;
        }
        int prov = Integer.parseInt(ruc.substring(0, 2));
        boolean val = false;
        if (!(((prov > 0) && (prov <= NUM_PROVINCIAS)) || prov == 30)) {
            return val;
        }
        Integer v1, v2, v3, v4, v5, v6, v7, v8, v9;
        Integer sumatoria;
        Integer modulo;
        Integer digito;
        int[] d = new int[ruc.length()];
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(ruc.charAt(i) + "");
        }
        v1 = d[0] * 3;
        v2 = d[1] * 2;
        v3 = d[2] * 7;
        v4 = d[3] * 6;
        v5 = d[4] * 5;
        v6 = d[5] * 4;
        v7 = d[6] * 3;
        v8 = d[7] * 2;
        v9 = d[8];
        sumatoria = v1 + v2 + v3 + v4 + v5 + v6 + v7 + v8;
        modulo = sumatoria % 11;
        digito = 11 - modulo;
        digito = (digito == 11) ? 0 : digito;
        val = Objects.equals(digito, v9);
        return val;
    }

    private static Boolean validaCedula(String cedula) {
        if (cedula.length() != 10 && cedula.length() != 13) {
            return false;
        }
        int prov = Integer.parseInt(cedula.substring(0, 2));
        if (!(((prov > 0) && (prov <= NUM_PROVINCIAS)) || prov == 30)) {
            return false;
        }
        int[] d = new int[10];
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(cedula.charAt(i) + "");
        }
        int imp = 0;
        int par = 0;
        for (int i = 0; i < d.length; i += 2) {
            d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
            imp += d[i];
        }
        for (int i = 1; i < (d.length - 1); i += 2) {
            par += d[i];
        }
        int suma = imp + par;
        int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1) + "0") - suma;
        d10 = (d10 == 10) ? 0 : d10;
        return d10 == d[9];
    }

}
