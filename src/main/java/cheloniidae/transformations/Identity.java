package cheloniidae.transformations;

import cheloniidae.Transformation;

public class Identity<T> implements Transformation<T> {
    public Identity() {
    }

    public T transform(final T x) {
        return x;
    }
}
