package org.gnome.dconf;

import org.bridj.BridJ;
import org.bridj.CRuntime;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.*;
import org.bridj.ann.Runtime;

/**
 * @author Benjamin P. Jung
 */
@Library("dconf")
@Runtime(CRuntime.class)
public class DConf {

    static {
        BridJ.register(DConf.class);
    }

    @Name("dconf_client_new")
    public static native Pointer<Client> clientNew();

    @Name("DConfClient")
    public static final class Client extends StructObject {
        public Client() {
            super();
        }
        public Client(Pointer<Client> peer) {
            super(peer);
        }
    }

}
