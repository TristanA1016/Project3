import java.awt.*;

public class MoveCommand extends Command {
    private Point startPoint;
    private Point endPoint;
    private int dx, dy;

    public MoveCommand(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.dx = endPoint.x - startPoint.x;
        this.dy = endPoint.y - startPoint.y;
    }

    @Override
    public void execute() {
        model.translateSelectedItems(dx, dy);
    }

    @Override
    public boolean undo() {
        model.translateSelectedItems(-dx, -dy);
        return true;
    }

    @Override
    public boolean redo() {
        model.translateSelectedItems(dx, dy);
        return true;
    }
}
