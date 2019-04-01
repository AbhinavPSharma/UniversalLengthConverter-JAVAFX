package application;
import java.util.*;
import application.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField value;
    @FXML
    private ComboBox cb1;
    @FXML
    private ComboBox cb2;
   
    Stage dialogStage = new Stage();
    Scene scene;
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int resultSet = 0;
 
    public FXMLDocumentController() {
        connection = ConnectionUtil.connectdb();
    }

    public Double convMeter(Double x,String y)
    {
    	HashMap<String, Double> map = new HashMap<>();
    	Double meter=0.0;
        map.put("Meter", 1.0);map.put("DecaMeter", 10.0);map.put("HectoMeter", 100.0);map.put("KiloMeter", 1000.0);map.put("MegaMeter", 1000000.0);map.put("GigaMeter", 1000000000.0);map.put("TeraMeter", 1000000000000.0);map.put("PetaMeter", 1000000000000000.0);map.put("ExaMeter", 1000000000000000000.0);map.put("ZetaMeter", 1000000000000000000000.0);map.put("YottaMeter", 1000000000000000000000000.0);map.put("DeciMeter", 0.1);map.put("CentiMeter", .01);map.put("MilliMeter", .001);map.put("MicroMeter", .000001);map.put("NanoMeter", .000000001);map.put("PicoMeter", .000000000001);map.put("FemtoMeter", .000000000000001);map.put("AttoMeter", .000000000000000001);map.put("ZeptoMeter", .000000000000000000001);map.put("YoctoMeter", .000000000000000000000001);map.put("Angström", .0000000001);map.put("Inch", 0.0254);map.put("Foot", 0.3048);map.put("Yard", 0.9144);map.put("Mile", 1609.34);map.put("Fathom", 1.8288);map.put("Nautical Mile", 1852.0);map.put("Electron", 0.000000000000000001);map.put("Earth Radius", 6378160.0);map.put("Lunar Radius", 1737100.0);map.put("Light Year", 9460730472580044.0);map.put("Parsec", 9460730472580044.0);map.put("Astronomical Unit", 149600000000.0);map.put("Human Hair", .000181);
       if (map.containsKey(y))  
        { 
            Double a = map.get(y); 
            meter=x*a; 
        }
        return meter;
    }
    public Double conv(Double x,String y)
    {
    	HashMap<String, Double> map = new HashMap<>();
    	Double result=0.0;
        map.put("Meter", 1.0);map.put("DeciMeter", 10.0);map.put("CentiMeter", 100.0);map.put("MilliMeter", 1000.0);map.put("MicroMeter", 1000000.0);map.put("NanoMeter", 1000000000.0);map.put("PicoMeter", 1000000000000.0);map.put("FemtoMeter", 1000000000000000.0);map.put("AttoMeter", 1000000000000000000.0);map.put("ZeptoMeter", 1000000000000000000000.0);map.put("YoctoMeter", 1000000000000000000000000.0);map.put("DecaMeter", 0.1);map.put("HectoMeter", .01);map.put("KiloMeter", .001);map.put("MegaMeter", .000001);map.put("GigaMeter", .000000001);map.put("TeraMeter", .000000000001);map.put("PetaMeter", .000000000000001);map.put("ExaMeter", .000000000000000001);map.put("ZettaMeter", .000000000000000000001);map.put("YottaMeter", .000000000000000000000001);map.put("Angström", 10000000000.0);map.put("Inch", 39.3701);map.put("Foot", 3.28084);map.put("Yard", 1.09361);map.put("Mile", 0.000621371);map.put("Fathom", 0.546807);map.put("Nautical Mile", 0.000539957);map.put("Electron", 1000000000000000000.0);map.put("Earth Radius", 1/6378160.0);map.put("Lunar Radius", 1.0);map.put("Light Year", 1/9460730472580044.0);map.put("Parsec", 1.0/9460730472580044.0);map.put("Astronomical Unit", 1.0/149600000000.0);map.put("Human Hair", 181000000.0);        
        if (map.containsKey(y))  
        { 
            Double a = map.get(y); 
            result=x*a; 
        }
        return result;
    }
    public void convert(ActionEvent event){
    	String inp=value.getText().toString()+" "+cb1.getValue().toString();
    	String res;
    	String sql = "INSERT INTO history(input,result) VALUES (?,?)";
        try {
        		Double x=Double.parseDouble(value.getText().toString());
        		Double mtr=convMeter(x,cb1.getValue().toString());
        		Double con=conv(mtr,cb2.getValue().toString());
        		res=con.toString()+" "+cb2.getValue().toString();
        		preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, inp);
                preparedStatement.setString(2, res);
                resultSet = preparedStatement.executeUpdate();
                infoBox(x.toString()+" "+cb1.getValue().toString()+" = "+con.toString()+" "+cb2.getValue().toString(),"Converted:","Converted" );
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
    public void history(ActionEvent event){
        try {
        		Node node = (Node)event.getSource();
                dialogStage = (Stage) node.getScene().getWindow();
                dialogStage.close();
                scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLHistory.fxml")));
                dialogStage.setScene(scene);
                dialogStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	cb1.getSelectionModel().selectFirst();
    	cb2.getSelectionModel().selectFirst();
        
    }    
    
}