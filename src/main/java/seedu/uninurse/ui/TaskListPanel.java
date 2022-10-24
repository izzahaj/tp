package seedu.uninurse.ui;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.uninurse.model.person.Patient;
import seedu.uninurse.model.task.Task;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";

    @FXML
    private ListView<Task> taskListView;
    @FXML
    private Label name;
    @FXML
    private FlowPane tags;
    @FXML
    private Label header;

    /**
     * Creates a {@code TaskListPanel} with the given {@code Patient}.
     */
    public TaskListPanel(Patient patient) {
        super(FXML);
        taskListView.setItems(FXCollections.observableList(patient.getTasks().getTasks()));
        taskListView.setCellFactory(listview -> new TaskListViewCell());
        taskListView.prefWidthProperty().bind(this.getRoot().widthProperty());

        header.setText("Tasks:");
        name.setText(patient.getName().toString());
        patient.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.getValue()))
                .forEach(tag -> tags.getChildren().add(new Label(tag.getValue())));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskListCard}.
     */
    class TaskListViewCell extends ListCell<Task> {
        TaskListViewCell() {
            super();
            setStyle("-fx-padding: 0px");
            prefWidthProperty().bind(taskListView.widthProperty());
        }

        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskListCard(task, getIndex() + 1).getRoot());
            }

        }
    }
}