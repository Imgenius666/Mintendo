package LoginAndRegister;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.TetrisModel;
import views.TetrisView;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class LoginController{
    TetrisModel model;
    Stage stage;
    @FXML
    private Button LoginButton, RegisterButton;
    @FXML
    private TextField UsernameField, PasswordField;

    final String user_name_salt = "kIwAldqhsyTATTA";
    final String user_password_salt = "T5nOjEFdT0uQxBD9VWGE";
    // Encryption algorithm using SHA-256
    // Algorithm taken from https://en.wikipedia.org/wiki/Cryptographic_hash_function
    public static byte[] getSHACode(String ipt) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(ipt.getBytes(StandardCharsets.UTF_8));
    }
    // Encryption algorithm using SHA-256
    // Algorithm taken from https://en.wikipedia.org/wiki/Cryptographic_hash_function
    public static String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
    /*
    A function that alerts the user to tell that the user is not exist in the stored files.
     */
    private void UsernameNotExistAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Username");
        alert.setHeaderText("User doesn't exists");
        alert.setContentText("Please register a new account");
        Optional<ButtonType> opt = alert.showAndWait();
        if(opt.isPresent()){
            if (opt.get() == ButtonType.OK) {
                UsernameField.setText("");
                PasswordField.setText("");
            }
        }
    }
    /*
    If there is a mismatch between the password stored and the user's password, alert the user.
     */
    private void PasswordMismatchAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Password mismatch");
        alert.setHeaderText("Wrong Password");
        alert.setContentText("Please enter the password correctly");
        Optional<ButtonType> opt = alert.showAndWait();
        if(opt.isPresent()){
            if (opt.get() == ButtonType.OK) {
                PasswordField.setText("");
            }
        }
    }
    /*
    Opens the register page.
     */
    @FXML
    public void OpenRegisterPage(ActionEvent event){
        try{
            Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegisterPage.fxml")));
            this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });
            this.stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /*
        Space for checking the username(probably email address, will be implemented later).
    */
    public void checkLegal(){
        String user_name = UsernameField.getText();
        String user_password = PasswordField.getText();
        CheckUsernameAndPassword(user_name, user_password);
    }

    /*
      Function that checks the username and password input by the user.
    */
    private void CheckUsernameAndPassword(String username, String user_password){
        try{
            String paths = "assets/LoginFiles/UserInformation";
            File file = new File(paths);
            if (!file.exists()) {
                boolean res = file.createNewFile();
                if (!res) {
                    System.out.println("File already exists");
                }
            }else {
                if(file.length() == 0){
                    UsernameNotExistAlert();
                }else{
                    boolean flag = true;
                    String user_name = toHexString(getSHACode(username + user_name_salt));
                    Scanner myReader = new Scanner(file);
                    while (myReader.hasNextLine()) {
                        String stored_username = myReader.nextLine().split(" ")[0];
                        String stored_password = myReader.nextLine().split(" ")[1];
                        if (stored_username.equals(user_name)) {
                            String password = toHexString(getSHACode(user_password + user_password_salt));
                            if(password.equals(stored_password)){
                                login_successful(stored_username);
                                flag = false;
                            }else {
                                PasswordMismatchAlert();
                            }
                        }
                    }
                    if(flag){
                        UsernameNotExistAlert();
                    }
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
    If the login is successful, load the game file and start the game.
    The previous page(login page) will then be closed.
     */
    @FXML
    public void login_successful(String username){
        try{
            Stage stage = (Stage)(UsernameField).getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(TetrisView.class.getResource("Tetris_View.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("CSC207 Tetris");
            this.model = new TetrisModel(); // create a model
            this.model.SetUser(username);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
            primaryStage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
