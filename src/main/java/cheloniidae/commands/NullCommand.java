package cheloniidae.commands;

import cheloniidae.Turtle;

public class NullCommand extends AtomicCommand {
    public NullCommand applyTo(final Turtle t) {
        return this;
    }
}
