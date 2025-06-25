package com.mycompany.farmacia;

import com.mycompany.farmacia.modelo.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import com.mycompany.farmacia.modelo.Cliente;
import com.mycompany.farmacia.modelo.Medicamento;
import com.mycompany.farmacia.modelo.detalleVenta;

public class PrimaryController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<Cliente> tablaCliente;
    @FXML
    private TableColumn<Cliente, Integer> columId;
    @FXML
    private TableColumn<Cliente, String> columNombre;
    @FXML
    private TableColumn<Cliente, String> columApellido;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<detalleVenta> tablaVenta;

    Cliente c = new Cliente();
    ObservableList<Cliente> registros;
    ObservableList<Cliente> registrosFiltrados;
    boolean modificar = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        mostrarDatos();
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txtId.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnModificar.setDisable(true);
        btnNuevo.setDisable(true);
        txtId.requestFocus();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtId.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtId.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnNuevo.setDisable(false);
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (txtId.getText().isEmpty() || txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Validación");
            alerta.setHeaderText(null);
            alerta.setContentText("Todos los campos son obligatorios.");
            alerta.show();
            return;
        }
        int cod = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        c.setId(cod);
        c.setNombre(nombre);
        c.setApellido(apellido);
        if (modificar) {//quiere guardar lo editado <3
            if (c.modificar()) {//si pudo modificar
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("El sistema comunica: ");
                alerta.setHeaderText("");
                alerta.setContentText("Datos modificados correctamente");
                alerta.show();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("El sistema comunica: ");
                alerta.setHeaderText("");
                alerta.setContentText("Datos no modificados");
                alerta.show();
            }
            modificar = false; //activa la bandera 
        } else {
            if (c.insertar()) {//insertar  
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("El sistema comunica: ");
                alerta.setHeaderText("");
                alerta.setContentText("Datos insertados correctamente");
                alerta.show();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("El sistema comunica: ");
                alerta.setHeaderText("");
                alerta.setContentText("Datos no insertados");
                alerta.show();
            }
        }
        mostrarDatos();
        cancelar(event); //llama al boton cancelar uwu

    }

    private void mostrarDatos() {
        registros = FXCollections.observableArrayList(c.consulta());
        columId.setCellValueFactory(new PropertyValueFactory<>("id")); //la columna id de la tabla del modelado corresponde al atributo id
        columNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tablaCliente.setItems(registros);
    }

    @FXML
    private void buscar(KeyEvent event) {
        registrosFiltrados = FXCollections.observableArrayList();
        String busqueda = txtBuscar.getText();
        if (busqueda.isEmpty()) {
            tablaCliente.setItems(registros); //todos los datos
        } else {
            registrosFiltrados.clear();
            for (Cliente registro : registros) {
                String nombre = registro.getNombre();
                if (nombre != null && nombre.toLowerCase().contains(busqueda.toLowerCase())) {
                    registrosFiltrados.add(registro);
                }//fin if
            } //fin for
            tablaCliente.setItems(registrosFiltrados);
        } //fin else
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        Cliente clie = tablaCliente.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf(clie.getId()));
        txtNombre.setText(clie.getNombre());
        txtApellido.setText(clie.getApellido());
        btnModificar.setDisable(false); //habilitar
        btnEliminar.setDisable(false); //habilitar
        btnCancelar.setDisable(false); //habilitar
        btnNuevo.setDisable(true); //deshabilitar

    }

    @FXML
    private void modificar(ActionEvent event) {
        txtNombre.setDisable(false); //habilitar
        txtApellido.setDisable(false); //habilitar
        btnEliminar.setDisable(true);
        btnNuevo.setDisable(true);
        btnModificar.setDisable(true);
        btnGuardar.setDisable(false);
        modificar = true;
    }

    @FXML
    private void eliminar(ActionEvent event) {
        int cod = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        c.setId(cod);
        c.setNombre(nombre);
        c.setApellido(apellido);
        if (c.eliminar()) {	//insertar
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("El sistema comunica: ");
            alerta.setHeaderText("");
            alerta.setContentText("Datos eliminados correctamente");
            alerta.show();
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("El sistema comunica: ");
            alerta.setHeaderText("");
            alerta.setContentText("Datos no eliminados");
            alerta.show();
        }
        mostrarDatos();
        cancelar(event);
    }

    void setCliente(Cliente clienteSeleccionado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setProducto(Medicamento seleccionado) {
        detalleVenta detalle = new detalleVenta();
        detalle.setCodigo(seleccionado.getId());
        detalle.setDescripcion(seleccionado.getNombre());
        detalle.setPrecio(seleccionado.getPrecio());
        detalle.setCantidad(1); // 預設數量是1
        detalle.setSubTotal(seleccionado.getPrecio());

        tablaVenta.getItems().add(detalle); // 加到表格中
    }

}
