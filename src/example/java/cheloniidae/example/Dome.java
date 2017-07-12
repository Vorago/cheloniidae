package cheloniidae.example;

import cheloniidae.TurtleCommand;
import cheloniidae.frames.SingleTurtleScene;

import static cheloniidae.frames.CoreCommands.*;

public class Dome extends SingleTurtleScene {
    public static void main(String[] args) {
        new Dome();
    }

    public TurtleCommand commands() {
        return bandSplitReplicator(turn(6),
                repeat(30, turn(6),
                        pitch(3),
                        repeat(60, move(4), triangleEmit(), pitch(6)),
                        pitch(3)));
    }
}
