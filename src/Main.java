
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.URI;

public class Main extends Application implements EventHandler<ActionEvent>, MessagesCallback {

    Stage window;
    Button year11, year12, year13;
    Button nickname;
    TextField txtMessage;
    TextField nickfield;
    Label label1, firstmsg;
    Label scenemsg;
    public ChatClient c;
    public TextArea textrecieve;
    String ip = "127.0.0.1";
    Scene scene;
    Scene firstscene;
    String username;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main m = new Main();

        try {
            c = new ChatClient(this, new URI("ws://" + ip + ":8887"));
            c.connect();
        } catch (Exception e) {
            System.out.println("connection error");
        }


        window = primaryStage;
        Pane layout = new Pane();
        Pane layout2 = new Pane();

        //Building the first scene which will require the user's nickname
        //First Scene
        firstscene = new Scene(layout2, 500, 200);

        scenemsg = new Label("Welcome to WhatsChat. Enter your desired nickname below");
        scenemsg.setLayoutX(80);
        scenemsg.setLayoutY(10);
        layout2.getChildren().add(scenemsg);

        nickfield = new TextField();
        nickfield.setLayoutX(150);
        nickfield.setLayoutY(50);
        layout2.getChildren().add(nickfield);
        username = nickfield.getText();

        nickname = new Button("Choose Nickname");
        nickname.setOnAction(e -> window.setScene(scene));
        nickname.setLayoutX(170);
        nickname.setLayoutY(100);
        layout2.getChildren().add(nickname);


        window.setScene(firstscene);
        window.show();
        window.setTitle("WHATSCHAT");

        //Main chatting scene.
        scene = new Scene(layout, 676, 600);

        firstmsg = new Label("Welcome to WhatsChat");
        firstmsg.setLayoutX(10);
        firstmsg.setLayoutY(10);
        layout.getChildren().add(firstmsg);

        year11 = new Button("Year 11");
        year11.setLayoutX(10);
        year11.setLayoutY(30);
        year11.setOnAction(this);
        layout.getChildren().add(year11);

        year12 = new Button("Year 12");
        year12.setLayoutX(85);
        year12.setLayoutY(30);
        layout.getChildren().add(year12);

        year13 = new Button("Year 13");
        year13.setLayoutX(160);
        year13.setLayoutY(30);
        year13.setOnAction(this);
        layout.getChildren().add(year13);

        label1 = new Label("Enter your message");
        label1.setLayoutX(10);
        label1.setLayoutY(57);
        layout.getChildren().add(label1);

        txtMessage = new TextField();
        txtMessage.setLayoutX(10);
        txtMessage.setLayoutY(75);
        txtMessage.setPrefWidth(500);
        txtMessage.setText("");
        layout.getChildren().add(txtMessage);


        textrecieve = new TextArea();
        textrecieve.setLayoutX(10);
        textrecieve.setLayoutY(110);
        textrecieve.setPrefWidth(600);
        textrecieve.setPrefHeight(480);
        textrecieve.setText(username);
        layout.getChildren().add(textrecieve);


    }

    @Override
    public void stop() {
        try {
            c.close();
        } catch (Exception e) {
            System.out.print("c.close did not work");
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == year13) {
            System.out.println("You sent the message to Year 13");
            String msg = txtMessage.getText();
            System.out.println("Sending to server: " + msg);


        }
        if (event.getSource() == year12) {
            System.out.println("You sent the message to Year 12");
            textrecieve.setText(c.getMessages());
        }
        if (event.getSource() == year11) {
            System.out.println("You sent the message to Year 11");
            sendMessage(nickfield.getText()+": "+txtMessage.getText());

        }
    }

    public void sendMessage(String msg) {
        try {

            System.out.println("Check the server for Connection...bro.");
            while (!this.c.getOpenStatus()) {
                System.out.println("Waiting for the connection to open...");
            }

            this.c.send(msg);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void receiveMessagesCallback(String msg) {
        textrecieve.setText(textrecieve.getText() + "\n\r" + msg);
    }
}











