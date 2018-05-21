package com.hundsun.tool.sp;

import android.content.Context;
import android.text.TextUtils;
import android.util.LruCache;

/**
 * @version V1.0
 * @Title: workspace
 * @Description: TODO(用一句话描述该文件做什么)
 * @date 17/9/20
 * @author:hejun.shen
 * @email:shenhj15971@hundsun.com
 * @replace author:
 * @replace date:
 */

public class OliverPreferencesManager {
    private static LruCache<String, OliverPreferences> spList = new LruCache<>(10);

    private OliverPreferencesManager() {

    }

    public static OliverPreferences getInstance(Context context) {
        return getInstance(context, OliverPreferences.PREF_DEFAULT_NAME, 0);
    }

    public static OliverPreferences getInstance(Context context, String name) {
        return getInstance(context, name, 0);
    }

    public static OliverPreferences getInstance(Context context, String name, int mode) {
        if ((context == null) || (TextUtils.isEmpty(name))) {
            return null;
        }
        OliverPreferences pref = spList.get(name);

        if (pref == null) {
            pref = new OliverPreferences(context, name, mode);
            spList.put(name, pref);
        }

        pref.init();
        return pref;
    }

}
