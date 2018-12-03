
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ConsultaPacienteController {
	/*
	 * @FXML //Annotation private Label labelStatus;
	 */
	@FXML
	private Pane pnlContent;

    @FXML
    private TextField txtNomePaciente;

    @FXML
    private ListView<String> listPacientes;

    @FXML
    private Button btnListaPacientes;

    @FXML
    private Button btnDeletarPaciente;

    @FXML
    private Button btnAtualizarPaciente;

    @FXML
    private TextField txtCPFPaciente;

    @FXML
    private Button btnRealizarConsulta;

    @FXML
    void atualizarListaPacientes(ActionEvent event) {
    	CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;

		try {
			listPacientes.getItems().clear();
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.buscaPacientes(?, ?)}");
			
			call.setString(1, txtNomePaciente.getText());
			call.setString(2, txtCPFPaciente.getText());

			// This returns true if there are any remaining ResultSets to fetch
			if (call.execute()) {
				rs = call.getResultSet();

				// Move on to the next result set, if any
				while (rs.next()) {
					String paciente = "";
					paciente += "Id: " + call.getResultSet().getString(1) + " \t\t";
					paciente += "Nome: " + call.getResultSet().getString(4) + " \t\t";
					paciente += "CPF: " + call.getResultSet().getString(3) + " \t\t";
					paciente += "Tel. Residencial: " + call.getResultSet().getString(8) + " \t\t";
					paciente += "Tel. Comercial: " + call.getResultSet().getString(9) + " \t\t";

					listPacientes.getItems().add(paciente);
				};
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		ConnectionDB.CloseConnection(cstmt, rs);
    }

    @FXML
    void atualizarPaciente(ActionEvent event) {

    }

    @FXML
    void deletarPaciente(ActionEvent event) {
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;

		try {
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.deletarPaciente(?)}");
			String selected = (String) listPacientes.getSelectionModel().getSelectedItem();
			Integer id = Integer.parseInt(selected.split(" ")[1]);
			call.setInt("@Id", id);
			call.execute();
			atualizarListaPacientes(event);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		ConnectionDB.CloseConnection(cstmt, rs);
    }

    @FXML
    void realizarConsulta(ActionEvent event) {
    	
    }
}




