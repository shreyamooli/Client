//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package helpers;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

public final class TREC<S> extends TableColumn<S, Boolean> {
    private static final String STYLE_CLASS = "expander-column";
    private static final String EXPANDER_BUTTON_STYLE_CLASS = "expander-button";
    private final Map<S, Node> expandedNodeCache = new HashMap();
    private final Map<S, BooleanProperty> expansionState = new HashMap();
    private Callback<TREC.TableRowDataFeatures<S>, Node> expandedNodeCallback;

    public BooleanProperty getExpandedProperty(S item) {
        BooleanProperty value = (BooleanProperty)this.expansionState.get(item);
        if (value == null) {
            value = new SimpleBooleanProperty(item, "expanded", false) {
                protected void invalidated() {
                    TREC.this.getTableView().refresh();
                    if (!this.getValue().booleanValue()) {
                        TREC.this.expandedNodeCache.remove(this.getBean());
                    }

                }
            };
            this.expansionState.put(item, value);
        }

        return (BooleanProperty)value;
    }

    public Node getOrCreateExpandedNode(TableRow<S> tableRow) {
        int index = tableRow.getIndex();
        if (index > -1 && index < this.getTableView().getItems().size()) {
            S item = this.getTableView().getItems().get(index);
            Node node = (Node)this.expandedNodeCache.get(item);
            if (node == null) {
                node = (Node)this.expandedNodeCallback.call(new TREC.TableRowDataFeatures(tableRow, this, item));
                this.expandedNodeCache.put(item, node);
            }

            return node;
        } else {
            return null;
        }
    }

    public Node getExpandedNode(S item) {
        return (Node)this.expandedNodeCache.get(item);
    }

    public TREC(Callback<TREC.TableRowDataFeatures<S>, Node> expandedNodeCallback) {
        this.expandedNodeCallback = expandedNodeCallback;
        this.getStyleClass().add("expander-column");
        this.setCellValueFactory((param) -> {
            return this.getExpandedProperty(param.getValue());
        });
        this.setCellFactory((param) -> {
            return new TREC.ToggleCell();
        });
        this.installRowFactoryOnTableViewAssignment();
    }

    private void installRowFactoryOnTableViewAssignment() {
        this.tableViewProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.getTableView().setRowFactory((param) -> {
                    return new TableRow<S>() {
                        protected Skin<?> createDefaultSkin() {
                            return new TRECskin(this, TREC.this);
                        }
                    };
                });
            }

        });
    }

    public void toggleExpanded(int index) {
        BooleanProperty expanded = (BooleanProperty)this.getCellObservableValue(index);
        expanded.setValue(!expanded.getValue().booleanValue());
    }

    public static final class TableRowDataFeatures<S> {
        private TableRow<S> tableRow;
        private TREC<S> tableColumn;
        private BooleanProperty expandedProperty;
        private S value;

        public TableRowDataFeatures(TableRow<S> tableRow, TREC<S> tableColumn, S value) {
            this.tableRow = tableRow;
            this.tableColumn = tableColumn;
            this.expandedProperty = (BooleanProperty)tableColumn.getCellObservableValue(tableRow.getIndex());
            this.value = value;
        }

        public TableRow<S> getTableRow() {
            return this.tableRow;
        }

        public TREC<S> getTableColumn() {
            return this.tableColumn;
        }

        public BooleanProperty expandedProperty() {
            return this.expandedProperty;
        }

        public void toggleExpanded() {
            BooleanProperty expanded = this.expandedProperty();
            expanded.setValue(!expanded.getValue().booleanValue());
        }

        public Boolean isExpanded() {
            return this.expandedProperty().getValue();
        }

        public void setExpanded(Boolean expanded) {
            this.expandedProperty().setValue(expanded);
        }

        public S getValue() {
            return this.value;
        }
    }

    private final class ToggleCell extends TableCell<S, Boolean> {
        private JFXButton button = new JFXButton();

        public ToggleCell() {
            this.button.setFocusTraversable(false);
            this.button.getStyleClass().add("expander");
            this.button.setPrefSize(19.0D, 19.0D);
            this.button.setPadding(new Insets(0.0D));
            ImageView v = new ImageView(new Image(getClass().getResourceAsStream("/images/icons8-edit-row-80.png")));
            v.setSmooth(true);
            v.setPreserveRatio(true);
            v.setFitWidth(18);
            v.setFitHeight(18);
            this.button.setGraphic(v);
            this.button.setOnAction((event) -> {
                TREC.this.toggleExpanded(this.getIndex());
            });
        }

        protected void updateItem(Boolean expanded, boolean empty) {
            super.updateItem(expanded, empty);
            if (expanded != null && !empty) {
              //  this.button.setText(expanded.booleanValue() ? "-" : "+");
                this.setGraphic(this.button);
            } else {
                this.setGraphic((Node)null);
            }

        }
    }
}
