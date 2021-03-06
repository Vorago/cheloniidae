package cheloniidae.example;

import cheloniidae.TurtleCommand;
import cheloniidae.frames.SingleTurtleScene;

import java.awt.*;

import static cheloniidae.frames.CoreCommands.*;

public class Donut extends SingleTurtleScene {
    public static void main(String[] args) {
        new Donut();
    }

    public TurtleCommand commands() {
        return sequence(size(0.5), color(new Color(0.5f, 0.65f, 0.55f, 0.5f)),
                repeat(36,
                        jump(100),
                        repeat(90,
                                move(2), pitch(4)),
                        jump(-100),
                        turn(10)));
    }
}
