package cheloniidae.example;

import cheloniidae.TurtleCommand;
import cheloniidae.frames.SingleTurtleScene;

import static cheloniidae.frames.CoreCommands.*;

public class Circles extends SingleTurtleScene {
    public static void main(String[] args) {
        new Circles();
    }

    public TurtleCommand commands() {
        return section(50, 3);
    }

    public TurtleCommand section(double scale, int recursionLevel) {
        return repeat(15, move(scale),
                turn((recursionLevel & 1) == 0 ? 36 : -36),
                pitch(1),
                (recursionLevel > 0) ? section(scale / 3.0, recursionLevel - 1) : pass());
    }
}
