package controller.basic;

import dao.UserDAO;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

public class Login {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginBtn;

    public void login(Event event) throws ClassNotFoundException, SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isBlank() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi đăng nhập");
            alert.setHeaderText("Không được bỏ trống tên đăng nhập hoặc mật khẩu!");
            alert.setContentText("Vui lòng kiểm tra lại thông tin đăng nhập.");
            alert.showAndWait();
            return;
        }

        UserDAO userDTO = new UserDAO();

        try {
            User user = userDTO.authenticate(username, password);

            if (user != null) {
                try {
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/basic/frame.fxml"));
                    new JMetro(root, Style.LIGHT);
                    Stage stage = new Stage();
                    stage.setTitle("QLTV");
                    stage.setScene(new Scene(root, 450, 450));
                    stage.setMaximized(true);
                    stage.show();
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi!");
                    alert.setHeaderText("Có lỗi xảy ra!");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    System.out.println(e.getMessage());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi đăng nhập!");
                alert.setHeaderText("Tên đăng nhập hoặc mật khẩu sai!");
                alert.setContentText("Vui lòng kiểm tra lại thông tin đăng nhập.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi CSDL");
            alert.setHeaderText("Có lỗi xảy ra trong quá trình đăng nhập: ");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.out.println(e.getMessage());
        }
    }
}