
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class MainController implements Initializable
{
	/*@FXML //Annotation
	private Label labelStatus;
	*/
	@FXML 
	private Pane paneControl;
	
	@FXML
	protected void carregarTelaCategoria() 
	{
		try 
		{
			Parent newLoadedPane = FXMLLoader.load(getClass().getResource("views/TelaCategoria.fxml"));
		    paneControl.getChildren().add(newLoadedPane);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void carregarTelaCadastrarPaciente() 
	{
		try 
		{
			Parent newLoadedPane = FXMLLoader.load(getClass().getResource("views/TelaCadastroPaciente.fxml"));
		    paneControl.getChildren().add(newLoadedPane);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

    @FXML
    void carregarTelaCadastrarPerguntas() {
    	try 
		{
			Parent newLoadedPane = FXMLLoader.load(getClass().getResource("views/TelaCadastroPerguntas.fxml"));
		    paneControl.getChildren().add(newLoadedPane);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void carregarTelaConsultarPerguntas() {
    	try 
		{
			Parent newLoadedPane = FXMLLoader.load(getClass().getResource("views/TelaConsultaPerguntas.fxml"));
		    paneControl.getChildren().add(newLoadedPane);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }

    @FXML
    void carregarTelaCadastrarQuestionario() {
    	try 
		{
			Parent newLoadedPane = FXMLLoader.load(getClass().getResource("views/TelaCadastroQuestionario.fxml"));
		    paneControl.getChildren().add(newLoadedPane);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void carregarTelaConsultarQuestionario() {
    	try 
		{
			Parent newLoadedPane = FXMLLoader.load(getClass().getResource("views/TelaConsultaQuestionarios.fxml"));
		    paneControl.getChildren().add(newLoadedPane);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }

    @FXML
    void carregarTelaConsultarPaciente() {
    	try 
		{
			Parent newLoadedPane = FXMLLoader.load(getClass().getResource("views/TelaConsultaPaciente.fxml"));
		    paneControl.getChildren().add(newLoadedPane);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }

    @FXML
    void carregarTelaGerarGraficos() {
    	try 
		{
			Parent newLoadedPane = FXMLLoader.load(getClass().getResource("views/TelaGerarGraficos.fxml"));
		    paneControl.getChildren().add(newLoadedPane);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }

    @FXML
    void carregarTelaMenuPrincipal() {
    	
    }

    @FXML
    void carregarTelaRealizarQuestionario() {
    	try 
		{
			Parent newLoadedPane = FXMLLoader.load(getClass().getResource("views/TelaRealizarQuestionario.fxml"));
		    paneControl.getChildren().add(newLoadedPane);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
    }

    @FXML
    void carregarTelaSobre() {
    	
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
}
