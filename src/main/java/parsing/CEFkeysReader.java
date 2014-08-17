package parsing;

import com.google.common.base.Preconditions;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gaurav Kumar <gkumar@ciphercloud.com> on 8/17/2014.
 */
public class CEFkeysReader {

    private static Set cefKeys;

    /**
     *
     * @return Set of CEF keys found in CEF_keys.txt
     * @throws IOException
     */
    public static Set getCefKeys() throws IOException {

        if (cefKeys == null) {
            cefKeys = new HashSet<>(150);
            InputStream fileInputStream = Thread.currentThread().getClass().getResourceAsStream("/CEF_keys.txt");
            Preconditions.checkNotNull(fileInputStream, "Can't open CEF keys file (CEF_keys.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
            String line = bufferedReader.readLine();
            while (line != null) {
                cefKeys.add(line);
                line = bufferedReader.readLine();
            }
        }
        return cefKeys;

    }


}
