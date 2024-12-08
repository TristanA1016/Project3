import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// TriangleButton that extends JButton and handles triangle-specific actions
public class TriangleButton extends JButton implements ActionListener {
    private JPanel drawingPanel; // Panel for drawing the triangle
    private View view; // Reference to the view
    private MouseHandler mouseHandler; // Mouse handler for triangle drawing
    private TriangleCommand triangleCommand; // Command for managing the triangle drawing
    private UndoManager undoManager; // UndoManager for tracking commands

    public TriangleButton(UndoManager undoManager, View view, JPanel panel) {
        super("Triangle");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = panel;
        this.mouseHandler = new MouseHandler();

        this.addActionListener(this); // Set the action listener
    }

    // Handle button action events
    @Override
    public void actionPerformed(ActionEvent event) {
        // Change cursor to crosshair when the Triangle button is clicked
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        drawingPanel.addMouseListener(mouseHandler); // Attach the mouse handler
        triangleCommand = new TriangleCommand(); // Initialize the triangle command
        undoManager.beginCommand(triangleCommand); // Begin the command for undo/redo
    }

    // Inner class to handle mouse events
    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event)) {
                // Add a new point to the triangle
                triangleCommand.addPoint(View.mapPoint(event.getPoint()));
                view.refresh(); // Refresh the view to show the updated triangle

                if (triangleCommand.isComplete()) { // Check if triangle is complete
                    finalizeTriangle();
                }
            } else if (SwingUtilities.isRightMouseButton(event)) {
                if (triangleCommand.isComplete()) {
                    finalizeTriangle();
                }
            }
        }

        private void finalizeTriangle() {
            triangleCommand.completeTriangle();
            undoManager.endCommand(triangleCommand); // Finalize the command
            drawingPanel.removeMouseListener(this); // Remove the mouse handler
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Reset cursor to default
            view.refresh(); // Refresh the view to show the completed triangle
        }
    }
    
}