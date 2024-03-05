package io.finazon;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.OAuth2Credentials;

public class FinazonApiCredentials extends OAuth2Credentials {
    public FinazonApiCredentials(String apiToken) {
        super(new AccessToken(apiToken, null));
    }
}
