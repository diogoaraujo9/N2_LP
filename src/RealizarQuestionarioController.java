
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.layout.Pane;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class RealizarQuestionarioController implements Initializable {
	/*
	 * @FXML //Annotation private Label labelStatus;
	 */
	@FXML
	private Pane pnlContent;

    @FXML
    private ComboBox<String> cbCategoria;

    @FXML
    private ComboBox<String> cbPaciente;

    @FXML
    private ComboBox<String> cbQuestionario;

    @FXML
    private Button btnIniciarQuestionario;

    @FXML
    private Label txtNumeroPergunta;

    @FXML
    private Label txtPergunta;

    @FXML
    private ListView<String> listRespostas;

    @FXML
    private Button btnProximaPergunta;

    @FXML
    private Button btnCancelarQuestionario;
    
    public List<PerguntaDAO> listaPerguntas = new ArrayList<PerguntaDAO>();
    public List<RespostaDAO> listaRespostas = new ArrayList<RespostaDAO>();
    public int idPaciente = 0;
    public String nomeQuestionario = "";
    public int somaTotal = 0;
    public int perguntaAtual = 0;    

    @FXML
    void cancelarQuestionario() {

    }
    
    @FXML
    void iniciarQuestionario() {
    	CallableStatement cstmt = null;
		ResultSet rs = null;
		ResultSet rsPergunta = null;
		ResultSet rsResposta = null;
		Connection connection = null;

		try {
			perguntaAtual += 1;
	    	txtNumeroPergunta.setText("Pergunta " + String.valueOf(perguntaAtual));
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.buscaQuestionarioPerguntas(?)}");
			String questionario = cbQuestionario.getSelectionModel().getSelectedItem();
			idPaciente = Integer.parseInt(cbPaciente.getSelectionModel().getSelectedItem().split(":")[0]);
			nomeQuestionario = questionario.split(":")[0];
			call.setInt(1, Integer.parseInt(nomeQuestionario));
			

			// This returns true if there are any remaining ResultSets to fetch
			if (call.execute()) {
				rs = call.getResultSet();

				// Move on to the next result set, if any
				while (rs.next()) {
					String perguntaId = call.getResultSet().getString(3);
					CallableStatement callPergunta = connection.prepareCall("{call dbo.buscaPerguntaPorId(?)}");
					callPergunta.setInt(1, Integer.parseInt(perguntaId));
					
					if (callPergunta.execute()) {
						rsPergunta = callPergunta.getResultSet();
						
						while(rsPergunta.next())
						{
							int idPergunta = callPergunta.getResultSet().getInt(1);
							String descricao = callPergunta.getResultSet().getString(2);
							String dateCriacao = callPergunta.getResultSet().getString(3); 
							PerguntaDAO pergunta = new PerguntaDAO(descricao, dateCriacao, idPergunta);
							
							listaPerguntas.add(pergunta);
						}
					}					
				};
			}
			PerguntaDAO primeiraPergunta = listaPerguntas.remove(0);
			txtPergunta.setText(primeiraPergunta.getDescricao());
			
			CallableStatement callRespostas = connection.prepareCall("{call dbo.buscaRespostaPorId(?)}");
			callRespostas.setInt(1, primeiraPergunta.getId());
			
			if (callRespostas.execute()) {
				rsResposta = callRespostas.getResultSet();
				
				while (rsResposta.next()) {
					String respostaDescricao = callRespostas.getResultSet().getString(3);
					int respostaPontuacao = callRespostas.getResultSet().getInt(4);
					int idResposta = callRespostas.getResultSet().getInt(1);
					
					RespostaDAO resposta = new RespostaDAO(respostaDescricao, respostaPontuacao);
					resposta.idPergunta = primeiraPergunta.getId();
					resposta.idResposta = idResposta;
					
					listaRespostas.add(resposta);
				}
			}
			
			for (int i = 0; i < listaRespostas.size(); i++) {
				listRespostas.getItems().add(String.valueOf(listaRespostas.get(i).idResposta) + " : " + listaRespostas.get(i).getResposta());
			}		
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		ConnectionDB.CloseConnection(cstmt, rs);
    }

    @FXML
    void proximaPergunta(ActionEvent event) {
    	String respostaSelecionada = listRespostas.getSelectionModel().getSelectedItem();
    	perguntaAtual += 1;
    	txtNumeroPergunta.setText("Pergunta " + String.valueOf(perguntaAtual));
    	if (respostaSelecionada == null || respostaSelecionada.isEmpty())
    	{
    		return;
    	}
    	
    	int idRespostaSelecionada = Integer.parseInt(respostaSelecionada.split(" ")[0]);
    	
    	for (int i = 0; i < listaRespostas.size(); i++) {
			if(listaRespostas.get(i).idResposta == idRespostaSelecionada)
			{
				somaTotal += listaRespostas.get(i).getPontuacao();
			}
		}
    	
    	listRespostas.getItems().clear();
    	listaRespostas = new ArrayList<RespostaDAO>();
    	if(listaPerguntas != null && listaPerguntas.size() > 0)
    	{
    		try {
	    		Connection connection = null;
	    		connection = ConnectionDB.GetConnection();
	    		PerguntaDAO primeiraPergunta = listaPerguntas.remove(0);
				txtPergunta.setText(primeiraPergunta.getDescricao());
				ResultSet rsResposta = null;
				CallableStatement callRespostas = connection.prepareCall("{call dbo.buscaRespostaPorId(?)}");
				callRespostas.setInt(1, primeiraPergunta.getId());
				
				if (callRespostas.execute()) {
					rsResposta = callRespostas.getResultSet();
					
					while (rsResposta.next()) {
						String respostaDescricao = callRespostas.getResultSet().getString(3);
						int respostaPontuacao = callRespostas.getResultSet().getInt(4);
						int idResposta = callRespostas.getResultSet().getInt(1);
						
						RespostaDAO resposta = new RespostaDAO(respostaDescricao, respostaPontuacao);
						resposta.idPergunta = primeiraPergunta.getId();
						resposta.idResposta = idResposta;
						
						listaRespostas.add(resposta);
					}
				}
			
				for (int i = 0; i < listaRespostas.size(); i++) {
					listRespostas.getItems().add(String.valueOf(listaRespostas.get(i).idResposta) + " : " + listaRespostas.get(i).getResposta());
				}	
    		}
    		catch (Exception ex) {
    			System.out.println(ex);
    		}
    	}
    	else
    	{
    		AlertHelper.showWarningMessage("Fim", "O questionário finalizou","Soma: "+  String.valueOf(somaTotal));
    		
    		try {
	    		Connection connection = null;
	    		connection = ConnectionDB.GetConnection();
				CallableStatement callRespostas = connection.prepareCall("{call dbo.insereQuestionarioResposta(?, ?, ?)}");
				callRespostas.setInt(1, idPaciente);
				callRespostas.setString(2, nomeQuestionario);
				callRespostas.setInt(3, somaTotal);
				callRespostas.execute();
				
				txtPergunta.setText("");
				perguntaAtual = 0;
		    	txtNumeroPergunta.setText("");
		    	
		    	somaTotal = 0;
    		}
    		catch (Exception ex) {
    			System.out.println(ex);
    		}
    	}	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadCategoriasComboBox();
		loadPacientesComboBox();
		loadQuetionariosComboBox();
		txtPergunta.setText("");
		perguntaAtual = 0;
    	txtNumeroPergunta.setText("");
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
    
	public void loadPacientesComboBox()
	{
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;

		try {
			cbPaciente.getItems().clear();
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.buscaPacientes(?,?)}");
				
			call.setString(1, "");
			call.setString(2, "");
			// This returns true if there are any remaining ResultSets to fetch
			if (call.execute()) {
				rs = call.getResultSet();

				// Move on to the next result set, if any
				while (rs.next()) {
					String paciente = "";
					paciente = call.getResultSet().getString(1) + ": " +call.getResultSet().getString(4);

					cbPaciente.getItems().add(paciente);
				};
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		ConnectionDB.CloseConnection(cstmt, rs);
	}
	
	public void loadQuetionariosComboBox()
	{
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;

		try {
			cbQuestionario.getItems().clear();
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.buscaQuestionarios}");

			// This returns true if there are any remaining ResultSets to fetch
			if (call.execute()) {
				rs = call.getResultSet();

				// Move on to the next result set, if any
				while (rs.next()) {
					String questionario = "";
					questionario = call.getResultSet().getString(1) + ": " +call.getResultSet().getString(5);

					cbQuestionario.getItems().add(questionario);
				};
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		ConnectionDB.CloseConnection(cstmt, rs);
	}
}










