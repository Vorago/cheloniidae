import cheloniidae.PathwiseTriangleConnector;
import cheloniidae.StandardRotationalTurtle;
import cheloniidae.TurtleCommand;
import cheloniidae.Vector;
import cheloniidae.frames.SingleTurtleScene;

import java.awt.*;

import static cheloniidae.frames.CoreCommands.*;

public class band extends SingleTurtleScene<PathwiseTriangleConnector<StandardRotationalTurtle>> {
    public static void main(String[] args) {
        new band();
    }

    public PathwiseTriangleConnector<StandardRotationalTurtle> createTurtle() {
        return new PathwiseTriangleConnector<StandardRotationalTurtle>();
    }

    public TurtleCommand commands() {
        turtle.add(new StandardRotationalTurtle().position(new Vector(-50, 0, -50)).bank(315))
                .add(new StandardRotationalTurtle().position(new Vector(-50, 0, 50)).bank(45))
                .add(new StandardRotationalTurtle().position(new Vector(50, 0, 50)).bank(135))
                .add(new StandardRotationalTurtle().position(new Vector(50, 0, -50)).bank(225));

        return sequence(color(new Color(0f, 0f, 0f, 0.02f)),
                repeat(120, jump(5), turn(90), jump(50), pitch(3), jump(-50), turn(-90), triangleEmit()));
    }
}
