
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import alert.AlertHelper;
import connection.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CadastroPacienteController {
	/*
	 * @FXML //Annotation private Label labelStatus;
	 */
	@FXML
	private Pane pnlContent;
	@FXML
    private TextField txtNome;

    @FXML
    private DatePicker txtDataNascimento;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtTelCelular;

    @FXML
    private TextField txtTelResidencial;

    @FXML
    private TextField txtProfissao;

    @FXML
    private CheckBox cbBebeSim;

    @FXML
    private CheckBox cbBebeNao;

    @FXML
    private TextField txtEstadoCivil;

    @FXML
    private CheckBox cbFumaSim;

    @FXML
    private CheckBox cbFumaNao;

    @FXML
    private TextField txtConvenio;

    @FXML
    TextArea txtAntecedentes;

    @FXML
    private TextArea txtHistorico;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextArea txtComportamento;

    @FXML
    private TextArea txtHumor;

    @FXML
    private TextArea txtDemandaAtual;

    @FXML
    private TextArea txtEncaminhamento;

    @FXML
    private Button txtCadastrarPaciente;

    @FXML
    private Button btnLimparCampos;

    @FXML
    private TextField txtEscolaridade;

    @FXML
    void cadastrarPaciente(ActionEvent event) {
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;

		try {
			
			String nome =  txtNome.getText();		    
		    LocalDate dataNascimento  = txtDataNascimento.getValue();
		    Date date = Date.from(dataNascimento.atStartOfDay(ZoneId.systemDefault()).toInstant());
		    String endereco =  txtEndereco.getText();		    
		    String telCelular =  txtTelCelular.getText();		    
		    String telResidencial = txtTelResidencial.getText();		    
		    String profissao = txtProfissao.getText();		    
		    Boolean bebe = cbBebeSim.isSelected();		    
		    String estadoCivil   =  txtEstadoCivil.getText();		    
		    Boolean fuma   = cbFumaSim.isSelected();		    
		    String convenio =  txtConvenio.getText();		    
		    String antecedentes   =  txtAntecedentes.getText();		    
		    String  historico  =  txtHistorico.getText();		    
		    String CPF   =  txtCPF.getText();		    
		    String comportamento   =  txtComportamento.getText();		    
		    String  humor  =  txtHumor.getText();		    
		    String demandaAtual  =  txtDemandaAtual.getText();		    
		    String  encaminhamento  =  txtEncaminhamento.getText();		    
		    String  escolaridade  =  txtEscolaridade.getText();
		    
			if (nome == null || nome.isEmpty()) {
				AlertHelper.showErrorMessage("Erro",
											 "Não foi possível salvar paciente",
											 "Campo 'nome' está vazio.\nPor favor, preencher antes de salvar.");   
			} else {
				connection = ConnectionDB.GetConnection();
				CallableStatement call = connection.prepareCall("{call dbo.inserePaciente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				call.setString(1, CPF);
				call.setString(2, nome);
				call.setString(3, "");
				
				java.util.Calendar cal = Calendar.getInstance();
				java.util.Date utilDate = new java.util.Date(); // your util date
				cal.setTime(utilDate);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);    
				java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime()); // your sql date
				
				call.setDate(4, sqlDate);
				call.setString(5, endereco);
				call.setString(6, telResidencial);
				call.setString(7, telCelular);
				call.setBoolean(8, bebe);
				call.setBoolean(9, fuma);
				call.setString(10, estadoCivil);
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date dateAux = new Date();
				call.setString(11, (dateFormat.format(date).toString()));
				call.setString(12, convenio);
				call.setString(13, profissao);
				call.setString(14, escolaridade);
				call.setString(15, antecedentes);
				call.setString(16, historico);
				call.setString(17, comportamento);
				call.setString(18, humor);
				call.setString(19, demandaAtual);
				call.setString(20, encaminhamento);
				call.execute();
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		ConnectionDB.CloseConnection(cstmt, rs);
    }
    
    @FXML
    void cbBebeItemSelecionadoAlterado(ActionEvent event) {
    	
    	CheckBox chk = (CheckBox) event.getSource();
    	if("Sim".equals(chk.getText()))
    	{
			cbBebeSim.setSelected(true);
			cbBebeNao.setSelected(false);
		}
    	else
    	{
    		cbBebeSim.setSelected(false);
			cbBebeNao.setSelected(true);
    	}
    }

    @FXML
    void cbFumaItemSelecionadoAlterado(ActionEvent event) {
    	CheckBox chk = (CheckBox) event.getSource();
    	if("Sim".equals(chk.getText()))
    	{
			cbFumaSim.setSelected(true);
			cbFumaNao.setSelected(false);
		}
    	else
    	{
    		cbFumaSim.setSelected(false);
    		cbFumaNao.setSelected(true);
    	}
    }

    @FXML
    void limparCampos(ActionEvent event) {
    	txtNome.setText("");
    	txtDataNascimento.setValue(null);

    	txtEndereco.setText("");
    	txtTelCelular.setText("");
    	txtTelResidencial.setText("");
    	txtProfissao.setText("");
    	cbBebeSim.setSelected(false);
    	txtEstadoCivil.setText("");
    	cbFumaSim.setSelected(false);
    	cbBebeNao.setSelected(false);
    	cbFumaNao.setSelected(false);
    	txtConvenio.setText("");
    	txtAntecedentes.setText("");
    	txtHistorico.setText("");
    	txtCPF.setText("");
    	txtComportamento.setText("");
    	txtHumor.setText("");
    	txtDemandaAtual.setText("");
    	txtEncaminhamento.setText("");
    	txtEscolaridade.setText("");
    }
	
}
