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

import static org.bridj.Pointer.pointerToCString;
import static org.bridj.Pointer.pointerToPointer;
import static org.gnome.dconf.DConf.DConfClient;
import org.bridj.Pointer;
import org.gtk.glib.GLib;
import org.gtk.glib.GLib.GError;
import org.gtk.glib.GLib.GVariant;
import org.gtk.glib.GLib.GVariantClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * dconf client
 * <p>Instances of this class can be used to interact with the dconf subsystem.</p>
 * @author Benjamin P. Jung
 */
public class Client implements AutoCloseable {

    private DConfClient _client;

    /**
     * Constructs a new dconf client instance.
     * <p>This constructor should never be used. Construct new dconf client instances by calling
     * {@link #create()}.</p>
     * @param _client
     *     Internal C-structure to be wrapped by OOP functionality.
     */
    private Client(final DConfClient _client) {
        super();
        this._client = _client;
    }

    /**
     * Creates a new dconf client instance.
     * @return
     *     A new dconf client instance.
     */
    public static Client create() {
        final Pointer<DConfClient> _client = DConf.clientNew();
        return new Client(_client.get());
    }


    public Boolean readBoolean(final String key) {
        return this.<Boolean>read(key, GVariantClass.BOOLEAN);
    }

    public void writeBoolean(final String key, final Boolean value) {
        this.<Boolean>write(key, value, GVariantClass.BOOLEAN);
    }

    public Byte readByte(final String key) {
        return this.<Byte>read(key, GVariantClass.BYTE);
    }

    public void writeByte(final String key, final Byte value) {
        this.<Byte>write(key, value, GVariantClass.BYTE);
    }

    public String readString(final String key) {
        return this.<String>read(key, GVariantClass.STRING);
    }

    public void writeString(final String key, final String value) {
        this.<String>write(key, value, GVariantClass.STRING);
    }

    /**
     * Gets the list of all dirs and keys immediately under the given dir.
     * <p>In most cases it will probably not make sense for you to call this method directly.
     * Use {@link #listDirs(String)} and {@link #listKeys(String)} instead.</p>
     * @param dir
     *     The dir to list the contents of.
     *     Cannot be {@code null}.
     * @return
     *     A list that contains the names of all direct sub-dirs of the given
     *     dir, never {@code null}.
     */
    public List<String> list(final String dir) {

        return list(dir, true, true);

    }

    public List<String> listKeys(final String dir) {
        return list(dir, false, true);
    }

    public List<String> listDirs(final String dir) {
        return list(dir, true, false);
    }

    /**
     * Internal helper method to retrieve either dirs or keys under a given dir.
     * @param dir
     *     The dir to list the contents of.
     *     Cannot be {@code null}
     * @param dirs
     *     {@code true} if you want to list all sub-dirs directly underneath the given dir,
     *     {@code false} otherwise.
     * @param keys
     *     {@code true} if you want to retrieve all keys directly underneath the given dir,
     *     {@code false} otherwise.
     * @return
     *     A list of dirs and/or keys that can be found directly underneath the given dir.
     */
    protected List<String> list(final String dir, final boolean dirs, final boolean keys) {

        if (!dir.startsWith("/") || !dir.endsWith("/")) {
            throw new IllegalArgumentException("dirs must start and end with a single slash (\"/\").");
        }

        final Pointer<Integer> _lengthPtr = Pointer.allocateInt();
        final Pointer<Pointer<Byte>> _list = DConf.clientList(_client.getPeer(), pointerToCString(dir), _lengthPtr);

        final int _length = _lengthPtr.get().intValue();
        final List<String> list = new ArrayList<>(_length);

        for (int i = 0; i < _length; i++) {
            final String entry = _list.get(i).getCString();
            final boolean isDir = entry.endsWith("/");
            if ((isDir && dirs) || (!isDir && keys)) {
                list.add(entry);
            }
        }

        return list;
    }

    /**
     * Helper method that wraps up all the different conversion facilities offered by GLib.
     * @param key
     * @param variantClass
     * @param <T>
     * @return
     */
    protected <T> T read(final String key, final GVariantClass variantClass) {

        // Fetch config entry from dconf.
        final Pointer<GVariant> _variantPtr = DConf.clientRead(this._client.getPeer(), pointerToCString(key));
        if (_variantPtr == Pointer.NULL) {
            return null;
        }

        switch (variantClass) {
            case BOOLEAN:
                return (T) Boolean.valueOf(GLib.variantGetBoolean(_variantPtr));
            case BYTE:
                return (T) Byte.valueOf(GLib.variantGetByte(_variantPtr));
            case INT16:
                return (T) Short.valueOf(GLib.variantGetInt16(_variantPtr));
            case INT32:
                return (T) Integer.valueOf(GLib.variantGetInt32(_variantPtr));
            case INT64:
                return (T) Long.valueOf(GLib.variantGetInt64(_variantPtr));
            case DOUBLE:
                return (T) Double.valueOf(GLib.variantGetDouble(_variantPtr));
            case STRING:
                return (T) GLib.variantGetString(_variantPtr).getCString();
            default:
                throw new IllegalStateException("Unsupported variant class encountered.");
        }
    }

    protected <T> void write(final String key, final T value, final GVariantClass variantClass) {

        final Pointer<Pointer<GError>> _error = pointerToPointer(Pointer.NULL);
        final Pointer<GVariant> _value;

        switch (variantClass) {
            case BOOLEAN:
                _value = GLib.variantNewBoolean(((Boolean) value).booleanValue());
                break;
            case BYTE:
                _value = GLib.variantNewByte(((Byte) value).byteValue());
                break;
            case INT16:
                _value = GLib.variantNewInt16(((Short) value).shortValue());
                break;
            case INT32:
                _value = GLib.variantNewInt32(((Integer) value).intValue());
                break;
            case INT64:
                _value = GLib.variantNewInt64(((Long) value).longValue());
                break;
            case DOUBLE:
                _value = GLib.variantNewDouble(((Double) value).doubleValue());
                break;
            case STRING:
                _value = GLib.variantNewString(pointerToCString((String) value));
                break;
            default:
                throw new IllegalStateException("Unsupported variant class encountered.");
        }

        DConf.clientWriteFast(this._client.getPeer(), pointerToCString(key), _value, _error);

        if (_error.get() != Pointer.NULL) {
            // TODO Throw a more meaningful exception and extract the contents of the error message text.
            throw new RuntimeException();
        }

    }

    /**
     * Checks whether this dconf client instance can (still) be used.
     * @return
     *     {@code} true if this client instance can be used, {@code false} otherwise.
     */
    public boolean isReady() {
        return this._client != null;
    }

    protected void checkState() {
        if (!this.isReady()) {
            throw new IllegalStateException("dconf Client has been closed.");
        }
    }

    @Override
    public void close() {
        if (this._client != null) {
            DConf.clientSync(this._client.getPeer());
            this._client = null;
        }
    }

}
