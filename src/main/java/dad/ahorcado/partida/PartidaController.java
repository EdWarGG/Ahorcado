package dad.ahorcado.partida;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PartidaController implements Initializable {

	
	//model
	
	//view
    @FXML
    private BorderPane PartidaView;
    @FXML
    private ImageView ahorcadoImage;
    @FXML
    private TextField escribirText;
    @FXML
    private Button letraButton;
    @FXML
    private Label letrasFallidas;
    @FXML
    private Label palabraAResolver;
    @FXML
    private Label puntosLabel;
    @FXML
    private Button resolverButton;
    
    
    public PartidaController() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartidaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

    @FXML
    void onLetraAction(ActionEvent event) {

    }

    @FXML
    void onResolverAction(ActionEvent event) {

    }

}
