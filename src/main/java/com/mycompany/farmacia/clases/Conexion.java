/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farmacia.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author spooky
 */
public class Conexion {
    private String base;
    private String host;
    private String usuario;
    private String contraseña;
    private Connection con;

    public Conexion(String base, String host, String usuario, String contraseña) {
        this.base = base;
        this.host = host;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public Conexion() {
        this.base = "farmacia";
        this.host = "localhost";
        this.usuario = "root";
        this.contraseña = "lisayang@021";
    }

    public Connection getCon() {
        try {
            String url = "jdbc:mysql://" + host + "/" + base;
            con = DriverManager.getConnection(url,this.usuario, this.contraseña);
            System.out.println("Conectado");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No conectado");
        }
        return con;

    }
    
    
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
}
