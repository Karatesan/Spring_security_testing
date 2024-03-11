package com.karatesan.BasicSpringSecurity.config;

public interface SecurityConstants {

    //to powinno byc dodane przez devopsa podczas konfigurowania srodowiska przy deployowaniu apki, jako np. enviroment_variable
    /*
    Here's an example of using environment variables in Java:

        public class AppConfig {
    public static final String JWT_KEY = System.getenv("MY_JWT_KEY");
}

Then, you would set the environment variable MY_JWT_KEY on your server or in your deployment environment.
    */
    public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ54YUn4";
    public static final String JWT_HEADER = "Authorization";
}
