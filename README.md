# L

A super simple library that aims to eliminate logging boilerplate. There are no dependencies.

## Boilerplate Example

Most Java developers are familiar with the classic logging boilerplate, e.g.

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Foo {

    private static final Logger logger = LoggerFactory.getLogger(Foo.class);

    public Foo() {
        logger.trace("This is an instance of {}.", Foo.class.getSimpleName());
    }

}
```

## Example with L

```java
import com.tagadvance.l;

public class Foo {

    public Foo() {
        L.logger().trace("This is an instance of {}.", Foo.class.getSimpleName());
    }

}
```

## That's it?
That's it.
