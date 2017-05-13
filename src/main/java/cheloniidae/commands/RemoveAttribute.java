package cheloniidae.commands;

import cheloniidae.Attribute;
import cheloniidae.Turtle;
import cheloniidae.TurtleCommand;

public class RemoveAttribute extends UnaryCommand<Attribute> {
    public RemoveAttribute(final Attribute _value) {
        super(_value);
    }

    public TurtleCommand applyTo(final Turtle t) {
        t.attributes().remove(value);
        return this;
    }
}
