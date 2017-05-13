package cheloniidae.commands;

import cheloniidae.Turtle;

import java.awt.*;

public interface SupportsBodyColor<T extends Turtle> {
    public Color bodyColor();

    public T bodyColor(Color c);
}
