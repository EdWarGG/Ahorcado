package dad.ahorcado.puntuacion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class PuntuacionController implements Initializable {

    @FXML
    private ListView<?> listaPuntacion;

    @FXML
    private BorderPane puntuacionesView;
    
    public PuntuacionController() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PuntuacionView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
			
	}

}

