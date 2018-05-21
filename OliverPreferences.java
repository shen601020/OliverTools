package com.hundsun.tool.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.TextUtils;

import com.hundsun.tool.encrypt.SecurityUtils;
import com.hundsun.tool.encrypt.YRAESUtils;
import com.hundsun.tool.encrypt.YRMD5Utils;
import com.hundsun.tool.encrypt.annotation.SecurityEncrypt;
import com.hundsun.tool.encrypt.annotation.SecurityType;
import com.hundsun.tool.utils.YRDataUtils;

import java.util.Map;

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

public class OliverPreferences {
    public final static String PREF_DEFAULT_NAME = "default_pref";

    private SharedPreferences.Editor mEditor;

    private Context mContext;
    private String mGroup = PREF_DEFAULT_NAME;
    private int mMode = Context.MODE_PRIVATE;


    public OliverPreferences(Context context, String name, int mode) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
        if (!TextUtils.isEmpty(name)) {
            mGroup = name;
        }
        mMode = mode;
    }

    public void init() {
        if (mContext != null && mEditor == null) {
            mEditor = mContext.getSharedPreferences(mGroup, mMode).edit();
        }
    }

    private void checkKey(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key must be not null");
        }
    }


    public void putSharedPref(String key, Object value) {
        if (mEditor == null) {
            return;
        }
        if (value instanceof String) {
            mEditor.putString(securityKey(key), securityValue((String) value));
        }
        if (value instanceof Boolean) {
            mEditor.putBoolean(securityKey(key), (Boolean) value);
        }
        if (value instanceof Long) {
            mEditor.putLong(securityKey(key), (Long) value);
        }
        if (value instanceof Float) {
            mEditor.putFloat(securityKey(key), (Float) value);
        }

    }



    private boolean contains(String name, String key) {
        if (mContext != null) {
            return mContext.getSharedPreferences(name, mMode).contains(key);
        }
        return false;
    }

    private boolean getBoolean(String name, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (mContext != null) {
            value = mContext.getSharedPreferences(name, mMode).getBoolean(securityKey(key), defaultValue);
        }
        return value;
    }

    private float getFloat(String name, String key, float defaultValue) {
        float f = defaultValue;
        if (mContext != null) {
            f = mContext.getSharedPreferences(name, mMode).getFloat(securityKey(key), defaultValue);
        }
        return f;
    }

    private int getInt(String name, String key, int defaultValue) {
        int i = defaultValue;
        if (mContext != null) {
            i = mContext.getSharedPreferences(name, mMode).getInt(securityKey(key), defaultValue);
        }
        return i;
    }

    private long getLong(String name, String key, long defaultValue) {
        long l = defaultValue;
        if (mContext != null) {
            l = mContext.getSharedPreferences(name, mMode).getLong(securityKey(key), defaultValue);
        }
        return l;
    }

    private String getString(String name, String key, String defaultValue) {
        if (mContext != null) {
            String str = mContext.getSharedPreferences(name, mMode).getString(securityKey(key), YRDataUtils.EMPTY);
            if (!TextUtils.isEmpty(str)) {
                str = decryptValue(str);
            } else {
                str = defaultValue;
            }
            return str;

        }
        return defaultValue;
    }


    public boolean clear() {
        if (mEditor != null) {
            mEditor.clear();
            return true;
        }
        return false;
    }

    public boolean commit() {
        if (mEditor != null) {
            return mEditor.commit();
        }
        return false;
    }

    public boolean contains(String key) {
        return contains(mGroup, key);
    }

    public Map<String, ?> getAll() {
        if (mContext != null) {
            return mContext.getSharedPreferences(mGroup, this.mMode).getAll();
        }
        return null;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(mGroup, key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return getFloat(mGroup, key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return getInt(mGroup, key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return getLong(mGroup, key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return getString(mGroup, key, defaultValue);
    }


    public boolean remove(String key) {
        if ((mEditor != null) && (!TextUtils.isEmpty(key))) {
            mEditor.remove(key);
            return true;
        }
        return false;
    }

    private String securityKey(String key) {
        checkKey(key);
        return YRMD5Utils.getMD5String(key);
    }

    private String securityValue(String value) {
        if (TextUtils.isEmpty(value)) return value;
        return YRAESUtils.encrypt(value, getCryptoKey());
    }

    private String decryptValue(String value) {
        if (TextUtils.isEmpty(value)) return value;
        return YRAESUtils.decrypt(value, getCryptoKey());
    }

    private String getCryptoKey() {
        String key = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        return TextUtils.isEmpty(key) ? "000011112222" : key;
    }


}
