package cheloniidae.transformations;

import cheloniidae.Transformation;
import cheloniidae.TurtleCommand;
import cheloniidae.commands.Jump;
import cheloniidae.commands.LineSize;
import cheloniidae.commands.Move;

public class Scale implements Transformation<TurtleCommand> {
    public final double factor;
    public final boolean scaleLineSizes;

    public Scale(final double _factor) {
        this(_factor, false);
    }

    public Scale(final double _factor, final boolean _scaleLineSizes) {
        factor = _factor;
        scaleLineSizes = _scaleLineSizes;
    }

    public TurtleCommand transform(final TurtleCommand c) {
        if (c instanceof Move) return new Move(((Move) c).value * factor);
        else if (c instanceof Jump) return new Jump(((Jump) c).value * factor);
        else if (scaleLineSizes && c instanceof LineSize) return new LineSize(((LineSize) c).value * factor);
        else return c;
    }
}
