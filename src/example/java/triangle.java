import cheloniidae.TurtleCommand;
import cheloniidae.frames.SingleTurtleScene;

import static cheloniidae.frames.CoreCommands.*;
import static cheloniidae.frames.CoreCommands.triangle;

public class triangle extends SingleTurtleScene {
    public static void main(String[] args) {
        new triangle();
    }

    public TurtleCommand commands() {
        return triangle(move(100), sequence(turn(60), move(100)));
    }
}
