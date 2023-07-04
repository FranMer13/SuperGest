package com.supermercado.supermercadofran;

public class Producto {
    private final String nombre;
    private final int unidadesVendidas;
    private final float precio;

    public Producto(String nombre, int unidadesVendidas, float precio) {
        this.nombre = nombre;
        this.unidadesVendidas = unidadesVendidas;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public float getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return nombre + " (" + unidadesVendidas + " unidades x €" + precio + ")";
    }
}


