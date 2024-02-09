package com.paymentapi.util;

public interface AppConstant {
    String EXCEPTION_NETWORK = "network";
    String EXCEPTION_DATABASE = "database";
    String EXCEPTION_REST = "rest";

    public class TargetURI {
        public static final String LOG = "/log";
        public static final String PAYMENT = "/payment";

    }
}
