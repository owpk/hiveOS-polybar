package org.owpk.module;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public interface Module {
    void authRequest() throws IOException, UnirestException;
    void walletRequest() throws UnirestException;
}
