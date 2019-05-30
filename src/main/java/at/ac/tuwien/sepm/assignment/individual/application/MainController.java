package at.ac.tuwien.sepm.assignment.individual.application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /** Holder of a switchable scene. */
    @FXML
    private StackPane sceneHolder;

    /**
     * Replaces the scene displayed in the scene holder with a new scene.
     *
     * @param node the scene node to be swapped in.
     */
    public void setScene(Node node) {
        sceneHolder.getChildren().setAll(node);
        LOG.info("Set Main Scene");
    }
}
