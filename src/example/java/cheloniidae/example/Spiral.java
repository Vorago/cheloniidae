package cheloniidae.example;

import cheloniidae.TurtleCommand;
import cheloniidae.frames.SingleTurtleScene;

import static cheloniidae.frames.CoreCommands.*;

public class Spiral extends SingleTurtleScene {
    public static void main(String[] args) {
        new Spiral();
    }

    public TurtleCommand commands() {
        return repeat(5760, pitch(0.25),
                move(10.0),
                pitch(-0.25),
                turn(4.0));
    }
}
