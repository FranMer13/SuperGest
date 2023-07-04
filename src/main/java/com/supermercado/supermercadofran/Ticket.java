package com.supermercado.supermercadofran;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private final List<Producto> productos;

    public Ticket() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public List<Producto> getProductos() {
        return productos;
    }


}
