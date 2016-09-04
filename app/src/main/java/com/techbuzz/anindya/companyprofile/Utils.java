package com.techbuzz.anindya.companyprofile;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static String jsonToStringFromAssetFolder(String fileName,
                                                     Context context) {

        String jsonString = null;

        try {
            AssetManager manager = context.getAssets();
            InputStream file = manager.open(fileName);

            byte[] data = new byte[file.available()];
            file.read(data);
            file.close();
            jsonString = new String(data);
        } catch (IOException ie) {

        }
        return jsonString;
    }
}
