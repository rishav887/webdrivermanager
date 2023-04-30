/*
 * (C) Copyright 2015 Boni Garcia (http://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia.wdm;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * Manager for Chrome.
 *
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class ChromeDriverManager extends WebDriverManager {

    private static final String CHROME_DRIVER_VERSION_KEY = "wdm.chromeDriverVersion";
    private static final String CHROME_DRIVER_URL_KEY = "wdm.chromeDriverUrl";
    private static final String CHROME_DRIVER_MIRROR_URL_KEY = "wdm.chromeDriverMirrorUrl";
    private static final String CHROME_DRIVER_EXPORT_PARAM_KEY = "wdm.chromeDriverExport";
    private static final List<String> CHROME_DRIVER_NAME = Collections.singletonList("chromedriver");

    public static synchronized WebDriverManager getInstance() {
        return chromedriver();
    }

    public ChromeDriverManager() {
        driverManagerType = DriverManagerType.CHROME;
        exportParameterKey = CHROME_DRIVER_EXPORT_PARAM_KEY;
        driverVersionKey = CHROME_DRIVER_VERSION_KEY;
        driverUrlKey = CHROME_DRIVER_URL_KEY;
        driverMirrorUrlKey = CHROME_DRIVER_MIRROR_URL_KEY;
        driverName = CHROME_DRIVER_NAME;
    }

    @Override
    protected List<URL> getDrivers() throws IOException {
        URL driverUrl = config().getDriverUrl(driverUrlKey);
        if (isUsingTaobaoMirror()) {
            return getDriversFromMirror(driverUrl);
        } else {
            return getDriversFromXml(driverUrl);
        }
    }

    @Override
    protected String getCurrentVersion(URL url, String driverName) {
        if (isUsingTaobaoMirror()) {
            int lastSlashIndex = url.getFile().lastIndexOf('/');
            int secondLastSlashIndex = url.getFile().substring(0, lastSlashIndex).lastIndexOf('/') + 1;
            return url.getFile().substring(secondLastSlashIndex, lastSlashIndex);
        } else {
            return super.getCurrentVersion(url, driverName);
        }
    }

}
