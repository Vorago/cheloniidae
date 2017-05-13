package cheloniidae;

import cheloniidae.commands.SupportsWindow;

import java.util.Set;
import java.util.TreeSet;

public abstract class BasicTurtle<T extends BasicTurtle>
        extends Replicable<T>
        implements Turtle<T>, SupportsWindow {

    public final Set<Attribute> attributes = new TreeSet<Attribute>();
    protected TurtleWindow window = null;

    public Set<Attribute> attributes() {
        return attributes;
    }

    public T attribute(final Attribute _attribute) {
        attributes.add(_attribute);
        return (T) this;
    }

    public TurtleWindow window() {
        return window;
    }

    public T window(final TurtleWindow _window) {
        window = _window;
        return (T) this;
    }

    public TurtleState serialize() {
        return new State(attributes);
    }

    public T run(final TurtleCommand c) {
        c.applyTo(this);
        return (T) this;
    }

    public TurtleCommand map(final Transformation<TurtleCommand> t) {
        final TurtleState serialized = this.serialize();
        if (serialized instanceof TurtleCommand) return ((TurtleCommand) serialized).map(t);
        else return null;
    }

    public static class State
            extends ImmutableTurtleState
            implements TurtleState, TurtleCommand {

        public final Set<Attribute> attributes = new TreeSet<Attribute>();

        public State(final Set<Attribute> _attributes) {
            attributes.addAll(_attributes);
        }

        public State applyTo(final Turtle t) {
            t.attributes().addAll(attributes);
            return this;
        }
    }
}
