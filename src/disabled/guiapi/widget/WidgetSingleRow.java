package drzhark.guiapi.widget;

import de.matthiasmann.twl.Widget;

import java.util.ArrayList;

/**
 * This is a layout widget designed to arrange your widgets in a row. It
 * specifies height and width for each widget when you add them.
 *
 * @author lahwran
 */
public class WidgetSingleRow extends Widget {

    /**
     * This is the default height of any new widgets.
     */
    public int defaultHeight = 20;
    /**
     * This is the default width of any new widgets.
     */
    public int defaultWidth = 150;
    protected ArrayList<Integer> heights = new ArrayList<Integer>();
    protected ArrayList<Widget> widgets = new ArrayList<Widget>();
    protected ArrayList<Integer> widths = new ArrayList<Integer>();
    /**
     * This defines the space between child widgets.
     */
    public int xSpacing = 3;

    /**
     * This creates a new WidgetSingleRow, specifying the default width and
     * height for any new widgets, as well as adding any widgets you would like
     * to add.
     *
     * @param defwidth The default width to use for any new widgets.
     * @param defheight The default height to use for any new widgets.
     * @param widgets The widgets you are adding.
     */
    public WidgetSingleRow(int defwidth, int defheight, Widget... widgets) {
        setTheme("");
        this.defaultWidth = defwidth;
        this.defaultHeight = defheight;
        for (int i = 0; i < widgets.length; i++) {
            add(widgets[i]);
        }
    }

    @Override
    public void add(Widget widget) {
        add(widget, this.defaultWidth, this.defaultHeight);
    }

    /**
     * This adds a new Widget with specified width and height.
     *
     * @param widget The widget you are adding.
     * @param width The width of the widget you are adding.
     * @param height The height of the widget you are adding.
     */
    public void add(Widget widget, int width, int height) {
        this.widgets.add(widget);
        this.heights.add(height);
        this.widths.add(width);
        super.add(widget);
    }

    private int getHeight(int idx) {
        if (this.heights.get(idx) >= 0) {
            return this.heights.get(idx);
        } else {
            return this.widgets.get(idx).getPreferredHeight();
        }
    }

    @Override
    public int getPreferredHeight() {
        int maxheights = 0;
        for (int i = 0; i < this.heights.size(); i++) {
            if (getHeight(i) > maxheights) {
                maxheights = getHeight(i);
            }
        }
        return maxheights;
    }

    @Override
    public int getPreferredWidth() {
        int totalwidth = (this.widths.size() - 1) * this.xSpacing;
        totalwidth = totalwidth >= 0 ? totalwidth : 0;
        for (int i = 0; i < this.widths.size(); i++) {
            totalwidth += getWidth(i);
        }
        return totalwidth;
    }

    private int getWidth(int idx) {
        if (this.widths.get(idx) >= 0) {
            return this.widths.get(idx);
        } else {
            return this.widgets.get(idx).getPreferredWidth();
        }
    }

    @Override
    public void layout() {
        int curXpos = 0;
        for (int i = 0; i < this.widgets.size(); i++) {
            Widget w = this.widgets.get(i);
            w.setPosition(curXpos + getX(), getY());
            w.setSize(getWidth(i), getHeight(i));
            curXpos += getWidth(i) + this.xSpacing;
        }
    }

    @Override
    public Widget removeChild(int idx) {
        this.widgets.remove(idx);
        this.heights.remove(idx);
        this.widths.remove(idx);
        return super.removeChild(idx);
    }

    @Override
    public boolean removeChild(Widget widget) {
        int idx = this.widgets.indexOf(widget);
        this.widgets.remove(idx);
        this.heights.remove(idx);
        this.widths.remove(idx);
        return super.removeChild(widget);
    }
}
