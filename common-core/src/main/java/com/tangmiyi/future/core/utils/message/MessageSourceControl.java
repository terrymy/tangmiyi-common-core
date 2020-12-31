package com.tangmiyi.future.core.utils.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class MessageSourceControl extends ResourceBundle.Control {

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException {

        String properties = "java.properties";
        if (properties.equals(format)) {
            String bundleName = toBundleName(baseName, locale);
            final String resourceName = toResourceName(bundleName, "properties");
            final ClassLoader classLoader = loader;
            final boolean reloadFlag = reload;
            InputStream stream;
            try {
                stream = AccessController.doPrivileged(
                        new PrivilegedExceptionAction<InputStream>() {
                            @Override
                            public InputStream run() throws IOException {
                                InputStream is = null;
                                if (reloadFlag) {
                                    URL url = classLoader.getResource(resourceName);
                                    if (url != null) {
                                        URLConnection connection = url.openConnection();
                                        if (connection != null) {
                                            connection.setUseCaches(false);
                                            is = connection.getInputStream();
                                        }
                                    }
                                }
                                else {
                                    is = classLoader.getResourceAsStream(resourceName);
                                }
                                return is;
                            }
                        });
            }
            catch (PrivilegedActionException ex) {
                throw (IOException) ex.getException();
            }
            if (stream != null) {
                try {
                    return  new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
                }
                finally {
                    stream.close();
                }
            }
            else {
                return null;
            }
        }
        else {
            return super.newBundle(baseName, locale, format, loader, reload);
        }
    }
}
