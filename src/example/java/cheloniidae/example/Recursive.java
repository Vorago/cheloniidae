package cheloniidae.example;

import cheloniidae.TurtleCommand;
import cheloniidae.frames.SingleTurtleScene;

import static cheloniidae.frames.CoreCommands.*;

public class Recursive extends SingleTurtleScene {
    public static void main(String[] args) {
        new Recursive();
    }

    public TurtleCommand commands() {
        return sequence(turn(180),
                recursiveBlock("cheloniidae.example.Tree", stack.push(),
                        size(4),
                        bank(random(360.0)),
                        stack.push(),
                        turn(random(20.0)),
                        move(100),
                        recurse("cheloniidae.example.Tree", 16, scale(0.8, true), pass()),
                        stack.pop(),
                        stack.push(),
                        turn(random(-40.0)),
                        move(100),
                        recurse("cheloniidae.example.Tree", 16, scale(0.7, true), pass()),
                        stack.pop(),
                        stack.pop()));
    }
}
