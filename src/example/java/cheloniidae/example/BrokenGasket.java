package cheloniidae.example;

import cheloniidae.TurtleCommand;
import cheloniidae.frames.DarkSingleTurtleScene;

import static cheloniidae.frames.CoreCommands.*;

public class BrokenGasket extends DarkSingleTurtleScene {
    public static void main(String[] args) {
        new BrokenGasket();
    }

    public TurtleCommand commands() {
        TurtleCommand recursiveInvocation = recurse("cheloniidae.example.BrokenGasket", 7, scale(1.0 / 2.0, true), pass());
        double scale = 500;
        return recursiveBlock("cheloniidae.example.BrokenGasket",
                size(40),
                recursiveInvocation, move(scale),
                bank(-60), turn(120), move(scale), jump(-scale), turn(-120), bank(60), turn(120),
                recursiveInvocation, move(scale),
                bank(-60), turn(120), move(scale), jump(-scale), turn(-120), bank(60), turn(120),
                recursiveInvocation, move(scale),
                bank(-60), turn(120), move(scale), turn(120), recursiveInvocation, turn(-120),
                jump(-scale), turn(-120), bank(60), turn(120));
    }
}
