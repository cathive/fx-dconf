package org.gnome.dconf.prefs;

import org.gnome.dconf.Client;

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
    protected DConfPreferences(AbstractPreferences parent, String name) {
        super(parent, name);

    }

    @Override
    protected void putSpi(String key, String value) {
        if (key.contains("/") || key.contains(".")) {
            throw new IllegalArgumentException("Key must not contains slashes ('/') or dots ('.').");
        }
        DCONF_CLIENT.writeString(this.absolutePath() + "/" + key, value);
    }

    @Override
    protected String getSpi(String key) {
        if (key.contains("/") || key.contains(".")) {
            throw new IllegalArgumentException("Key must not contains slashes ('/') or dots ('.').");
        }
        return DCONF_CLIENT.readString(this.absolutePath() + "/" + key);
    }

    @Override
    protected void removeSpi(String key) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    protected void removeNodeSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    protected String[] keysSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    protected String[] childrenNamesSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    protected AbstractPreferences childSpi(String name) {
        if (name.contains(".")) {
            throw new IllegalArgumentException("Name must not contain dots ('.').");
        }
        if (name.endsWith("/")) {
            throw new IllegalArgumentException("name must not end with a slash ('/').");
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
