package LoginAndRegister;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {
    Stage stage;
    @FXML
    private Label Note;
    @FXML
    private TextField UsernameField, NewPasswordField, ConfirmPasswordField;
    @FXML
    private ImageView back_button;
    final String user_name_salt = "kIwAldqhsyTATTA";
    final String user_password_salt = "T5nOjEFdT0uQxBD9VWGE";
    public final Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    // Algorithm taken from https://en.wikipedia.org/wiki/Cryptographic_hash_function
    public static byte[] getSHACode(String ipt) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(ipt.getBytes(StandardCharsets.UTF_8));
    }
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

    public void UsernameEmptyAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Empty Username");
        alert.setHeaderText("User name cannot be empty");
        alert.setContentText("Please enter a username");
        Optional<ButtonType> opt = alert.showAndWait();
        if(opt.isPresent()){
            if (opt.get() == ButtonType.OK) {
                UsernameField.setText("");
                NewPasswordField.setText("");
                ConfirmPasswordField.setText("");
            }
        }
    }

    public void UsernameInvalidAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Illegal Username");
        alert.setHeaderText("User name is invalid(It contains illegal symbols such as a space)");
        alert.setContentText("Please enter a username");
        Optional<ButtonType> opt = alert.showAndWait();
        if(opt.isPresent()){
            if (opt.get() == ButtonType.OK) {
                UsernameField.setText("");
                NewPasswordField.setText("");
                ConfirmPasswordField.setText("");
            }
        }
    }

    private void PasswordEmptyAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Empty password");
        alert.setHeaderText("Password area empty");
        alert.setContentText("Please enter a password");
        Optional<ButtonType> opt = alert.showAndWait();
        if(opt.isPresent()){
            if (opt.get() == ButtonType.OK) {
                NewPasswordField.setText("");
                ConfirmPasswordField.setText("");
            }
        }
    }

    private void PasswordInvalidAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Password");
        alert.setHeaderText("Password does not meet the requirements");
        alert.setContentText("Please check the password and try again");
        Optional<ButtonType> opt = alert.showAndWait();
        if(opt.isPresent()){
            if (opt.get() == ButtonType.OK) {
                NewPasswordField.setText("");
                ConfirmPasswordField.setText("");
            }
        }
    }

    private void ContentMismatchAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Password mismatch");
        alert.setHeaderText("Password does not match with the confirm password");
        alert.setContentText("Please check the password");
        Optional<ButtonType> opt = alert.showAndWait();
        if(opt.isPresent()){
            if (opt.get() == ButtonType.OK) {
                NewPasswordField.setText("");
                ConfirmPasswordField.setText("");
            }
        }
    }
    /*
    Checks whether the username and the password is legal.
     */
    @FXML
    public void RegisterUser() {
        String username = UsernameField.getText();
        if (username.equals("")) {
            UsernameEmptyAlert();
        }else if(username.contains(" ")){
            UsernameInvalidAlert();
        }else{
            String password = NewPasswordField.getText();
            String confirmPassword = ConfirmPasswordField.getText();
            if (password.equals("") || confirmPassword.equals("")) {
                PasswordEmptyAlert();
            }else if(password.length() < 8 || !this.textPattern.matcher(password).matches()){
                PasswordInvalidAlert();
            }else if (!password.equals(confirmPassword)){
                ContentMismatchAlert();
            }else{
                SetUser(username,password);
                go_back();
            }
        }
    }
    /*
    Opens the file and writes the encrypted username and password into it, each user on a line, separated by lines.
     */
    public void SetUser(String username, String password) {
        try{
            String paths = "assets/LoginFiles/UserInformation";
            File file = new File(paths);
            if (!file.exists()) {
                boolean res = file.createNewFile();
                if (!res) {
                    System.out.println("File already exists");
                }
            }else {
                String username_and_password = toHexString(getSHACode(username + user_name_salt)) + " " +
                        toHexString(getSHACode(password + user_password_salt)) +"\n";
                Writer output;
                output = new BufferedWriter(new FileWriter(file, true));  //clears file every time
                output.append(username_and_password);
                output.close();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void go_back() {
        try{
            Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginPage.fxml")));
            this.stage = (Stage)(back_button).getScene().getWindow();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.Note.setText("""
                Password must be >= 8 characters,
                 have at least a lower case character,
                 an upper case character,
                 and a number.""");
    }


}
