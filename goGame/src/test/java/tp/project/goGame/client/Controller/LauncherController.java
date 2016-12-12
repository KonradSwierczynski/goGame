package tp.project.goGame.client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tp.project.goGame.client.Model.Model;

public class LauncherController {
	
	private Model model;
	private Stage stage;
	
    @FXML
    private TextField textFieldLogin;

    @FXML
    private PasswordField textBoxPassword;

    @FXML
    private Button buttonLogIn;

    @FXML
    private Button buttonRegister;
    
    @FXML
    private TextField textFieldRegisterUsername;

    @FXML
    private PasswordField textBoxRegisterPassword;

    @FXML
    private TextField textFieldRegisterEmail;

    @FXML
    private TextField textFieldRegisterNickname;

    @FXML
    private Button buttonCreateAccount;
    
    public LauncherController(Model model, Stage stage) {
    	this.model = model;
    	this.stage = stage;
    }
    
    public LauncherController(Model model) {
    	this.model = model;
    }
    
    public Stage getStage() {
    	return stage;
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    public Model getModel() {
    	return model;
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

    @FXML
    void ButtonCreateAccountClicked(ActionEvent event) {

    }
    
    @FXML
    void ButtonLogInClicked(ActionEvent event) {

    }

    @FXML
    void ButtonRegisterClicked(ActionEvent event) {
    	
    }

}

