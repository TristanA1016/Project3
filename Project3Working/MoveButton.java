import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MoveButton extends JButton implements ActionListener {
    private JPanel drawingPanel;
    private View view;
    private UndoManager undoManager;
    private Point initialPoint;
    private MoveCommand moveCommand;
    private MouseHandler mouseHandler;

    public MoveButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Move");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        addActionListener(this);
        mouseHandler = new MouseHandler();
    }

    public void actionPerformed(ActionEvent event) {
        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.addMouseMotionListener(mouseHandler);
        view.setCursor(new Cursor(Cursor.MOVE_CURSOR));
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                initialPoint = View.mapPoint(e.getPoint());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                Point endPoint = View.mapPoint(e.getPoint());
                moveCommand = new MoveCommand(initialPoint, endPoint);
                undoManager.beginCommand(moveCommand);
                undoManager.endCommand(moveCommand);
                drawingPanel.removeMouseListener(this);
                drawingPanel.removeMouseMotionListener(this);
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                view.refresh();
            }
        }
    }
}

