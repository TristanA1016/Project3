import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class Triangle extends Item implements Serializable {
    private final List<Point> vertices; // List of 3 points for the triangle

    // Constructor to initialize the triangle with 3 vertices
    public Triangle(Point vertex1, Point vertex2, Point vertex3) {
        this.vertices = List.of(vertex1, vertex2, vertex3);
    }

    // Render the triangle by drawing lines between its vertices
    @Override
    public void render(UIContext uiContext) {
        uiContext.drawLine(vertices.get(0), vertices.get(1));
        uiContext.drawLine(vertices.get(1), vertices.get(2));
        uiContext.drawLine(vertices.get(2), vertices.get(0));
    }

    // Optional: Determine if a point is inside the triangle
    @Override
    public boolean includes(Point point) {
        return false;
    }

    public void translate(int dx, int dy) {
        for (Point vertex : vertices) {
            vertex.translate(dx, dy); // Translate each vertex by the given offset
        }
    }
}