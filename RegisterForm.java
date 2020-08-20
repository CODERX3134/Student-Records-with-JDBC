import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;

public class RegisterForm extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // shadows for neumorphic view
        DropShadow shadow1 = new DropShadow();
        shadow1.setBlurType(BlurType.THREE_PASS_BOX);
        shadow1.setColor(Color.rgb(255,255,255,1));
        shadow1.setOffsetX(-6);
        shadow1.setOffsetY(-6);

        DropShadow shadow2 = new DropShadow();
        shadow2.setBlurType(BlurType.THREE_PASS_BOX);
        shadow2.setColor(Color.rgb(0,0,0,0.1));
        shadow2.setOffsetY(6);
        shadow2.setOffsetX(6);

        /*Body*/
        AnchorPane bodyPane = new AnchorPane();
        bodyPane.setId("body-pane");

        /*Form*/
        AnchorPane form = new AnchorPane();
        form.setId("form");
        form.setEffect(shadow1);
        shadow1.setInput(shadow2);
        form.setLayoutY(50);
        form.setLayoutX(30);
        FontAwesomeIconView userIcon = new FontAwesomeIconView(FontAwesomeIcon.USER_PLUS);
        userIcon.setId("user-icon");

        /*Setting Labels and Input Boxes*/
        Label user = new Label("",userIcon);
        user.setId("user");
        user.setLayoutX(180);
        user.setLayoutY(20);
        GridPane inputHolder = new GridPane();   /*Grid pane for display properly*/
        inputHolder.setLayoutX(50);
        inputHolder.setLayoutY(120);
        inputHolder.setHgap(30);
        inputHolder.setVgap(30);
        Label id = new Label("Id: ");
        id.setId("input-label");
        JFXTextField txt_id = new JFXTextField();
        txt_id.setId("inputs");
        txt_id.setPromptText("2019***");
        Label name = new Label("Name: ");
        name.setId("input-label");
        JFXTextField txt_name = new JFXTextField();
        txt_name.setId("inputs");
        txt_name.setPromptText("coderx");
        Label mobile = new Label("Mobile: ");
        mobile.setId("input-label");
        JFXTextField txt_mobile = new JFXTextField();
        txt_mobile.setPromptText("0761318390");
        txt_mobile.setId("inputs");
        Label course = new Label("Course: ");
        course.setId("input-label");
        JFXTextField txt_course = new JFXTextField();
        txt_course.setPromptText("AI and Data Science");
        txt_course.setId("inputs");
        /*Adding process to the grid*/
        inputHolder.add(id,0,0);
        inputHolder.add(txt_id,1,0);
        inputHolder.add(name,0,1);
        inputHolder.add(txt_name,1,1);
        inputHolder.add(mobile,0,2);
        inputHolder.add(txt_mobile,1,2);
        inputHolder.add(course,0,3);
        inputHolder.add(txt_course,1,3);
        HBox buttonBox = new HBox(30);
        JFXButton submitBtn = new JFXButton("Submit");
        submitBtn.setId("buttons");
        JFXButton deleteBtn = new JFXButton("Delete");
        deleteBtn.setId("buttons");
        JFXButton editBtn = new JFXButton("Edit");
        editBtn.setId("buttons");
        buttonBox.getChildren().addAll(submitBtn,deleteBtn,editBtn);
        buttonBox.setLayoutX(20);
        buttonBox.setLayoutY(400);
        form.getChildren().addAll(user,inputHolder,buttonBox);
        Label copyright = new Label("Copyright CODERX Alright Reserved");
        copyright.setId("copyright");
        copyright.setLayoutY(570);
        copyright.setLayoutX(450);
        FontAwesomeIconView closeIcon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
        closeIcon.setId("close-icon");
        Label close = new Label("",closeIcon);
        close.setId("close");
        close.setLayoutX(1000);
        close.setLayoutY(5);
        close.setOnMouseClicked(e -> System.exit(0));
        Label stReg = new Label("Student Registration");
        stReg.setId("student-registration");
        stReg.setLayoutX(500);
        stReg.setLayoutY(50);

        /*Creating the table*/
        TableView<Student> stu_records = new TableView<>();
        stu_records.setId("table-view");
        stu_records.setPrefSize(450,300);
        stu_records.setLayoutX(500);
        stu_records.setLayoutY(100);
        //setting-up the table headings
        TableColumn<Student, Integer> st_id = new TableColumn<>("Id");
        st_id.setPrefWidth(85);
        TableColumn<Student, String> st_name = new TableColumn<>("Name");
        st_name.setPrefWidth(138);
        TableColumn<Student, String> st_mobile = new TableColumn<>("Mobile");
        st_mobile.setPrefWidth(88);
        TableColumn<Student, String> st_course = new TableColumn<>("Course");
        st_course.setPrefWidth(137);
        stu_records.setEffect(shadow1);
        shadow1.setInput(shadow2);

        // add columns to the table
        stu_records.getColumns().addAll(st_id,st_name,st_mobile,st_course);
        bodyPane.getChildren().addAll(form,copyright,close,stReg,stu_records);

        submitBtn.setOnAction(e ->{
            addStudent(txt_id,txt_name,txt_mobile,txt_course);
            update_table(stu_records,st_id,st_name,st_mobile,st_course);

        });
        Scene scene = new Scene(bodyPane, 1024,600);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());

        bodyPane.setOnMousePressed(e ->{
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        bodyPane.setOnMouseDragged( e ->{
            primaryStage.setX(e.getScreenX() - xOffset);
            primaryStage.setY(e.getScreenY() - yOffset);
        });
        update_table(stu_records,st_id,st_name,st_mobile,st_course);

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();



    }
    public static void main(String[] args){
        launch();
    }

    /*Add to dataBase*/
    private void addStudent(JFXTextField textField1, JFXTextField textField2, JFXTextField textField3, JFXTextField textField4){
        int id = Integer.parseInt(textField1.getText());
        String name = textField2.getText();
        String mobile = textField3.getText();
        String course = textField4.getText();
        String query = "INSERT INTO record VALUES (?,?,?,?)";
        /*DataBase Connection*/
        String uri = "jdbc:mysql://localhost/db-test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user_name = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(uri,user_name,password);
            System.out.println("Connected to MySql Server");
            PreparedStatement insert = dbConnection.prepareStatement(query);
            insert.setInt(1,id);
            insert.setString(2,name);
            insert.setString(3,mobile);
            insert.setString(4,course);
            insert.executeUpdate();
            System.out.println("Successfully Added to DataBase");
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setContentText("SuccessFully Added to DataBase");
            successAlert.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Ocurred");
        }


        System.out.println(id+" : "+name+" : "+mobile+" : "+course);
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
    }

    private void update_table(TableView table,TableColumn id, TableColumn name, TableColumn mobile, TableColumn course){
        String uri = "jdbc:mysql://localhost/db-test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user_name = "root";
        String password = "";
        String query = "SELECT * FROM record";
        ObservableList<Student> data = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(uri,user_name,password);
            System.out.println("Connected to MySql Server");
            Statement readDB = dbConnection.createStatement();
            ResultSet results = readDB.executeQuery(query);
            //results.next();

            while (results.next()){
                Student student = new Student(results.getInt(1),results.getString(2),results.getString(3),
                        results.getString(4));
                data.add(student);
                table.itemsProperty().setValue(data);
                id.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
                name.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
                mobile.setCellValueFactory(new PropertyValueFactory<Student, String>("mobile"));
                course.setCellValueFactory(new PropertyValueFactory<Student, String>("course"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
