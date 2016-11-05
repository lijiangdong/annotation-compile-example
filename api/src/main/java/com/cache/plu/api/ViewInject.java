package com.cache.plu.api;

import android.app.Activity;

import com.example.ViewInjector;

/**
 * Created by lijiangdong on 2016/10/31.
 */
public class ViewInject {
    public static void inject(Activity activity){

        Class clazz = activity.getClass();
        try {
            Class proxyClazz = Class.forName(clazz.getName() + "$$ViewInjector");
            ViewInjector viewInjector = (ViewInjector) proxyClazz.newInstance();
            viewInjector.inject(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
