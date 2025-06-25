package com.mycompany.farmacia;

import com.mycompany.farmacia.clases.ventaSingleton;
import com.mycompany.farmacia.modelo.Cliente;
import com.mycompany.farmacia.modelo.Medicamento;
import com.mycompany.farmacia.modelo.detalleVenta;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FacturaController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(FacturaController.class.getName());

    @FXML private TextField txtFact;
    @FXML private Button btnBuscarCliente;
    @FXML private Button btnbuscarProd;
    @FXML private DatePicker txtFecha;
    @FXML private Button btnNuevo;
    @FXML private ComboBox<String> cmbMetodo;
    @FXML private TextField txtCliente;
    @FXML private TextField txtProd;
    @FXML private TextField txtCant;
    @FXML private Button btnAgregar;
    @FXML private Button btnImprimir;
    @FXML private javafx.scene.control.TableView<detalleVenta> tablaDetalle;
    @FXML private javafx.scene.control.TextField txtTotal;
    @FXML private Button btnGrabar;
    @FXML private javafx.scene.control.TableColumn<detalleVenta, Integer> colCodigo;
    @FXML private javafx.scene.control.TableColumn<detalleVenta, String> colDescripcion;
    @FXML private javafx.scene.control.TableColumn<detalleVenta, Double> colPrecio;
    @FXML private javafx.scene.control.TableColumn<detalleVenta, Integer> colCantidad;
    @FXML private javafx.scene.control.TableColumn<detalleVenta, Double> colSubtotal;

    int codCliente;
    int codProd = 0;
    double precio = 0.0;
    double suma = 0.0;

    Cliente c = new Cliente();
    ObservableList<Cliente> registros;
     detalleVenta detalle = new detalleVenta();

    Medicamento m = new Medicamento();
    ObservableList<Medicamento> registrosMedicamentos;

    ObservableList<detalleVenta> registrosDetalle = FXCollections.observableArrayList();

    ventaSingleton v = ventaSingleton.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registros = FXCollections.observableArrayList(c.consulta());
        registrosMedicamentos = FXCollections.observableArrayList(m.consulta());

        colCodigo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("codProd"));
        colDescripcion.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("cantidad"));
        colSubtotal.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("subTotal"));
        
    }


    public void abrirFxmlModal(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(titulo);
            stage.setResizable(false);
            stage.setMinWidth(300);
            stage.setMinHeight(200);
            stage.showAndWait();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void nuevo(ActionEvent event) {
        txtFact.setDisable(false);
        txtFact.requestFocus();
        btnBuscarCliente.setDisable(false);
        txtFecha.setDisable(false);
        btnNuevo.setDisable(true);

        cmbMetodo.setPromptText("Seleccione Método");
        cmbMetodo.getItems().clear();
        cmbMetodo.getItems().addAll("Efectivo", "Tarjeta de Débito", "Tarjeta de Crédito");

        txtFecha.setValue(LocalDate.now());
    }

    @FXML
    private void buscarCliente(ActionEvent event) {
        abrirFxmlModal("buscarCliente.fxml", "Buscar Cliente");
        codCliente = ventaSingleton.getInstance().getCodCliente();
        for (Cliente registro : registros) {
            if (registro.getId() == codCliente) {
                txtCliente.setText(registro.getNombre() + " " + registro.getApellido());
                break;
            }
        }
        btnbuscarProd.setDisable(false);
        
    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtFact.clear();
        txtCliente.clear();
        txtProd.clear();
        txtCant.clear();
        txtFecha.setValue(null);
        cmbMetodo.getSelectionModel().clearSelection();
        registrosDetalle.clear();

        txtFact.setDisable(true);
        btnBuscarCliente.setDisable(true);
        txtFecha.setDisable(true);
        btnNuevo.setDisable(false);
        btnbuscarProd.setDisable(true);
        txtCant.setDisable(true);
        btnAgregar.setDisable(true);
        btnImprimir.setDisable(true);
    }

    @FXML
    private void buscarProducto(ActionEvent event) {
        abrirFxmlModal("buscarProducto.fxml", "Buscar Producto");
        codProd = ventaSingleton.getInstance().getCodProd();
        System.out.println(codProd);
        for (Medicamento object : registrosMedicamentos) {
            if (object.getId() == codProd) {
                txtProd.setText(object.getNombre());
                precio = object.getPrecio();
                break;
            }
        }
        txtCant.setDisable(false);
        btnAgregar.setDisable(false);
    }

    @FXML
    private void guardarVenta(ActionEvent event) {
        if (txtFact.getText().isEmpty()) {
            mostrarError("Debe ingresar el número de factura.");
            return;
        }
        if (cmbMetodo.getSelectionModel().isEmpty()) {
            mostrarError("Debe seleccionar un método de pago.");
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("El sistema comunica:");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Desea grabar la venta?");
        Optional<ButtonType> opcion = alerta.showAndWait();

        if (opcion.get() == ButtonType.OK) {
            v.setNro(Integer.parseInt(txtFact.getText()));
            v.setFecha(txtFecha.getValue().toString());
            v.setMetodo(cmbMetodo.getSelectionModel().getSelectedItem());
            v.setCodCliente(codCliente);
            v.setCodEmpleado(1); // fijo, a personalizar según sistema

            if (v.insertar()) {
                for (detalleVenta object : registrosDetalle) {
                   
                    detalle.setCod(Integer.parseInt(txtFact.getText()));
                    detalle.setCodProd(object.getCodProd());
                    detalle.setCantidad(object.getCantidad());
                    detalle.insertar();
                }

                Alert alertaIn = new Alert(Alert.AlertType.INFORMATION);
                alertaIn.setTitle("El sistema comunica:");
                alertaIn.setHeaderText(null);
                alertaIn.setContentText("Insertado correctamente en la base de datos");
                alertaIn.show();

                btnImprimir.setDisable(false);
            } else {
                mostrarError("Error. Registro no insertado.");
            }

            cancelar(event);
        }
    }
    @FXML
    private void AgregarFila(ActionEvent event) {
         if(!txtCant.getText().isEmpty()){ // sino esta vacio el campo de texto cantidad
            double subtotal=precio*Integer.parseInt(txtCant.getText());// se calcula el subtotal(precio por cantiadad)
            suma=suma+subtotal;// se calcula la suma(variable global)
            // se crea un nuevo objeto de tipo detalle de venta, esta declaradado de forma global
            detalleVenta dv=new detalleVenta(codProd, txtProd.getText(), precio,Integer.parseInt(txtCant.getText()),subtotal);
            //se agrega el objeto a un observable list(previamente declarado e inicializado)
            registrosDetalle.add(dv);
            //se asocia con la tabla la lista, la tabla debe estar formateado correctamente
            //(indicar que modelo lleva e inicializar las columnas)    
            tablaDetalle.setItems(registrosDetalle);
            //se muestra el total formateado a dos decimales
            txtTotal.setText(String.format("%.2f", suma));
            //se limpia los campos y se desactivan
            txtProd.clear();
            txtCant.clear();
            txtCant.setDisable(true);
            btnGrabar.setDisable(false);
             
        }else{
           //mensaje correspondiente(ventana emergente)
            System.out.println("no debe quedar vacio");
        }
    }
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    public int obtenerIdVentaPorNro(int nroFactura) {
    int idVenta = -1;
    String sql = "SELECT ID_Venta FROM venta WHERE nro = ?";

    try (Connection cn = this.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setInt(1, nroFactura);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            idVenta = rs.getInt("ID_Venta");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener ID_Venta: " + e.getMessage());
    }

    return idVenta;
}

    private Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}