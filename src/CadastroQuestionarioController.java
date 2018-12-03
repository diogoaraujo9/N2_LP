
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import alert.AlertHelper;
import connection.ConnectionDB;
import dao.PerguntaDAO;
import dao.RespostaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CadastroQuestionarioController implements Initializable {
	/*
	 * @FXML //Annotation private Label labelStatus;
	 */
	@FXML
	private Pane pnlContent;

	@FXML
    private TextField txtNome;

    @FXML
    private TextField txtDescricao;

    @FXML
    private Button btnCadastrarPaciente;

    @FXML
    private ComboBox<String> cbCategoria;

    @FXML
    private TableView<PerguntaDAO> listPerguntasDisponiveis;

    @FXML
    private TableColumn<String, String> columnPerguntaDisponivel;

    @FXML
    private TableColumn<String, String> columnDataCriacaoDisponivel;

    @FXML
    private TextField txtPergunta;

    @FXML
    private TableView<PerguntaDAO> listPerguntasEscolhidas;

    @FXML
    private TableColumn<String, String> columnPerguntaEscolhida;

    @FXML
    private TableColumn<String, String> columnDataCriacaoEscolhida;

    @FXML
    private Button btnAdicionarPergunta;

    @FXML
    private Button btnRemoverPergunta;

    @FXML
    void adicionarPergunta(ActionEvent event) {
    	PerguntaDAO pergunta = listPerguntasDisponiveis.getSelectionModel().getSelectedItem();
    	
    	if(pergunta != null)
    	{
    		listPerguntasDisponiveis.getItems().remove(pergunta);
        	listPerguntasEscolhidas.getItems().add(pergunta);
    	}
    	
    }

    @FXML
    void cadastrarPaciente(ActionEvent event) {
    	CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;
    	
    	String nomeQuestionario = txtNome.getText();
    	String descricao = txtDescricao.getText();
    	String categoria = cbCategoria.getSelectionModel().getSelectedItem();
    	
    	if (nomeQuestionario == null || nomeQuestionario.isEmpty())
    	{
    		AlertHelper.showErrorMessage("Erro",
					 "Não foi possível cadastrar questionário",
					 "Campo 'Nome' está vazio.\nPor favor, preencher antes de adicionar."); 
    		return;
    	}
    	else if (categoria == null || categoria.isEmpty())
    	{
    		AlertHelper.showErrorMessage("Erro",
					 "Não foi possível cadastrar questionário",
					 "Campo 'Categoria' está vazio.\nPor favor, selecionar antes de adicionar."); 
    		return;
    	}
    	else if (listPerguntasEscolhidas.getItems().isEmpty() || listPerguntasEscolhidas.getItems().size() == 0)
		{
			AlertHelper.showErrorMessage("Erro",
					 "Não foi possível salvar questionário",
					 "Não existem perguntas escolhidas.\nPor favor, escolha ao menos uma antes de salvar.");
			return;
		}
    	
    	try
    	{
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.insereQuestionario(?,?,?)}");
			call.setInt(1, Integer.parseInt(categoria.split(":")[0]));
			call.setString(2, descricao);
			call.setString(3, nomeQuestionario);
			call.execute();
			CallableStatement callAux = connection.prepareCall("{call dbo.buscaUltimoQuestionario}");

				// This returns true if there are any remaining ResultSets to fetch
			if (callAux.execute()) {
				rs = callAux.getResultSet();

				// Move on to the next result set, if any
				while (rs.next()) {
					for(PerguntaDAO pergunta : listPerguntasEscolhidas.getItems())
					{
						int idPergunta = Integer.parseInt(callAux.getResultSet().getString(1));
						System.out.println("FOI");
						CallableStatement callRespostas = connection.prepareCall("{call dbo.insereQuestionarioPergunta(?,?)}");
						callRespostas.setInt(1, idPergunta);
						callRespostas.setInt(2, pergunta.getId());
						
						callRespostas.execute();
					}
				};
			}
    	} catch (Exception ex) {
			System.out.println(ex);
		}
    	
    	
    	
    }

    @FXML
    void removerPergunta(ActionEvent event) {
    	PerguntaDAO pergunta = listPerguntasEscolhidas.getSelectionModel().getSelectedItem();
    	
    	if(pergunta != null)
    	{    		
			listPerguntasEscolhidas.getItems().remove(pergunta);
			listPerguntasDisponiveis.getItems().add(pergunta);
    	}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadCategoriasComboBox();
		loadPerguntas();
	}
	
	public void loadCategoriasComboBox()
	{
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;

		try {
			cbCategoria.getItems().clear();
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.buscaCategorias}");

			// This returns true if there are any remaining ResultSets to fetch
			if (call.execute()) {
				rs = call.getResultSet();

				// Move on to the next result set, if any
				while (rs.next()) {
					String categoria = "";
					categoria = call.getResultSet().getString(1) + ": " +call.getResultSet().getString(2);
					System.out.println(categoria);

					cbCategoria.getItems().add(categoria);
				};
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		ConnectionDB.CloseConnection(cstmt, rs);
	}
	
	public void loadPerguntas()
	{
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;
		
		columnPerguntaDisponivel.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		columnDataCriacaoDisponivel.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
		
		columnPerguntaEscolhida.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		columnDataCriacaoEscolhida.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
		try {
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.buscaPerguntas}");

			// This returns true if there are any remaining ResultSets to fetch
			if (call.execute()) {
				rs = call.getResultSet();

				// Move on to the next result set, if any
				while (rs.next()) {
					

		        	PerguntaDAO perguntaDAO = new PerguntaDAO(call.getResultSet().getString(2), call.getResultSet().getString(3),call.getResultSet().getInt(1));
		        	listPerguntasDisponiveis.getItems().add(perguntaDAO);
				};
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		ConnectionDB.CloseConnection(cstmt, rs);
	}
}













