package cheloniidae.commands;

import cheloniidae.Turtle;

public class Visible extends UnaryCommand<Boolean> {
    public Visible(final boolean value) {
        super(value);
    }

    public Visible applyTo(final Turtle t) {
        if (t instanceof SupportsVisible) ((SupportsVisible) t).visible(value);
        return this;
    }
}
