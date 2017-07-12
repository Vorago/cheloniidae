package cheloniidae.example;

import cheloniidae.TurtleCommand;
import cheloniidae.frames.SingleTurtleScene;

import static cheloniidae.frames.CoreCommands.*;

public class CircleTorus extends SingleTurtleScene {
    public static void main(String[] args) {
        new CircleTorus();
    }

    public TurtleCommand commands() {
        return inductiveReplicator(60, sequence(jump(1), turn(6), visible(false)),
                jump(50), bank(210), repeat(180, move(1), turn(2)));
    }
}
