/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.farmacia;

import com.mycompany.farmacia.clases.Reporte;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 * FXML Controller
 * class
 *
 * @author spooky
 */
public class MenuPrincipalController implements Initializable {

	/**
	 * Initializes
	 * the
	 * controller
	 * class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}	

	@FXML
	private void cliente(ActionEvent event) {
		abrirFXML("primary.fxml", "Formulario Clientes");
	}
	
	public void abrirFXML(String direccion, String titulo) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(direccion));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setTitle(titulo);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException ex) {
			Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}

	@FXML
	private void mostrarCliente(ActionEvent event) {
		Reporte r=new Reporte();
		String ubicacion="/Reporte/Cliente.jasper";
		String titulo="Informe de Cliente";
		r.generarReporte(ubicacion, titulo);
		
	}

	@FXML
	private void reportarCliente(ActionEvent event) {
		// Crear el diálogo
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ingresar Código del Cliente");
        dialog.setHeaderText(null);
        dialog.setContentText("Código:");
        // Mostrar el diálogo y esperar respuesta
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String input = result.get();
            try {
                int numero = Integer.parseInt(input);
               //llamar al reporte
               Reporte r=new Reporte();
                String ubicacion="/Reporte/ClienteReporte.jasper";
                String titulo="Informe de Cliente por Código";
                r.generarReporteParametro(ubicacion, titulo,numero);
            } catch (NumberFormatException e) {
                System.out.println("¡Entrada no válida! No es un número.");
            }
        }
	}
	
}