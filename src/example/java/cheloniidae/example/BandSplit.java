package cheloniidae.example;

import cheloniidae.TurtleCommand;
import cheloniidae.frames.SingleTurtleScene;

import static cheloniidae.frames.CoreCommands.*;

public class BandSplit extends SingleTurtleScene {
    public static void main(String[] args) {
        new BandSplit();
    }

    public TurtleCommand commands() {
        return bandSplitReplicator(sequence(turn(90), move(10), turn(-90)),
                repeat(30, move(10), triangleEmit(), pitch(12)));
    }
}
