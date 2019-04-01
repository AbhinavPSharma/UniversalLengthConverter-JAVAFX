package application;

import java.net.URL;
import application.ConnectionUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLHistoryController implements Initializable {
	@FXML
    private ScrollPane pane;

	Stage dialogStage = new Stage();
    Scene scene;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
 
    public FXMLHistoryController() {
        connection = ConnectionUtil.connectdb();
        String sql = "SELECT * FROM history";
        try {
        	preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	public void shw(ActionEvent event){
		String op="Converted: \n----------------------------------\n";
		try {
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (resultSet.next()) {
			String temp="";
		    for (int i = 1; i <= columnsNumber; i++) {
		        if (i > 1) temp+=" = ";
		        String columnValue = resultSet.getString(i);
		        temp+=columnValue;
		    }
		    temp+=("\n");
		    op+=temp;
		}
		TextArea textArea = new TextArea();
		textArea.setText(op);
		pane.setContent(textArea);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void home(ActionEvent event){
		try {
    		Node node = (Node)event.getSource();
            dialogStage = (Stage) node.getScene().getWindow();
            dialogStage.close();
            scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")));
            dialogStage.setScene(scene);
            dialogStage.show();
    }
    catch(Exception e){
        e.printStackTrace();
    }
    }
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {
    	        
    } 

}
