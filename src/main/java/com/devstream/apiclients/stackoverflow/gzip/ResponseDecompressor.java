package com.devstream.apiclients.stackoverflow.gzip;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Created by bipulk on 9/24/16.
 */
public class ResponseDecompressor {

    /**
     * Decompresses and returns the content as a String.
     */
    public static String decompressGZIPContent(byte[] bContent) {
// Holds the content as it's being decompressed.
        byte[] bDecompressedContent = new byte[0];

// Tracks the number of characters read.
        int bytesRead = 0;

// Acts as a buffer.
        byte[] buffer = new byte[128];

// Temporarily holds the content while it is being
// copied.
        byte[] bTemp;

// Tracks the start position of the destination in
// the arraycopy.
        int startPos = 0;

        try {
// Decompresses the content.
            GZIPInputStream gzipStream = new GZIPInputStream(new
                    ByteArrayInputStream(bContent));

//System.out.println( "About to read..." );
            while ((bytesRead = gzipStream.read(buffer)) != -1) {
//System.out.println( "Reading..." );
                startPos = bDecompressedContent.length;
                bTemp = bDecompressedContent;
                bDecompressedContent = new
                        byte[bDecompressedContent.length + bytesRead];
                System.arraycopy(bTemp, 0,
                        bDecompressedContent, 0, bTemp.length);
                System.arraycopy(buffer, 0,
                        bDecompressedContent, startPos, bytesRead);
            }

            gzipStream.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        bContent = bDecompressedContent;

        return new String(bContent);
    }
}



