package cheloniidae;

import cheloniidae.commands.Sequence;

import cheloniidae.frames.CoreCommands;
import cheloniidae.frames.SingleTurtleScene;

import java.awt.*;

public class RealtimeTurtle implements RealtimeTurtleApi {
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private SingleTurtleScene scene = new SingleTurtleScene() {
        @Override
        public TurtleCommand commands() {
            return new Sequence(CoreCommands.size(1));
        }
    };
    private Color currentColor = new Color(0, 0, 0, 1f);

    public RealtimeTurtle() {
        size(1);
        color(0, 1, 0);
        left(180);
    }

    @Override
    public void forward(double distance) {
        scene.run(CoreCommands.move(distance));
    }

    @Override
    public void back(double distance) {
        scene.run(CoreCommands.move(-distance));
    }

    @Override
    public void size(double size) {
        scene.run(CoreCommands.size(size));
    }

    @Override
    public void color(int r, int g, int b) {
        currentColor = new Color(r / 255F, g / 255F, b / 255F, 1F);
        scene.run(CoreCommands.color(currentColor));
    }

    @Override
    public void penUp() {
        scene.run(CoreCommands.color(TRANSPARENT));
    }

    @Override
    public void penDown() {
        scene.run(CoreCommands.color(currentColor));
    }

    @Override
    public void left(double degree) {
        scene.run(CoreCommands.turn(degree));
    }

    @Override
    public void right(double degree) {
        scene.run(CoreCommands.turn(-degree));
    }
}

