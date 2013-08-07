dconf integration for Java and JavaFX
=====================================


Introduction
------------

This library provides an easy way to integrate dconf into
your Java / JavaFX applications.

It uses the <a href="https://github.com/cathive/fx-glib">fx-glib</a> GLib bindings
to map native java objects to GVariant pointers and vice versa.


Highlights
----------

*   Native bindings to libdconf via BridJ (no need to install any native libraries)
*   Nice object-oriented API
*   Supported platforms: Linux, possibly others (untested)


Requirements
------------

*   libdconf, libglib2,.... whatever is needed to interact with dconf
*   Running D-Bus instance (the default dconf client interacts with libdconf via D-Bus)
*   Oracle Java SE 7 Runtime (possibly OpenJDK 7 works, haven't tested)

Usually every modern Linux distribution should meet the first of the two requirements mentioned above.


Features
--------

Below you'll find a list of already existing features as well as features that are planned for
future releases:

- [*] Bindings to the C-API of dconf (libdconf) -  about 50% done
- [x] Support for synchronous calls
- [ ] Support for asynchronous calls
- [ ] Nice JavaFX API using Workers, Services, Callbacks and Properties
- [*] java.util.prefs.Preferences implementation that uses dconf as it's backend - works for simple stuff already.


Example usage
-------------

The Java code below shows how to play around with a few configuration keys.:

```java
// Obtains a new dconf client instance.
final org.gnome.dconf.Client dconfClient = org.gnome.dconf.Client.create();

// Use the client to write some config entries...
dconfClient.writeBoolean("/com/example/ExampleApp/exampleBoolean", true);
dconfClient.writeString("/com/example/ExampleApp/exampleString", "Hello, World!")

// Retrieve some config values...
final boolean exampleBoolean = dconfClient.readBoolean("/com/example/ExampleApp/exampleBoolean");
final String exampleString = dconfClient.readString("/com/example/ExampleApp/exampleString");
```
