/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package org.yaml.snakeyaml;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Util {

    public static String getLocalResource(String theName) {
        try {
            InputStream input;
            input = YamlDocument.class.getClassLoader().getResourceAsStream(theName);
            if (input == null) {
                throw new RuntimeException("Can not find " + theName);
            }
            BufferedInputStream is = new BufferedInputStream(input);
            StringBuffer buf = new StringBuffer(3000);
            int i;
            try {
                while ((i = is.read()) != -1) {
                    buf.append((char) i);
                }
            } finally {
                is.close();
            }
            String resource = buf.toString();
            // convert EOLs
            String[] lines = resource.split("\\r?\\n");
            StringBuffer buffer = new StringBuffer();
            for (int j = 0; j < lines.length; j++) {
                buffer.append(lines[j]);
                buffer.append("\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
