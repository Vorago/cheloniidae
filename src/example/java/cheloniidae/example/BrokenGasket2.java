package cheloniidae.example;

import cheloniidae.TurtleCommand;
import cheloniidae.frames.DarkSingleTurtleScene;

import static cheloniidae.frames.CoreCommands.*;

public class BrokenGasket2 extends DarkSingleTurtleScene {
    public static void main(String[] args) {
        new BrokenGasket2();
    }

    public TurtleCommand commands() {
        TurtleCommand recursiveInvocation = recurse("cheloniidae.example.BrokenGasket2", 3, scale(1.0 / 3.0, true), pass());
        final double scale = 500;
        final double size = 20;
        return sequence(pitch(180),
                recursiveBlock("cheloniidae.example.BrokenGasket2",
                        repeat(4, size(size), move(scale * 2), recursiveInvocation, size(size), move(scale), recursiveInvocation, size(size), pitch(90),
                                move(scale), recursiveInvocation, size(size), move(scale), recursiveInvocation, size(size), move(scale), pitch(90),
                                move(scale), recursiveInvocation, size(size), move(scale * 2), jump(-scale * 3),
                                pitch(-90), jump(-scale * 3), pitch(-90), turn(90), bank(1))));
    }
}
