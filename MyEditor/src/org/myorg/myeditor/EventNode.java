package org.myorg.myeditor;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.time.ZonedDateTime;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.myorg.myapi.Event;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.actions.Presenter;
import org.openide.util.lookup.Lookups;

public class EventNode extends AbstractNode {

    public EventNode(Event obj) {
        super(Children.create(new EventChildFactory(), true), Lookups.singleton(obj));
        setDisplayName("Event " + obj.getIndex());
    }

    public EventNode() {
        super(Children.create(new EventChildFactory(), true));
        setDisplayName("Root");
    }

    @Override
    public String getHtmlDisplayName() {
        Event obj = getLookup().lookup(Event.class);
        if (obj != null) {
            return "<font color='!textText'>Event " + obj.getIndex() + "</font>"
                    + " <font color='!controlShadow'><i>" + obj.getDate() + "</i></font>";
        } else {
            return null;
        }
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("org/myorg/myeditor/icon.png");
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public Action[] getActions(boolean popup) {
        return new Action[]{new MyAction()};
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        final Event obj = getLookup().lookup(Event.class);
        if (obj != null) {
        try {
            Property indexProp = new PropertySupport.Reflection(obj, Integer.class, "getIndex", null);
            Property dateProp = new PropertySupport.Reflection(obj, String.class, "getDateAsString", null);
            indexProp.setName("index");
            dateProp.setName("date");

            set.put(indexProp);
            set.put(dateProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        }

        sheet.put(set);
        return sheet;
    }

    private class MyAction extends AbstractAction implements Presenter.Popup {

        public MyAction() {
            putValue(NAME, "Do Something");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Event obj = getLookup().lookup(Event.class);
            JOptionPane.showMessageDialog(null, "Hello from " + obj);
        }

        @Override
        public JMenuItem getPopupPresenter() {
            JMenu result = new JMenu("Submenu");  //remember JMenu is a subclass of JMenuItem
            result.add(new JMenuItem(this));
            result.add(new JMenuItem(this));
            return result;
        }
    }
}
