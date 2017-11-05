package com.nhimcoi.yuh.chopsticks;

import com.nhimcoi.yuh.chopsticks.Model.FoodModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabu on 10/13/2017.
 */

public class DatMonUtils {

    private static final List<FoodModel> mListMonDuocDat = new ArrayList<>();

    public static void addMonAn(FoodModel food) {
        if (!mListMonDuocDat.contains(food)) {
            mListMonDuocDat.add(food);
        }
    }

    public static void removeMonAn(FoodModel food) {
        if (mListMonDuocDat.contains(food)) {
            mListMonDuocDat.remove(food);
        }
    }
    public static List<FoodModel> getListMonAnDuocDat() {
        return mListMonDuocDat;
    }
}
