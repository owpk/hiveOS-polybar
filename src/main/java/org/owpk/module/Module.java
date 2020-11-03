package org.owpk.module;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.owpk.entities.UserDetails;

import java.io.IOException;

public interface Module {
    void authRequest(UserDetails userDetails) throws IOException, UnirestException;
    void walletRequest() throws UnirestException;
}
