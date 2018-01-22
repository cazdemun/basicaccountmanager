package com.example.cleonurb.basicsecondauth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * A service is required for the Authenticator class to exist, although the code is just boilerplate
 * This service must be declared in the manifest, along with the metadata specified in another xml file
 */

public class BasicAuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        BasicAuthenticator authenticator = new BasicAuthenticator(this);
        return authenticator.getIBinder();
    }
}
