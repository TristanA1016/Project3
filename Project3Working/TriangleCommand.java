import java.awt.*;
import java.util.ArrayList;

// TriangleCommand manages the creation and handling of a triangle item
public class TriangleCommand extends Command {
    private ArrayList<Point> points; // Points defining the triangle
    private Triangle triangleItem; // The finalized triangle item
    private boolean isComplete; // Whether the triangle is complete

    // Constructor initializes the command with an empty points list
    public TriangleCommand() {
        this.points = new ArrayList<>();
        this.isComplete = false;
    }

    // Add a point to the triangle, allowing up to 3 points
    public void addPoint(Point point) {
        if (points.size() < 3) {
            points.add(point);
            model.setChanged();
            if (points.size() == 3) {
                completeTriangle();
            }
        }
    }

    // Complete the triangle when 3 points are added
    public void completeTriangle() {
        if (points.size() == 3) {
            triangleItem = new Triangle(points.get(0), points.get(1), points.get(2));
            isComplete = true;
            model.addItem(triangleItem);
            model.setChanged();
        }
    }

    // Execute the command to render the triangle
    @Override
    public void execute() {
        if (isComplete && triangleItem != null) {
            model.addItem(triangleItem); // Add the polygon to the model
            model.setChanged(); // Notify the view to refresh
        }
    }

    // Undo the triangle creation
    @Override
    public boolean undo() {
        if (triangleItem != null) {
            model.removeItem(triangleItem); // Remove the polygon from the model
            model.setChanged(); // Notify the view to refresh
            return true;
        }
        return false;
    }

    // Redo the triangle creation
    @Override
    public boolean redo() {
        if (triangleItem != null) {
            model.addItem(triangleItem); // Re-add the polygon to the model
            model.setChanged(); // Notify the view to refresh
            return true;
        }
        return false;
    }

    // Returns true if the triangle is complete; otherwise, false
    public boolean isComplete() {
        return isComplete;
    }
}