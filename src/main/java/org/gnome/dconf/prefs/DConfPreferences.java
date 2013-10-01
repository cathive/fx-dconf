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

package org.gnome.dconf.prefs;

import org.gnome.dconf.Client;

import java.util.Iterator;
import java.util.List;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;

/**
 * @author Benjamin P. Jung
 */
public class DConfPreferences extends AbstractPreferences {

    private static final Client DCONF_CLIENT = Client.create();

    /**
     * Creates a preference node with the specified parent and the specified
     * name relative to its parent.
     *
     * @param parent the parent of this preference node, or null if this
     *               is the root.
     * @param name   the name of this preference node, relative to its parent,
     *               or <tt>""</tt> if this is the root.
     * @throws IllegalArgumentException
     *          if <tt>name</tt> contains a slash
     *          (<tt>'/'</tt>),  or <tt>parent</tt> is <tt>null</tt> and
     *          name isn't <tt>""</tt>.
     */
    protected DConfPreferences(final AbstractPreferences parent, final String name) {
        super(parent, name);

    }

    @Override
    protected void putSpi(final String key, final String value) {
        if (key.contains("/") || key.contains(".")) {
            throw new IllegalArgumentException("Key must not contains slashes (\"/\") or dots (\".\").");
        }
        DCONF_CLIENT.writeString(this.absolutePath() + "/" + key, value);
    }

    @Override
    protected String getSpi(final String key) {
        if (key.contains("/") || key.contains(".")) {
            throw new IllegalArgumentException("Key must not contains slashes (\"/\") or dots (\".\").");
        }
        return DCONF_CLIENT.readString(this.absolutePath() + "/" + key);
    }

    @Override
    protected void removeSpi(final String key) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    protected void removeNodeSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    protected String[] keysSpi() throws BackingStoreException {
        final List<String> keys = DCONF_CLIENT.listKeys(this.absolutePath() + "/");
        return keys.toArray(new String[keys.size()]);
    }

    @Override
    protected String[] childrenNamesSpi() throws BackingStoreException {
        final List<String> dirs = DCONF_CLIENT.listDirs((this.absolutePath().equals("/") ? "" : "/") + "/");
        for (int i = 0; i < dirs.size(); i += 1) {
            final String dirName = dirs.get(i);
            dirs.set(i, dirName.substring(0, dirName.length() - 1));
        }
        return dirs.toArray(new String[dirs.size()]);
    }

    @Override
    protected AbstractPreferences childSpi(final String name) {
        if (name.contains(".")) {
            throw new IllegalArgumentException("Name must not contain dots (\".\").");
        }
        if (name.endsWith("/")) {
            throw new IllegalArgumentException("name must not end with a slash (\"/\").");
        }
        return new DConfPreferences(this, name);
    }

    @Override
    protected void syncSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    protected void flushSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

}
