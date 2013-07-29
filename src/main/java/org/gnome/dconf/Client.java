package org.gnome.dconf;

import org.bridj.Pointer;

/**
 * @author Benjamin P. Jung
 */
public class Client {

    private final DConf.Client _client;

    private Client(final DConf.Client _client) {
        super();
        this._client = _client;
    }

    public static Client create() {
        final Pointer<DConf.Client> _client = DConf.clientNew();
        return new Client(_client.get());
    }

}
