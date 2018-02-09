package org.lds.ldssa.util.serializer;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import pocketknife.PocketKnifeBundleSerializer;
import pocketknife.PocketKnifeIntentSerializer;

public class ListLongParceller implements PocketKnifeBundleSerializer<List<Long>>, PocketKnifeIntentSerializer<List<Long>> {
    @Override
    public void put(Bundle bundle, List<Long> target, String keyPrefix) {
        long[] array = new long[target.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = target.get(i);
        }

        bundle.putLongArray(keyPrefix, array);
    }

    @Override
    public List<Long> get(Bundle bundle, List<Long> target, String keyPrefix) {
        long[] array = bundle.getLongArray(keyPrefix);
        if (array == null) {
            return new ArrayList<>();
        }
        target = new ArrayList<>(array.length);
        for (long l : array) {
            target.add(l);
        }
        return target;
    }

    @Override
    public void put(Intent intent, List<Long> target, String keyPrefix) {
        long[] array = new long[target.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = target.get(i);
        }

        intent.putExtra(keyPrefix, array);
    }

    @Override
    public List<Long> get(Intent intent, List<Long> target, String keyPrefix) {
        long[] array = intent.getLongArrayExtra(keyPrefix);
        if (array == null) {
            return new ArrayList<>();
        }
        target = new ArrayList<>(array.length);
        for (long l : array) {
            target.add(l);
        }
        return target;
    }
}
