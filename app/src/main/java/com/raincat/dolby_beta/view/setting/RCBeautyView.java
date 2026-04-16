package com.raincat.dolby_beta.view.setting;

import android.content.Context;
import android.util.AttributeSet;

import com.raincat.dolby_beta.helper.SettingHelper;
import com.raincat.dolby_beta.view.BaseDialogItem;

/**
 * <pre>
 *     author : Forked from RC_1.3.0
 *     desc   : RC版本增强美化设置页面
 *     version: Fork from dolby_rc 1.3.0
 * </pre>
 *
 * 展示RC版本特有的美化功能:
 * - 播放器透明背景
 * - 圆形专辑封面
 * - 自定义播放器皮肤
 * - 隐藏动态壁纸
 * - 通知栏美化
 * - 迷你播放器样式
 * - 歌词颜色自定义
 * - 播放进度条样式
 * - 专辑封面模糊
 */
public class RCBeautyView extends BaseDialogItem {

    public RCBeautyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RCBeautyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RCBeautyView(Context context) {
        super(context);
    }

    @Override
    public void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        title = "RC增强美化";
        key = "rc_beauty_key";
        setData(false, false);

        setOnClickListener(view -> {
            // 发送广播打开RC增强美化对话框
        });
    }
}
