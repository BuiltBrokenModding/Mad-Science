package madscience;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

import madscience.network.CustomConnectionHandler;
import madscience.util.MadXML;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import cpw.mods.fml.common.network.NetworkRegistry;

class MadUpdates
{
    /** Pings a HTTP URL. This effectively sends a HEAD request and returns <code>true</code> if the response code is in the 200-399 range.
     * 
     * @param url The HTTP URL to be pinged.
     * @param timeout The timeout in millis for both the connection timeout and the response read timeout. Note that the total timeout is effectively two times the given timeout.
     * @return <code>true</code> if the given HTTP URL has returned response code 200-399 on a HEAD request within the given timeout, otherwise <code>false</code>. */
    private static boolean ping(String url, int timeout)
    {
        url = url.replaceFirst("https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

        try
        {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        }
        catch (IOException exception)
        {
            return false;
        }
    }

    static void checkJenkinsBuildNumbers()
    {
        // Determine if we should check for updates by comparing build numbers in our Jenkins build server.
        if (MadConfig.UPDATE_CHECKER)
        {
            // Attempt to open a connection to the URL and see if it is active.
            boolean reachable = false;
            try
            {
                reachable = ping(MadConfig.UPDATE_URL, 500);
            }
            catch (Exception err)
            {
                MadScience.logger.info("Unable to connect to Mad Science Jenkins build server for update information. Skipping...");
                return;
            }

            try
            {
                // Abort the rest of this process if we cannot even reach the site.
                if (reachable)
                {
                    // Look for XML response from server for update information.
                    Document docXML = MadXML.loadXMLFromString(MadConfig.UPDATE_URL);
                    Node child = docXML.getFirstChild();
                    String xmlBuildNumber = child.getTextContent();
                    long myXMLLong = new Long(xmlBuildNumber);

                    // Register a custom connection handler so we can tell the user something when the login to the game world.
                    MadScience.logger.info("Mad Science Jenkins Build Server Last Stable Build: " + String.valueOf(myXMLLong));
                    NetworkRegistry.instance().registerConnectionHandler(new CustomConnectionHandler(myXMLLong));
                }
                else
                {
                    MadScience.logger.info("Unable to connect to Mad Science Jenkins build server for update information. Skipping...");
                    return;
                }
            }
            catch (Exception err)
            {
                MadScience.logger.info("Unable to parse XML from Jenkins build server... perhaps it is down!");
                return;
            }
        }
    }
}
