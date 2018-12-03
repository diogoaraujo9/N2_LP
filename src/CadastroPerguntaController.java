
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import alert.AlertHelper;
import connection.ConnectionDB;
import dao.RespostaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CadastroPerguntaController {
	/*
	 * @FXML //Annotation private Label labelStatus;
	 */
	@FXML
	private Pane pnlContent;

	@FXML
    private TextField txtPergunta;

    @FXML
    private Button btnCadastrarPergunta;

    @FXML
    private Button btnDeletarResposta;

    @FXML
    private Button btnAtualizarResposta;

    @FXML
    private TableView<RespostaDAO> listPerguntas;

    @FXML
    private TableColumn<String, String> columnResposta;

    @FXML
    private TableColumn<String, String> columnPontuacao;

    @FXML
    private TextField txtResposta;

    @FXML
    private TextField txtPontuacao;

    @FXML
    private Button btnAdicionarResposta;

    @FXML
    private ComboBox<String> cbCategoria;

    @FXML
    void adicionarResposta(ActionEvent event) {
    	
    	String resposta = txtResposta.getText();
    	String pontuacao = txtPontuacao.getText();
    	if (resposta == null || resposta.isEmpty())
    	{
    		AlertHelper.showErrorMessage("Erro",
					 "Não foi possível adicionar resposta",
					 "Campo 'Resposta' está vazio.\nPor favor, preencher antes de adicionar.");  
    	}
    	else if (pontuacao == null || pontuacao.isEmpty())
    	{
    		AlertHelper.showErrorMessage("Erro",
					 "Não foi possível adicionar resposta",
					 "Campo 'Pontuação' está vazio.\nPor favor, preencher antes de adicionar.");   
    	}
    	else
    	{
    		columnResposta.setCellValueFactory(new PropertyValueFactory<>("resposta"));
        	columnPontuacao.setCellValueFactory(new PropertyValueFactory<>("pontuacao"));

        	RespostaDAO respostaDAO = new RespostaDAO(txtResposta.getText(), Integer.parseInt(txtPontuacao.getText()));
        	listPerguntas.getItems().add(respostaDAO);
    	}
    }

    @FXML
    void atualizarResposta(ActionEvent event) {

    }

    @FXML
    void cadastrarPergunta(ActionEvent event) {
    	CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;

		try {
			String pergunta = txtPergunta.getText();
			
			if(pergunta == null || pergunta.isEmpty())
			{
				AlertHelper.showErrorMessage("Erro",
						 "Não foi possível salvar pergunta",
						 "Campo 'Pergunta' está vazio.\nPor favor, preencher antes de salvar.");
				return;
			}
			
			if (listPerguntas.getItems().isEmpty() || listPerguntas.getItems().size() == 0)
			{
				AlertHelper.showErrorMessage("Erro",
						 "Não foi possível salvar pergunta",
						 "Não existem respostas cadastradas.\nPor favor, preencher antes de salvar.");
				return;
			}
			
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.inserePergunta(?)}");
			call.setString(1, pergunta);
			call.execute();
				CallableStatement callAux = connection.prepareCall("{call dbo.buscaUltimaPergunta}");

				// This returns true if there are any remaining ResultSets to fetch
				if (callAux.execute()) {
					rs = callAux.getResultSet();

					// Move on to the next result set, if any
					while (rs.next()) {
						for(RespostaDAO resposta : listPerguntas.getItems())
						{
							int idPergunta = Integer.parseInt(callAux.getResultSet().getString(1));
							System.out.println("FOI");
							CallableStatement callRespostas = connection.prepareCall("{call dbo.insereResposta(?,?,?)}");
							callRespostas.setInt(1, idPergunta);
							callRespostas.setString(2, resposta.getResposta());
							callRespostas.setInt(3, resposta.getPontuacao());
							
							callRespostas.execute();
						}
					};
				}
			
			
			
			
		} catch (Exception ex) {
			System.out.println(ex);
		}

		ConnectionDB.CloseConnection(cstmt, rs);
    }

    @FXML
    void deletarResposta(ActionEvent event) {

    }
}


