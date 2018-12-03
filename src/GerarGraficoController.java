
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import alert.AlertHelper;
import connection.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class GerarGraficoController implements Initializable {
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
    private Button btnGerarGrafico;

    @FXML
    private DatePicker txtDesde;

    @FXML
    private DatePicker txtAte;
    
    @FXML
    private Pane chartPane;

    @FXML
    private LineChart<Number, Number> graficoPontuacao;

    @SuppressWarnings("deprecation")
	@FXML
    void gerarGrafico(ActionEvent event) {
    	String dataDesde = "";
    	String dataAte = "";
    	
    	if(txtDesde.getValue() == null)
    	{
    		dataDesde = "1900-01-01 00:00:00";
    	}
    	else
    	{
    		dataDesde = txtDesde.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:01"));
    	}
    	
    	if(txtAte.getValue() == null)
    	{
    		dataAte = "2100-01-01 00:00:00";
    	}
    	else
    	{
    		dataAte = txtAte.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59"));
    	}
    	System.out.println(dataDesde);
    	System.out.println(dataAte);
    	
    	int idPaciente = Integer.parseInt(cbPaciente.getSelectionModel().getSelectedItem().split(":")[0]);
    	int idCategoria = Integer.parseInt(cbCategoria.getSelectionModel().getSelectedItem().split(":")[0]);
    	
    	CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;
		XYChart.Series series1 = new XYChart.Series();
		try {
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.buscaQuestionarioRespondido(?,?,?)}");
			
			call.setInt(1, idPaciente);
			call.setString(2, dataAte);
			call.setString(3, dataDesde);
			// This returns true if there are any remaining ResultSets to fetch
			if (call.execute()) {
				rs = call.getResultSet();

				// Move on to the next result set, if any
				while (rs.next()) {
					String data = call.getResultSet().getString(4);
					int pontuacao = call.getResultSet().getInt(5);
					System.out.println("DEU CERTO");
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date date = formatter.parse(data);
					series1.getData().add(new XYChart.Data(pontuacao, date.getMonth()));
				};
			}
			System.out.println("OPA");
			
	        final NumberAxis xAxis = new NumberAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        yAxis.setLabel("Progressão");
	        xAxis.setLabel("Tempo");
			graficoPontuacao.getData().add(series1);
			
			Scene scene  = new Scene(graficoPontuacao,800,600);
			chartPane = new Pane();
			chartPane.getChildren().addAll(graficoPontuacao);
			
			
			Stage dialogStage = new Stage();            
            dialogStage.setTitle("Gráfico linha");
            Scene scene2 = new Scene(chartPane);
            dialogStage.setScene(scene);
            
            dialogStage.setAlwaysOnTop(true);
            dialogStage.setFullScreen(true);
            dialogStage.show();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}

		ConnectionDB.CloseConnection(cstmt, rs);
    	
    }
	
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	loadCategoriasComboBox();
    	loadPacientesComboBox();
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
    
    
}











