package cheloniidae.predicates;

import cheloniidae.Attribute;
import cheloniidae.HasAttributes;
import cheloniidae.Predicate;

public class HasAttribute<T extends HasAttributes> extends AtomicPredicate<T> {
    public final Predicate<Attribute> predicate;

    public HasAttribute(final Predicate<Attribute> _predicate) {
        predicate = _predicate;
    }

    public boolean matches(final T attributeContainer) {
        for (final Attribute a : attributeContainer.attributes())
            if (predicate.matches(a)) return true;
        return false;
    }
}
