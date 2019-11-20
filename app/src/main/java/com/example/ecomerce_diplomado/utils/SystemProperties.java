package com.example.ecomerce_diplomado.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public final class SystemProperties {

    private static final String URL = "http://ec2-3-86-40-181.compute-1.amazonaws.com";
    private static final int PORT = 6789;


    private SystemProperties() {
    }

    public static String getResource(final String resource) {
        return String.format("%s:%s/%s", URL, PORT, resource);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static CharSequence[] getOptions() {
        final CharSequence[] options = new CharSequence[PhotoOptions.values().length];
        final AtomicInteger atomic = new AtomicInteger(0);

        for (PhotoOptions obj : Arrays.asList(PhotoOptions.values())) {
            options[atomic.getAndIncrement()] = obj.getValue();
        }

        return options;
    }
}
