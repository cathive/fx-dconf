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
import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.*;
import org.bridj.ann.Runtime;
import org.gtk.glib.GLib;
import org.gtk.glib.GLib.gboolean;
import org.gtk.glib.GLib.gchar;
import org.gtk.glib.GLib.gint;
import org.gtk.glib.GLib.guint;
import org.gtk.glib.GLib.GCancellable;
import org.gtk.glib.GLib.GError;
import org.gtk.glib.GLib.GType;
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

    @Name("dconf_changeset_new")
    public static native Pointer<DConfChangeset> changesetNew();

    @Name("dconf_changeset_new_database")
    public static native Pointer<DConfChangeset> changesetNewDatabase(Pointer<DConfChangeset> copyOf);

    @Name("dconf_changeset_new_write")
    public static native Pointer<DConfChangeset> changesetNewWrite(@gchar Pointer<Byte> path,
                                                                   Pointer<GVariant> value);

    @Name("dconf_changeset_ref")
    public static native Pointer<DConfChangeset> changesetRef(Pointer<DConfChangeset> changeset);

    @Name("dconf_changeset_unref")
    public static native void changesetUnref(Pointer<DConfChangeset> changeset);

    @Name("dconf_changeset_is_empty")
    public static native @gboolean boolean changesetIsEmpty(Pointer<DConfChangeset> changeset);

    @Name("dconf_changeset_set")
    public static native void changesetSet(Pointer<DConfChangeset> changeset,
                                           @gchar Pointer<Byte> path,
                                           Pointer<GVariant> value);

    @Name("dconf_changeset_get")
    public static native @gboolean boolean changesetGet(Pointer<DConfChangeset> changeset,
                                                        @gchar Pointer<Byte> path,
                                                        Pointer<Pointer<GVariant>> value);

    @Name("dconf_changeset_is_similar_to")
    public static native @gboolean boolean changesetIsSimilarTo(Pointer<DConfChangeset> changeset,
                                                                Pointer<DConfChangeset> other);

    @Name("dconf_changeset_all")
    public static native @gboolean <T> boolean changesetAll(Pointer<DConfChangeset> changeset,
                                                            DConfChangesetPredicate<T> predicate,
                                                            Pointer<T> userData);

    // TODO Map dconf_changeset_describe

    @Name("dconf_changeset_serialise")
    public static native Pointer<GVariant> changesetSerialise(Pointer<DConfChangeset> changeset);

    @Name("dconf_changeset_deserialise")
    public static native Pointer<DConfChangeset> changesetDeserialise(Pointer<GVariant> serialised);

    @Name("dconf_changeset_change")
    public static native void changesetChange(Pointer<DConfChangeset> changeset,
                                              Pointer<DConfChangeset> changes);

    @Name("dconf_changeset_change")
    public static native Pointer<DConfChangeset> changesetDiff(Pointer<DConfChangeset> from,
                                                               Pointer<DConfChangeset> to);

    @Name("dconf_client_get_type")
    public static native @GType int clientGetType();

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

    @Name("dconf_client_write_sync")
    public static native @gboolean boolean clientWriteSync(Pointer<DConfClient> client,
                                                           @gchar Pointer<Byte> key,
                                                           Pointer<GVariant> value,
                                                           Pointer<GCancellable> cancellable,
                                                           Pointer<Pointer<GError>> error);

    @Name("dconf_client_change_fast")
    public static native @gboolean boolean clientChangeFast(Pointer<DConfClient> client,
                                                            Pointer<DConfChangeset> changeset,
                                                            Pointer<Pointer<GError>> error);

    @Name("dconf_client_change_sync")
    public static native @gboolean boolean clientChangeSync(Pointer<DConfClient> client,
                                                            Pointer<DConfChangeset> changeset,
                                                            Pointer<GCancellable> cancellable,
                                                            Pointer<Pointer<GError>> error);

    @Name("dconf_client_watch_fast")
    public static native void clientWatchFast(Pointer<DConfClient> client,
                                              @gchar Pointer<Byte> path);

    @Name("dconf_client_watch_sync")
    public static native void clientWatchSync(Pointer<DConfClient> client,
                                              @gchar Pointer<Byte> path);

    @Name("dconf_client_unwatch_fast")
    public static native void clientUnwatchFast(Pointer<DConfClient> client,
                                                @gchar Pointer<Byte> path);

    @Name("dconf_client_unwatch_sync")
    public static native void clientUnwatchSync(Pointer<DConfClient> client,
                                                @gchar Pointer<Byte> path);

    @Name("dconf_client_sync")
    public static native void clientSync(Pointer<DConfClient> client);

    @Name("dconf_is_path")
    public static native @gboolean boolean isPath(@gchar Pointer<Byte> string,
                                                  Pointer<Pointer<GError>> error);

    @Name("dconf_is_key")
    public static native @gboolean boolean isKey(@gchar Pointer<Byte> string,
                                                 Pointer<Pointer<GError>> error);

    @Name("dconf_is_dir")
    public static native @gboolean boolean isDir(@gchar Pointer<Byte> string,
                                                 Pointer<Pointer<GError>> error);

    @Name("dconf_is_rel_path")
    public static native @gboolean boolean isRelPath(@gchar Pointer<Byte> string,
                                                     Pointer<Pointer<GError>> error);

    @Name("dconf_is_rel_key")
    public static native @gboolean boolean isRelKey(@gchar Pointer<Byte> string,
                                                    Pointer<Pointer<GError>> error);

    @Name("dconf_is_rel_dir")
    public static native @gboolean boolean isRelDir(@gchar Pointer<Byte> string,
                                                    Pointer<Pointer<GError>> error);

    @Struct
    @Name("DConfChangeset")
    public static final class DConfChangeset extends StructObject {
        public DConfChangeset() { super(); }
        public DConfChangeset(Pointer<DConfChangeset> peer) { super(peer); }
        public Pointer<DConfChangeset> getPeer() { return (Pointer<DConfChangeset>) this.peer; }
    }

    @Struct
    @Name("DConfClient")
    public static final class DConfClient extends StructObject {
        public DConfClient() { super(); }
        public DConfClient(Pointer<DConfClient> peer) { super(peer); }
        public Pointer<DConfClient> getPeer() { return (Pointer<DConfClient>) this.peer; }
    }

    @Name("DConfChangesetPredicate")
    public static abstract class DConfChangesetPredicate<T> extends Callback<DConfChangesetPredicate<T>>
    {
        public abstract @gboolean boolean run(@gchar Pointer<Byte> path,
                                              Pointer<GVariant> value,
                                              Pointer<T> userData);
    }

}
