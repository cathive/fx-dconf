package com.cathive.fx.dconf;

import org.gnome.dconf.Client;
import org.gnome.dconf.DConf;

import java.util.List;
import java.util.prefs.Preferences;

/**
 * @author Benjamin P. Jung
 */
public class DConfDebugger {

    public static void main(String[] args) {

        System.out.println("Creating DConf client...");
        final Client client = Client.create();

        client.writeBoolean("/com/example/ExampleApp/exampleBoolean", false);
        client.writeByte("/com/example/ExampleApp/exampleByte", (byte) 1);
        client.writeString("/com/example/ExampleApp/exampleString", "Hello, World!");

        System.out.println("/some/bogus/Stuff/key = " + client.readBoolean("/some/bogus/Stuff/key"));
        System.out.println("/org/gnome/Contacts/did-initial-setup = " + client.readBoolean("/org/gnome/Contacts/did-initial-setup"));
        System.out.println("/org/gnome/Contacts/view-subset = " + client.readString("/org/gnome/Contacts/view-subset"));

        final List<String> apps = client.list("org/gnome/desktop");
        for (final String app: apps) {
            System.out.println(app);
        }


        final Preferences prefs = Preferences.userNodeForPackage(DConfDebugger.class);
        prefs.put("key1", "value1");
        System.out.println(prefs.get("key1", "ouch!"));

    }

}
