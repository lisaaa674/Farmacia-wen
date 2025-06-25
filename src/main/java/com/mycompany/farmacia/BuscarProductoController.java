package com.mycompany.farmacia;

import com.mycompany.farmacia.clases.ventaSingleton;
import com.mycompany.farmacia.modelo.Medicamento;
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
import java.net.URL;
import java.util.ResourceBundle;

public class BuscarProductoController implements Initializable {

    @FXML

    private TextField txtBuscar;

    @FXML

    private TableColumn<Medicamento, Integer> columCod;

    @FXML

    private TableColumn<Medicamento, String> columNom;

    @FXML

    private TableColumn<Medicamento, Double> columPrecio;

    @FXML

    private TableView<Medicamento> tablaProducto;

    private Medicamento medicamento = new Medicamento();

    private ObservableList<Medicamento> registros;

    private ObservableList<Medicamento> registrosFiltrados;

// 👉 用來傳值回主畫面
    private PrimaryController controladorFactura;

    public void setControladorFactura(PrimaryController controladorFactura) {

        this.controladorFactura = controladorFactura;

    }

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        mostrarDatos();

    }

    @FXML

    private void buscar(KeyEvent event) {

        registrosFiltrados = FXCollections.observableArrayList();

        String busqueda = txtBuscar.getText().toLowerCase();

        if (busqueda.isEmpty()) {

            tablaProducto.setItems(registros);

        } else {

            for (Medicamento med : registros) {

                if (med.getNombre().toLowerCase().contains(busqueda)) {

                    registrosFiltrados.add(med);

                }

            }

            tablaProducto.setItems(registrosFiltrados);

        }

    }

    @FXML

    private void mostrarFila(MouseEvent event) {

        if (event.getClickCount() == 2) {

            Medicamento seleccionado = tablaProducto.getSelectionModel().getSelectedItem();
            System.out.println(seleccionado.getId());

            if (seleccionado != null) {

                ventaSingleton.getInstance().setCodProd(seleccionado.getId());

                if (controladorFactura != null) {

                    controladorFactura.setProducto(seleccionado);

                }

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                stage.close();

            }

        }

    }

    public void mostrarDatos() {

        medicamento.setBase("farmacia");

        medicamento.setHost("localhost");

        medicamento.setUsuario("root");

        medicamento.setContra("lisayang@021");

        registros = FXCollections.observableArrayList(medicamento.consulta());

        columCod.setCellValueFactory(new PropertyValueFactory<>("id"));

        columNom.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        columPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        tablaProducto.setItems(registros);

    }

}
