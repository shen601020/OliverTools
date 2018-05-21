package com.hundsun.tool.sp;

import android.content.Context;

/**
 * @category:{基类}
 * @description: {sp持久化基类}
 * @author: hejun.shen
 * @email: hejun.shen@huijie-inc.com
 * @date : 2018/3/14
 * @version:1.0.1
 * @modifyAuthor:
 * @modifyDate:
 * @modifyVersion:
 */

public class BasePref {

    protected OliverPreferences mPref;

    public BasePref(Context context,String predsName){
        mPref= OliverPreferencesManager.getInstance(context,predsName);
    }
}
