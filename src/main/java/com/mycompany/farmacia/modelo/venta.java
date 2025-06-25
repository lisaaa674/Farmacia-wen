/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmacia.modelo;

import com.mycompany.farmacia.clases.Conexion;
import com.mycompany.farmacia.clases.Sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Federico
 */
public class venta extends Conexion implements Sentencias{
    private int nro;
    private String fecha;
    private String metodo;
    private int codCliente;
    private int codEmpleado;

    public venta(int nro, String fecha, String metodo, int codCliente, int codEmpleado) {
        this.nro = nro;
        this.fecha = fecha;
        this.metodo = metodo;
        this.codCliente = codCliente;
        this.codEmpleado = codEmpleado;
    }

    public venta() {
    }
    
    public int getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(int codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

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

    @Override
    public boolean insertar() {
        String sql="insert into venta values(?,?,?,?,?)";
        try( 
            Connection con=getCon();
            PreparedStatement stm=con.prepareStatement(sql)){
           stm.setInt(1,this.nro);
            stm.setString(2,this.fecha);
            stm.setString(3,this.metodo);
            stm.setInt(4,this.codCliente);
            stm.setInt(5,this.codEmpleado);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalleVenta.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    
    }

    @Override
    public ArrayList consulta() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean modificar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}