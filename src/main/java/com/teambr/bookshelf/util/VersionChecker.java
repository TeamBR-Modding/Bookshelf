package com.teambr.bookshelf.util;

import com.teambr.bookshelf.collections.VersionReturn;
import com.teambr.bookshelf.helpers.LogHelper;
import cpw.mods.fml.common.Loader;
import net.minecraft.util.StatCollector;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class VersionChecker implements Runnable {

    // The (publicly available) remote version number authority file
    private static String REMOTE_VERSION_XML_FILE;
    private static String lastVersion;
    private static int numRetry;
    private static VersionReturn results;
    private static String modID;

    static Properties remoteVersionProperties = new Properties();

    // All possible results of the remote version number check
    public enum Results {
        UNINITIALIZED,
        CURRENT,
        OUTDATED,
        ERROR,
        FINAL_ERROR,
        MC_VERSION_NOT_FOUND
    }

    // Var to hold the result of the remote version check, initially set to uninitialized
    static Results result = Results.UNINITIALIZED;
    static String remoteVersion = null;
    static String remoteUpdateLocation = null;

    public VersionChecker(String xml, String lastVersionIn, int numRetryIn, String modIDIn) {
        REMOTE_VERSION_XML_FILE = xml;
        lastVersion = lastVersionIn;
        numRetry = numRetryIn;
        results = new VersionReturn(null, null, null);
        modID = modIDIn;
    }

    public static void checkVersion() {
        InputStream remoteVersionRepoStream = null;
        result = Results.UNINITIALIZED;

        try {
            URL remoteVersionURL = new URL(REMOTE_VERSION_XML_FILE);
            remoteVersionRepoStream = remoteVersionURL.openStream();
            remoteVersionProperties.loadFromXML(remoteVersionRepoStream);

            String remoteVersionProperty = remoteVersionProperties.getProperty(Loader.instance().getMCVersionString());

            if (remoteVersionProperty != null) {
                String[] remoteVersionTokens = remoteVersionProperty.split("\\|");

                if (remoteVersionTokens.length >= 2) {
                    remoteVersion = remoteVersionTokens[0];
                    remoteUpdateLocation = remoteVersionTokens[1];
                }
                else {
                    result = Results.ERROR;
                }

                if (remoteVersion != null) {
                    if (remoteVersion.equalsIgnoreCase(lastVersion))
                    {
                        result = Results.CURRENT;
                    }
                    else {
                        result = Results.OUTDATED;
                    }

                    /*if (!lastVersion.equalsIgnoreCase(remoteVersion)) {
                        GeneralConfigRegistry.set(Reference.VERSIONCHECK, Reference.REMOTE_VERSION, remoteVersion);
                        if (GeneralConfigRegistry.versionNotify < 2)
                            GeneralConfigRegistry.set(Reference.VERSIONCHECK, Reference.UPDATE_TAB, false);

                    }*/
                }

            }
            else {
                result = Results.MC_VERSION_NOT_FOUND;
            }
            results = new VersionReturn(lastVersion, remoteVersion, result.name());
        }
        catch (Exception ignored) {
        }
        finally {
            if (result == Results.UNINITIALIZED) {
                result = Results.ERROR;
            }

            try {
                if (remoteVersionRepoStream != null) {
                    remoteVersionRepoStream.close();
                }
            }
            catch (Exception ignored) {
            }
        }
    }

    @Override
    public void run() {
        int tries = 0;
        LogHelper.info(StatCollector.translateToLocal(modID + ".versioncheck.start"));
        try {
            while (tries < numRetry) {
                checkVersion();
                tries++;
                if (result == Results.ERROR || result == Results.UNINITIALIZED) {
                    Thread.sleep(10000);
                }
            }
            if (result == Results.ERROR) {
                result = Results.FINAL_ERROR;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (result) {
            case CURRENT:
                LogHelper.info(StatCollector.translateToLocal(modID + ".versioncheck.current"));
                break;
            case OUTDATED:
                LogHelper.warning(StatCollector.translateToLocal(modID + ".versioncheck.outdated"));
                break;
            case MC_VERSION_NOT_FOUND:
                LogHelper.warning(StatCollector.translateToLocal(modID + ".versioncheck.mcversion"));
                break;
            default:
                LogHelper.severe(StatCollector.translateToLocal(modID + ".versioncheck.error"));
        }
    }

    /*public void execute() {
        new Thread(this).start();
        return results;
    }*/

    public static VersionReturn getResults() {
        return results;
    }
}
