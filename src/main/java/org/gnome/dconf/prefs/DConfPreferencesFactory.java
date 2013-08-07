package org.gnome.dconf.prefs;

import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

/**
 * @author Benjamin P. Jung
 */
public class DConfPreferencesFactory implements PreferencesFactory {

    @Override
    public Preferences systemRoot() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Preferences userRoot() {
        return new DConfPreferences(null, "");
    }

}
