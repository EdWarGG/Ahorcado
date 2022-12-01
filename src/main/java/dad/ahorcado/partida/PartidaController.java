package dad.ahorcado.partida;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.ahorcado.RootController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;

public class PartidaController implements Initializable {
	
	//controladores
	RootController rootController = new RootController();
	
	//model
	private IntegerProperty puntos = new SimpleIntegerProperty();
	private IntegerProperty vidas = new SimpleIntegerProperty();
	private StringProperty palAdivinar = new SimpleStringProperty(); 
	private StringProperty letrasUsadas = new SimpleStringProperty("");
	private StringProperty palEscrita = new SimpleStringProperty();
	private ObjectProperty<Image> imagen= new SimpleObjectProperty<>();
	
    private final int INTENTOS_TOTALES = 9;
    
    private int palabraActual = 0;
    
    ArrayList<String> listaPalabras = rootController.getPalabrasList();

	
	//view
    @FXML
    private BorderPane partidaView;
    @FXML
    private ImageView ahorcadoImage;
    @FXML
    private TextField escribirText;
    @FXML
    private Label letrasFallidas, palabraAResolver, puntosLabel, vidasLabel;
    @FXML
    private Button letraButton, resolverButton;
    
    
    public PartidaController() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartidaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//bindings
		ahorcadoImage.imageProperty().bind(imagen);
		puntosLabel.textProperty().bindBidirectional(puntos, new NumberStringConverter());
		vidasLabel.textProperty().bindBidirectional(vidas, new NumberStringConverter());
		letrasFallidas.textProperty().bind(letrasUsadas);
		palabraAResolver.textProperty().bind(palAdivinar);
		palEscrita.bind(escribirText.textProperty());
		//listener
		vidas.addListener((v, ov, nv) -> onCambioImagen());
		
		juego();
	}
	
	
	public BorderPane getView() {
		return partidaView;
	}
	
	public void onCambioImagen() {
		if(vidas.get() > 0) {
			imagen.set(new Image(getClass().getResource(String.format("/images/%d.png",10-vidas.get())).toString()));
		}
	}
	
	
	public void juego() {
	    vidas.set(INTENTOS_TOTALES);
	    puntos.set(0);
	    palabraActual = (int) (Math.random() * listaPalabras.size());
	    cambioPalabra();
	}
	 
	public void cambioPalabra() {
	    final String palabraOrigen = listaPalabras.get(palabraActual);
		StringBuilder ocultarPalabra = new StringBuilder(palabraOrigen.replaceAll("[a-z,A-Z,0-9]", "-"));
		palAdivinar.set(ocultarPalabra.toString());
	}

	
    @FXML
    void onLetraAction(ActionEvent event) {
    	Character letra;
    	if(palEscrita.get().length() == 1) {
    		letra = palEscrita.get().charAt(0);
    	    final String palabraOrigen = listaPalabras.get(palabraActual);
    	    
    		if( letrasUsadas.get().indexOf(letra) != -1 ) {
    			return;
    		}  
    		if( palAdivinar.get().indexOf(letra) != -1 ) {		
    			int n, j, i, m;
    			n = j = m = 0;   			
    			while( (i = palAdivinar.get().indexOf(letra,j)) != -1 ) {
    				j = i+1;
    				n++;
    			}    			
    			j = 0;
    			while( (i = palAdivinar.get().indexOf(letra,j)) != -1 ) {
    				j = i+1;
    				m++;
    			}  			
    			if( m == n ) {
    				return;
    			} 			
    		}
    		
    		if( palabraOrigen.indexOf(letra) != -1) {  			
    			puntos.set(puntos.get()+1);
    			StringBuilder palabra = new StringBuilder(palAdivinar.get());
    			int i, j;
    			j = 0;
    			while( (i = palabraOrigen.indexOf(letra,j)) != -1 ) {
    				palabra.setCharAt(i, letra);
    				j = i+1;
    			}   			
    			palAdivinar.set(palabra.toString());
    			if( palabra.toString().compareTo(palabraOrigen) == 0 ) {
    				palabraAdivinada();
    			}   			
    		} else {   			
    			vidas.set(vidas.get()-1);    			
    			if( vidas.get() <= 0 ) { 
    				finDePartida();
    			} else {
    				letrasUsadas.set(letrasUsadas.get()+letra);
    			}
    		}

    		
    	}else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Solo puedes enviar una letra al pulsar en \"Letra\", "
									+ "y hay más de una letra escrita.");
			errorAlert.show();
    	}
    }

    
    @FXML
    void onResolverAction(ActionEvent event) {
    	if( palEscrita.get().equalsIgnoreCase(listaPalabras.get(palabraActual))) {
    		palabraAdivinada();
			
		} else {
			
			vidas.set(vidas.get()-1);
			if( vidas.get() <= 0 ) { 
				finDePartida();
			} 
		}
    }
    
    public void palabraAdivinada() {
	    final String palabraOrigen = listaPalabras.get(palabraActual);
    	Alert adivinadoAlert = new Alert(AlertType.ERROR);
    	adivinadoAlert.setTitle("¡Felicidades!");
    	adivinadoAlert.setHeaderText("Has conseguido adivinar la palabra oculta, que era: " + palabraOrigen + ".");
    	adivinadoAlert.show();
    	
		listaPalabras.remove(listaPalabras.get(palabraActual));
		if( listaPalabras.size() == 0 ) {
			finDePartida();
		} else {
		palabraActual = (int) (Math.random() * listaPalabras.size());
		letrasUsadas.set("");
		cambioPalabra();
		}
    }
    
    public void finDePartida() {
    	String nombreJugador;
    	TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Fin de la partida");
		dialog.setHeaderText("Escribe tu nombre para guardar tu puntuación:");
		
		Optional<String> nombreIntroducido = dialog.showAndWait();
		nombreJugador = nombreIntroducido.get();
    }

}
