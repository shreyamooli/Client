//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package helpers;

import com.sun.javafx.scene.control.skin.TableRowSkin;
import javafx.scene.Node;
import javafx.scene.control.TableRow;

public class TRECskin<S> extends TableRowSkin<S> {
    private final TableRow<S> tableRow;
    private TREC<S> expander;
    private Double tableRowPrefHeight = -1.0D;

    public TRECskin(TableRow<S> tableRow, TREC<S> expander) {
        super(tableRow);
        this.tableRow = tableRow;
        this.expander = expander;
        tableRow.itemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                Node expandedNode = this.expander.getExpandedNode(oldValue);
                if (expandedNode != null) {
                    this.getChildren().remove(expandedNode);
                }
            }

        });
    }

    private Node getContent() {
        Node node = this.expander.getOrCreateExpandedNode(this.tableRow);
        if (!this.getChildren().contains(node)) {
            this.getChildren().add(node);
        }

        return node;
    }

    private Boolean isExpanded() {
        return ((TableRow)this.getSkinnable()).getItem() != null && ((Boolean)this.expander.getCellData(((TableRow)this.getSkinnable()).getIndex())).booleanValue();
    }

    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        this.tableRowPrefHeight = super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
        return this.isExpanded().booleanValue() ? this.tableRowPrefHeight.doubleValue() + this.getContent().prefHeight(width) : this.tableRowPrefHeight.doubleValue();
    }

    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);
        if (this.isExpanded().booleanValue()) {
            this.getContent().resizeRelocate(0.0D, this.tableRowPrefHeight.doubleValue(), w, h - this.tableRowPrefHeight.doubleValue());
        }

    }
}
