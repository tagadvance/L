# L

[![build](https://github.com/tagadvance/L/actions/workflows/build.yml/badge.svg)](https://github.com/tagadvance/L/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.tagadvance/l)](https://central.sonatype.com/artifact/com.tagadvance/l)
[![license](https://img.shields.io/github/license/tagadvance/L)](LICENSE)

A super simple library that aims to eliminate logging boilerplate. SLF4J is the only dependency;
bring your own binding.

## Installation

Maven

```xml

<dependency>
  <groupId>com.tagadvance</groupId>
  <artifactId>l</artifactId>
  <version>1.0.0</version>
</dependency>
```

Gradle

```groovy
implementation group: 'com.tagadvance', name: 'l', version: '1.0.0'
```

Gradle Kotlin

```kotlin
implementation("com.tagadvance:l:1.0.0")
```

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
import com.tagadvance.l.L;

public class Foo {

	public Foo() {
		L.logger().trace("This is an instance of {}.", Foo.class.getSimpleName());
	}

}
```

## That's it?

That's it.

## Caveat

`L` finds the calling class by walking the current thread's stack, and it does so on every call —
including calls at a level that is disabled and whose message is thrown away.

Measured on JDK 17, caller 50 frames deep, level disabled:

| | ns/call |
| --- | --- |
| `private static final Logger` field | 3 |
| `L` | 1,800 |

Three orders of magnitude. `L` is a convenience for code where logging is not on the hot path.
Anywhere it is, hold a field.

---

If you find this useful, you can [sponsor the work](https://github.com/sponsors/tagadvance).
