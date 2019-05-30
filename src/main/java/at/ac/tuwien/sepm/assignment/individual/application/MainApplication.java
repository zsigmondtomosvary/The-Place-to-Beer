package at.ac.tuwien.sepm.assignment.individual.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

public final class MainApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void start(Stage primaryStage) throws Exception {
        // setup application
        primaryStage.setTitle("The Place to Beer");
        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(event -> LOG.debug("Application shutdown initiated"));

        primaryStage.setScene(
            createScene(
                loadMainPane()
            )
        );

        try{
            ConnectDb.getInstance().getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // show application
        primaryStage.show();
        primaryStage.toFront();
        LOG.debug("Application startup complete");

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                ConnectDb.closeConnection();
            }
        });
    }

    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        LOG.info("Get Main Scene");

        Pane mainPane = (Pane) loader.load(
            getClass().getResourceAsStream(
                Changescene.MAIN
            )
        );

        MainController mainController = loader.getController();

        Changescene.setMainController(mainController);
        Changescene.loadScene(Changescene.MAINSCENE);

        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
            mainPane
        );

        return scene;
    }


    public static void main(String[] args) {
        LOG.debug("Application starting with arguments={}", (Object) args);
        Application.launch(MainApplication.class, args);
    }

}
