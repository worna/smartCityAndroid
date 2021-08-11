package com.worna.sportoo.utils.errors;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No Internet Connection Exception";
    }
}
