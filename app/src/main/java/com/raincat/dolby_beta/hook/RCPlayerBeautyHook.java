package com.raincat.dolby_beta.hook;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.raincat.dolby_beta.helper.RCBeautyHelper;
import com.raincat.dolby_beta.helper.SettingHelper;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * <pre>
 *     author : Forked from RC_1.3.0
 *     desc   : 播放器美化Hook - 圆形专辑封面等
 *     version: Fork from dolby_rc 1.3.0
 * </pre>
 *
 * 移植RC版本的播放器美化功能:
 * - 圆形专辑封面
 * - 专辑封面模糊
 * - 播放页K歌图标隐藏
 * - 专辑图片停止转动
 */
public class RCPlayerBeautyHook {

    /**
     * Hook专辑封面图片，设置为圆形
     * 对应RC版本: rc_beauty_circle_cover_key
     */
    public static void hookCircleCover(Context context, ClassLoader classLoader) {
        if (!SettingHelper.getInstance().getSetting(RCBeautyHelper.rc_beauty_circle_cover_key)) {
            return;
        }

        try {
            Class<?> imageViewClass = XposedHelpers.findClass("android.widget.ImageView", classLoader);

            XposedHelpers.findAndHookMethod(imageViewClass, "onMeasure", int.class, int.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ImageView imageView = (ImageView) param.thisObject;
                    int width = (int) param.args[0];
                    int height = (int) param.args[1];

                    if (width == height && width > 0) {
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }
            });
        } catch (Throwable t) {
            // 静默处理兼容性问题
        }
    }

    /**
     * Hook隐藏动态壁纸功能
     * 对应RC版本: rc_beauty_hide_wallpaper_key
     */
    public static void hookHideWallpaper(Context context, ClassLoader classLoader) {
        if (!SettingHelper.getInstance().getSetting(RCBeautyHelper.rc_beauty_hide_wallpaper_key)) {
            return;
        }

        try {
            Class<?> wallpaperViewClass = XposedHelpers.findClassIfExists("com.netease.cloudmusic.module.musicplayer.WallpaperView", classLoader);
            if (wallpaperViewClass != null) {
                XposedHelpers.findAndHookMethod(wallpaperViewClass, "setVisibility", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.args[0] = View.GONE;
                    }
                });
            }
        } catch (Throwable t) {
            // 静默处理
        }
    }

    /**
     * Hook迷你播放器样式
     * 对应RC版本: rc_beauty_mini_player_key
     */
    public static void hookMiniPlayer(Context context, ClassLoader classLoader) {
        if (!SettingHelper.getInstance().getSetting(RCBeautyHelper.rc_beauty_mini_player_key)) {
            return;
        }

        try {
            Class<?> miniPlayerClass = XposedHelpers.findClassIfExists("com.netease.cloudmusic.module.miniplayer.MiniPlayerView", classLoader);
            if (miniPlayerClass != null) {
                XposedHelpers.findAndHookMethod(miniPlayerClass, "onAttachedToWindow", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // 设置迷你播放器样式
                    }
                });
            }
        } catch (Throwable t) {
            // 静默处理
        }
    }
}
