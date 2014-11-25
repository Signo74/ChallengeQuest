package com.swinginpenguin.vmarinov.challengequest.db.utils;

import com.swinginpenguin.vmarinov.challengequest.model.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vmarinov on 11/11/2014.
 */
public class DBUtils {

    public DBUtils() {
    }

    public static List<Integer> dbStringToListIntegers(String dbValue) {
        //TODO convert the string representation to a List<>
        return new ArrayList<Integer>();
    }

    public static List<Float> dbStringToListFloats(String dbValue) {
        //TODO convert the string representation to a List<>
        return new ArrayList<Float>();
    }

    public static List<AttributeSet> dbStringToListAttributeSet(String dbValue) {
        //TODO convert the string representation to a List<>
        return new ArrayList<AttributeSet>();
    }

    public static String listToDBString(Object list) {
        String result = "";
        //TODO convert a List<> to a conveniently formatted string that will be inserted in DB.
        //TODO Perhaps to a JSON format?
        return result;
    }
}
