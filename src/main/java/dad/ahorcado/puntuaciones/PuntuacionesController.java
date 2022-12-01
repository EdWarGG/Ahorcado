package dad.ahorcado.puntuaciones;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class PuntuacionesController implements Initializable {
	 //model
	private ObservableList<Puntuacion> lista = FXCollections.observableArrayList(new ArrayList<Puntuacion>());
	private ListProperty<Puntuacion> puntuacionesList = new SimpleListProperty<>(lista);	
	
	//view
    @FXML
    private TableView<Puntuacion> tablaPuntuacion;
    @FXML
    private BorderPane puntuacionesView;

    
    public PuntuacionesController() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PuntuacionesView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tablaPuntuacion.itemsProperty().bind(puntuacionesList);
	}
	
	
	public BorderPane getView() {
		return puntuacionesView;
	}
	
	
	public final ListProperty<Puntuacion> listaPuntuacionProperty() {
		return this.puntuacionesList;
	}
	public final ObservableList<Puntuacion> getListaPuntuacion() {
		return this.listaPuntuacionProperty().get();
	}
	public final void setListaPuntuacion(final ObservableList<Puntuacion> puntuacionesList) {
		this.listaPuntuacionProperty().set(puntuacionesList);
	}

}

