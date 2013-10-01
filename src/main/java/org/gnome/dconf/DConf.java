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

package org.gnome.dconf;

import org.bridj.BridJ;
import org.bridj.CRuntime;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.*;
import org.bridj.ann.Runtime;
import org.gtk.glib.GLib.gboolean;
import org.gtk.glib.GLib.gchar;
import org.gtk.glib.GLib.gint;
import org.gtk.glib.GLib.GError;
import org.gtk.glib.GLib.GVariant;

/**
 * @author Benjamin P. Jung
 */
@Library("dconf")
@Runtime(CRuntime.class)
public final class DConf {

    static {
        BridJ.register(DConf.class);
    }

    private DConf() {}

    @Name("dconf_client_new")
    public static native Pointer<DConfClient> clientNew();

    @Name("dconf_client_read")
    public static native Pointer<GVariant> clientRead(Pointer<DConfClient> client,
                                                      @gchar Pointer<Byte> key);

    @Name("dconf_client_list")
    public static native @gchar Pointer<Pointer<Byte>> clientList(Pointer<DConfClient> client,
                                                                  @gchar Pointer<Byte> dir,
                                                                  @gint Pointer<Integer> length);

    @Name("dconf_client_is_writable")
    public static native @gboolean boolean clientIsWritable(Pointer<DConfClient> client,
                                                            @gchar Pointer<Byte> key);

    @Name("dconf_client_write_fast")
    public static native @gboolean boolean clientWriteFast(Pointer<DConfClient> client,
                                                           @gchar Pointer<Byte> key,
                                                           Pointer<GVariant> value,
                                                           Pointer<Pointer<GError>> error);

    @Name("dconf_client_sync")
    public static native void clientSync(Pointer<DConfClient> client);

    @Struct
    @Name("DConfClient")
    public static final class DConfClient extends StructObject {
        public DConfClient() { super(); }
        public DConfClient(Pointer<DConfClient> peer) { super(peer); }
        public Pointer<DConfClient> getPeer() { return (Pointer<DConfClient>) this.peer; }
    }

}
