
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import alert.AlertHelper;
import connection.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ConsultaPerguntaController {
	/*
	 * @FXML //Annotation private Label labelStatus;
	 */
	@FXML
	private Pane pnlContent;

    @FXML
    private TextField txtPergunta;

    @FXML
    private Button btnEditarPergunta;

    @FXML
    private Button btnDeletarResposta;

    @FXML
    private Button btnAtualizarPergunta;

    @FXML
    private TextField txtPerguntaEditada;

    @FXML
    private TableView<?> listPerguntas;

    @FXML
    private TableColumn<?, ?> columnPergunta;

    @FXML
    private TableColumn<?, ?> columnCategoria;

    @FXML
    void atualizarPergunta(ActionEvent event) {

    }

    @FXML
    void deletarPergunta(ActionEvent event) {

    }

    @FXML
    void editarPergunta(ActionEvent event) {
    	
    }
}













