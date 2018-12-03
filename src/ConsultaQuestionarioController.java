
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

public class ConsultaQuestionarioController {
	/*
	 * @FXML //Annotation private Label labelStatus;
	 */
	@FXML
	private Pane pnlContent;

	@FXML
    private TextField txtQuestionario;

    @FXML
    private Button btnEditarPergunta;

    @FXML
    private Button btnDeletarResposta;

    @FXML
    private Button btnAtualizarQuestionario;

    @FXML
    private TextField txtQuestionarioEditada;

    @FXML
    private TableView<?> listQuestionarios;

    @FXML
    private TableColumn<?, ?> columnQuestionario;

    @FXML
    private TableColumn<?, ?> columnCategoria;

    @FXML
    private TableColumn<?, ?> columnDataCriacao;

    @FXML
    private ComboBox<?> cbCategoria;

    @FXML
    void atualizarQuestionario(ActionEvent event) {

    }

    @FXML
    void deletarQuestionario(ActionEvent event) {

    }

    @FXML
    void editarQuestionario(ActionEvent event) {

    }
    
}













