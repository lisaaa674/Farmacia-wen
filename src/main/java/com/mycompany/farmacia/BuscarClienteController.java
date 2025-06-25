package com.mycompany.farmacia;

import com.mycompany.farmacia.clases.ventaSingleton;
import com.mycompany.farmacia.modelo.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BuscarClienteController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<Cliente> tablaCliente;
    @FXML
    private TableColumn<Cliente, Integer> columCod;
    @FXML
    private TableColumn<Cliente, String> columNom;
    @FXML
    private TableColumn<Cliente, String> columApe;

    private ObservableList<Cliente> registros;
    private ObservableList<Cliente> registrosFiltrados;

    private PrimaryController controladorFactura;

    // 供主控制器設置用
    public void setControladorFactura(PrimaryController controladorFactura) {
        this.controladorFactura = controladorFactura;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();
    }

    private void mostrarDatos() {
        Cliente c = new Cliente();
        c.setBase("farmacia");
        c.setHost("localhost");
        c.setUsuario("root");
        c.setContra("lisayang@021");

        registros = FXCollections.observableArrayList(c.consulta());

        columCod.setCellValueFactory(new PropertyValueFactory<>("id"));
        columNom.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columApe.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        tablaCliente.setItems(registros);
    }

    @FXML
    private void buscar(KeyEvent event) {
        registrosFiltrados = FXCollections.observableArrayList();
        String busqueda = txtBuscar.getText().toLowerCase();

        if (busqueda.isEmpty()) {
            tablaCliente.setItems(registros);
        } else {
            for (Cliente registro : registros) {
                if (registro.getNombre().toLowerCase().contains(busqueda)) {
                    registrosFiltrados.add(registro);
                }
            }
            tablaCliente.setItems(registrosFiltrados);
        }
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Cliente clienteSeleccionado = tablaCliente.getSelectionModel().getSelectedItem();
            if (clienteSeleccionado != null) {
                System.out.println(clienteSeleccionado.getId());
                ventaSingleton.getInstance().setCodCliente(clienteSeleccionado.getId());

                if (controladorFactura != null) {
                    controladorFactura.setCliente(clienteSeleccionado);
                }

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
    }
}