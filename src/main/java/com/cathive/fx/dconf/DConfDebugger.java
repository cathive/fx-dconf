/*
 * Copyright © 2013 The Cat Hive Developers
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the licence, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

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

        final List<String> apps = client.listDirs("/org/gnome/desktop/");
        for (final String app: apps) {
            System.out.println(" * " + app);
        }


        final Preferences prefs = Preferences.userNodeForPackage(DConfDebugger.class);
        prefs.put("key1", "value1");
        System.out.println(prefs.get("key1", "ouch!"));

    }

}
