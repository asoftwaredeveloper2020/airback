package com.airback.configuration;

import com.airback.spring.AppContextUtil;

import java.util.Locale;

import static com.airback.configuration.ApplicationProperties.*;

/**
 * Utility class read airback system properties when system starts
 *
 * @author airback Ltd.
 * @since 1.0
 */
public class SiteConfiguration {

    private static SiteConfiguration instance;

    private Locale defaultLocale;
    private String endecryptPassword;

    public static void loadConfiguration() {
        ApplicationProperties.loadProps();
        instance = new SiteConfiguration();

        String propLocale = ApplicationProperties.getString(DEFAULT_LOCALE, "en_US");
        try {
            instance.defaultLocale = Locale.forLanguageTag(propLocale);
        } catch (Exception e) {
            instance.defaultLocale = Locale.US;
        }

        instance.endecryptPassword = ApplicationProperties.getString(BI_ENDECRYPT_PASSWORD, "airback123");
    }

    private static SiteConfiguration getInstance() {
        if (instance == null) {
            loadConfiguration();
        }
        return instance;
    }

    public static String getSiteUrl(String subDomain) {
        IDeploymentMode modeService = AppContextUtil.getSpringBean(IDeploymentMode.class);
        return modeService.getSiteUrl(subDomain);
    }

    public static boolean isDemandEdition() {
        IDeploymentMode modeService = AppContextUtil.getSpringBean(IDeploymentMode.class);
        return modeService.isDemandEdition();
    }

    public static boolean isCommunityEdition() {
        IDeploymentMode modeService = AppContextUtil.getSpringBean(IDeploymentMode.class);
        return modeService.isCommunityEdition();
    }

    public static Locale getDefaultLocale() {
        return Locale.US;
    }

    public static String getEnDecryptPassword() {
        return getInstance().endecryptPassword;
    }
}