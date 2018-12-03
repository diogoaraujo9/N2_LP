
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

public class CategoriaController {
	/*
	 * @FXML //Annotation private Label labelStatus;
	 */
	@FXML
	private Pane pnlContent;
	public TextField txtCategoria;
	public ListView<String> listCategorias;

	@FXML
	protected void cadastrarCategoria(ActionEvent event) {
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;

		try {
			String categoria = txtCategoria.getText();
			if (categoria == null || categoria.isEmpty()) {
				AlertHelper.showErrorMessage("Erro",
											 "Não foi possível salvar categoria",
											 "Campo 'categoria está vazio.\nPor favor, preencher antes de salvar.");   
			} else {
				connection = ConnectionDB.GetConnection();
				CallableStatement call = connection.prepareCall("{call dbo.insereCategoria(?)}");
				call.setString(1, categoria);
				call.execute();
				atualizarListaCategoria(event);
				txtCategoria.setText("");
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		ConnectionDB.CloseConnection(cstmt, rs);
	}

	@FXML
	protected void atualizarListaCategoria(ActionEvent event) {
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;

		try {
			listCategorias.getItems().clear();
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.buscaCategorias}");

			// This returns true if there are any remaining ResultSets to fetch
			if (call.execute()) {
				rs = call.getResultSet();

				// Move on to the next result set, if any
				while (rs.next()) {
					String categoria = "";
					categoria += "Id: " + call.getResultSet().getString(1) + " \t\t";
					categoria += "Categoria: " + call.getResultSet().getString(2) + " \t\t";
					categoria += "Data de criação: " + call.getResultSet().getString(3) + " \t\t";

					listCategorias.getItems().add(categoria);
				};
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		ConnectionDB.CloseConnection(cstmt, rs);
	}

	@FXML
	protected void deletarCategoria(ActionEvent event) {
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;

		try {
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.deletarCategoria(?)}");
			String selected = (String) listCategorias.getSelectionModel().getSelectedItem();
			Integer id = Integer.parseInt(selected.split(" ")[1]);
			call.setInt("@Id", id);
			call.execute();
			atualizarListaCategoria(event);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		ConnectionDB.CloseConnection(cstmt, rs);
	}

	@FXML
	protected void atualizarCategoria(ActionEvent event) {
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection connection = null;

		try {
			connection = ConnectionDB.GetConnection();
			CallableStatement call = connection.prepareCall("{call dbo.atualizarCategoria(?,?)}");
			String selected = (String) listCategorias.getSelectionModel().getSelectedItem();
			Integer id = Integer.parseInt(selected.split(" ")[1]);
			call.setInt("@Id", id);
			call.setString("@Categoria", txtCategoria.getText());
			call.execute();
			atualizarListaCategoria(event);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		ConnectionDB.CloseConnection(cstmt, rs);
	}
}
