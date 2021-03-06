package cheloniidae.example;

import cheloniidae.StandardRotationalTurtle;
import cheloniidae.TurtleCommand;
import cheloniidae.commands.SupportsPosition;
import cheloniidae.frames.SingleTurtleScene;

import static cheloniidae.frames.CoreCommands.*;
import static cheloniidae.frames.CoreCommands.triangle;

public class Pyramid extends SingleTurtleScene {
    public static void main(String[] args) {
        new Pyramid();
    }

    public TurtleCommand commands() {
        final StandardRotationalTurtle c1 = new StandardRotationalTurtle();
        final StandardRotationalTurtle c2 = new StandardRotationalTurtle().move(100);
        final StandardRotationalTurtle c3 = new StandardRotationalTurtle().turn(60).move(100);
        return sequence(triangle((SupportsPosition) c2, c3),
                turn(30), pitch(-60), move(100), triangle((SupportsPosition) c1, c2),
                triangle((SupportsPosition) c2, c3),
                triangle((SupportsPosition) c1, c3));
    }
}
