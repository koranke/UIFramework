package ui.core.controls;

import com.microsoft.playwright.Locator;
import org.testng.Assert;

/*
Usage
* locator: Locator for the root element that contains the rows.
* rowLocatorPattern: Xpath string for identifying a row relative to the root parent element, as defined by "locator".
* hasHeader: Set to true if first row represents headers for columns withing each subsequent row.
* headerUsesRowLocatorPattern: Set to true if header row locator xpath is the same as each subsequent row.
* headerControlId: optional.  Xpath string for identifying the header row relative to the root parent element. Set this
if "headerUsesRowLocatorPattern" is false.
 */
public class ListControl<T> extends BaseControl {
    protected int currentRow;
    protected RepeatingControl<Label> searchLabel;
    protected String rowLocatorPattern;
    protected boolean hasHeader;
    protected boolean headerUsesRowLocatorPattern;
    protected String headerControlId;

    public ListControl(Locator locator) {
        super(locator);
    }

    private int getAdjustedRow(int row) {
        return hasHeader && headerUsesRowLocatorPattern ? row + 1 : row;
    }

    public int getRowCount() {
        int rowCount = locator.locator(rowLocatorPattern).all().size();
        return hasHeader && headerUsesRowLocatorPattern ? rowCount - 1 : rowCount;
    }

    public ListControl assertRowCount(int expectedRowCount) {
        expectedRowCount = getAdjustedRow(expectedRowCount);
        Assert.assertEquals(getRowCount(), expectedRowCount, "Unexpected row count");
        return this;
    }

    public Label getHeader(int column) {
        if (hasHeader) {
            return new Label(locator.locator(headerControlId).nth(column - 1));
        } else {
            return null;
        }
    }

    public T withRow(int row) {
        this.currentRow = row;
        return (T) this;
    }

    public T withRow(String text) {
        this.currentRow = searchLabel.getIndex(text);
        return (T) this;
    }

}
