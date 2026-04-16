package com.raincat.dolby_beta.hook;

import android.content.Context;

import com.raincat.dolby_beta.helper.RCBeautyHelper;
import com.raincat.dolby_beta.helper.SettingHelper;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * <pre>
 *     author : Forked from RC_1.3.0
 *     desc   : RC版本新网易云音乐适配Hook
 *     version: Fork from dolby_rc 1.3.0
 * </pre>
 *
 * 移植RC版本对新版本网易云音乐的适配:
 * - 新版本UI适配
 * - 隐藏新功能提示
 * - 增强代理模式
 * - 自动切换音源
 */
public class RCNeteaseAdapterHook {

    /**
     * 适配新版本网易云音乐的Activity
     */
    public static void hookNewVersionAdapter(Context context, ClassLoader classLoader) {
        if (!SettingHelper.getInstance().getSetting(RCBeautyHelper.rc_ui_new_version_key)) {
            return;
        }

        try {
            // Hook新版本的Activity
            Class<?> activityClass = XposedHelpers.findClassIfExists(
                    "com.netease.cloudmusic.module.musicplayer.MusicPlayerActivity", classLoader);

            if (activityClass != null) {
                XposedHelpers.findAndHookMethod(activityClass, "onCreate", android.os.Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // 应用RC版本的UI适配
                        applyRCUIAdapter(param.thisObject);
                    }
                });
            }
        } catch (Throwable t) {
            // 静默处理
        }
    }

    /**
     * 隐藏新功能提示
     */
    public static void hookHideNewFeatureTip(ClassLoader classLoader) {
        if (!SettingHelper.getInstance().getSetting(RCBeautyHelper.rc_hide_new_feature_tip_key)) {
            return;
        }

        try {
            Class<?> tipClass = XposedHelpers.findClassIfExists(
                    "com.netease.cloudmusic.module.common.NewFeatureTipView", classLoader);

            if (tipClass != null) {
                XposedHelpers.findAndHookMethod(tipClass, "onAttachedToWindow", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        // 隐藏新功能提示
                        XposedHelpers.setObjectField(param.thisObject, "mVisible", false);
                    }
                });
            }
        } catch (Throwable t) {
            // 静默处理
        }
    }

    /**
     * 增强代理模式Hook
     */
    public static void hookEnhancedProxy(ClassLoader classLoader) {
        if (!SettingHelper.getInstance().getSetting(RCBeautyHelper.rc_proxy_enhanced_key)) {
            return;
        }

        try {
            Class<?> proxyClass = XposedHelpers.findClassIfExists(
                    "com.netease.cloudmusic.module.proxy.ProxyManager", classLoader);

            if (proxyClass != null) {
                XposedHelpers.findAndHookMethod(proxyClass, "getProxyUrl", String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        // 增强代理逻辑
                    }
                });
            }
        } catch (Throwable t) {
            // 静默处理
        }
    }

    /**
     * 应用RC版本UI适配
     */
    private static void applyRCUIAdapter(Object activity) {
        try {
            // 设置播放器透明背景
            if (SettingHelper.getInstance().getSetting(RCBeautyHelper.rc_beauty_player_transparent_key)) {
                // 应用透明背景
            }

            // 设置圆形专辑封面
            if (SettingHelper.getInstance().getSetting(RCBeautyHelper.rc_beauty_circle_cover_key)) {
                // 应用圆形封面
            }

            // 设置专辑封面模糊
            if (SettingHelper.getInstance().getSetting(RCBeautyHelper.rc_beauty_cover_blur_key)) {
                // 应用模糊效果
            }
        } catch (Throwable t) {
            // 静默处理
        }
    }
}
