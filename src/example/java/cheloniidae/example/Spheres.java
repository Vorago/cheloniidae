package cheloniidae.example;

import cheloniidae.TurtleCommand;
import cheloniidae.frames.SingleTurtleScene;

import java.awt.*;

import static cheloniidae.frames.CoreCommands.*;

public class Spheres extends SingleTurtleScene {
    public static void main(String[] args) {
        new Spheres();
    }

    public TurtleCommand commands() {
        return sequence(size(0.1), color(new Color(0.3f, 0.5f, 0.5f, 0.8f)), spheres(10));
    }

    public TurtleCommand spheres(int size) {
        return sequence(repeat(35, repeat(72, move(8 - size * 0.5), pitch(5)),
                turn(3)),
                turn(-30),
                bank(8),
                size > 0 ? spheres(size - 1) : pass());
    }
}
