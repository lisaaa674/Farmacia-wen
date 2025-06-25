package com.mycompany.farmacia.clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ventaSingleton extends Conexion {

    // Atributos
    private int nro;
    private String fecha;
    private String metodo;
    private int codCliente;
    private int codEmpleado;
    private int codProd;

    // Constructor privado
    private ventaSingleton() {}

    // Singleton interno
    private static class ventaSingletonHolder {
        private static final ventaSingleton INSTANCE = new ventaSingleton();
    }

    public static ventaSingleton getInstance() {
        return ventaSingletonHolder.INSTANCE;
    }

    // Getters y Setters

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public int getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(int codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public boolean insertar() {
        String sql = "INSERT INTO venta (nro, fecha, metodo, codCliente, codEmpleado) VALUES (?, ?, ?, ?, ?)";
        try (
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql)
        ) {
            stm.setInt(1, this.nro);
            stm.setString(2, this.fecha);
            stm.setString(3, this.metodo);
            stm.setInt(4, this.codCliente);
            stm.setInt(5, this.codEmpleado);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ventaSingleton.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public int getCodProd() {
       // int codProd = 0;
        return codProd;
    }

    public void setCodProd(int codProd) {
        this.codProd = codProd;
    }
    
    private int idVenta;
    public int getIdVenta(){
        return idVenta;
    }
    public void setIdVenta(int idVenta){
        this.idVenta=idVenta;
    }
}