package com.example.android.andela;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;

import java.util.ArrayList;

/**
 * Created by UTYLO on 4/25/2017.
 */
public class CustomTabsIntent {
    public static final String EXTRA_SESSION = "android.support.customtabs.extra.SESSION";
    public static final String EXTRA_TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";
    public static final String EXTRA_ENABLE_URLBAR_HIDING = "android.support.customtabs.extra.ENABLE_URLBAR_HIDING";
    public static final String EXTRA_CLOSE_BUTTON_ICON = "android.support.customtabs.extra.CLOSE_BUTTON_ICON";
    public static final String EXTRA_TITLE_VISIBILITY_STATE = "android.support.customtabs.extra.TITLE_VISIBILITY";
    public static final int NO_TITLE = 0;
    public static final int SHOW_PAGE_TITLE = 1;
    public static final String EXTRA_ACTION_BUTTON_BUNDLE = "android.support.customtabs.extra.ACTION_BUTTON_BUNDLE";
    public static final String KEY_ICON = "android.support.customtabs.customaction.ICON";
    public static final String KEY_DESCRIPTION = "android.support.customtabs.customaction.DESCRIPTION";
    public static final String KEY_PENDING_INTENT = "android.support.customtabs.customaction.PENDING_INTENT";
    public static final String EXTRA_TINT_ACTION_BUTTON = "android.support.customtabs.extra.TINT_ACTION_BUTTON";
    public static final String EXTRA_MENU_ITEMS = "android.support.customtabs.extra.MENU_ITEMS";
    public static final String KEY_MENU_ITEM_TITLE = "android.support.customtabs.customaction.MENU_ITEM_TITLE";
    public static final String EXTRA_EXIT_ANIMATION_BUNDLE = "android.support.customtabs.extra.EXIT_ANIMATION_BUNDLE";
    @NonNull
    public final Intent intent;
    @Nullable
    public final Bundle startAnimationBundle;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void launchUrl(Activity context, Uri url) {
        this.intent.setData(url);
        if(this.startAnimationBundle != null) {
            context.startActivity(this.intent, this.startAnimationBundle);
        } else {
            context.startActivity(this.intent);
        }

    }

    private CustomTabsIntent(Intent intent, Bundle startAnimationBundle) {
        this.intent = intent;
        this.startAnimationBundle = startAnimationBundle;
    }

    public static final class Builder {
        private final Intent mIntent;
        private ArrayList<Bundle> mMenuItems;
        private Bundle mStartAnimationBundle;

        public Builder() {
            this((CustomTabsSession)null);
        }

        public Builder(@Nullable CustomTabsSession session) {
            this.mIntent = new Intent("android.intent.action.VIEW");
            this.mMenuItems = null;
            this.mStartAnimationBundle = null;
            if(session != null) {
                this.mIntent.setPackage(session.getComponentName().getPackageName());
            }

            Bundle bundle = new Bundle();
            BundleCompat.putBinder(bundle, "android.support.customtabs.extra.SESSION", session == null?null:session.getBinder());
            this.mIntent.putExtras(bundle);
        }

        public CustomTabsIntent.Builder setToolbarColor(@ColorInt int color) {
            this.mIntent.putExtra("android.support.customtabs.extra.TOOLBAR_COLOR", color);
            return this;
        }

        public CustomTabsIntent.Builder enableUrlBarHiding() {
            this.mIntent.putExtra("android.support.customtabs.extra.ENABLE_URLBAR_HIDING", true);
            return this;
        }

        public CustomTabsIntent.Builder setCloseButtonIcon(@NonNull Bitmap icon) {
            this.mIntent.putExtra("android.support.customtabs.extra.CLOSE_BUTTON_ICON", icon);
            return this;
        }

        public CustomTabsIntent.Builder setShowTitle(boolean showTitle) {
            this.mIntent.putExtra("android.support.customtabs.extra.TITLE_VISIBILITY", showTitle?1:0);
            return this;
        }

        public CustomTabsIntent.Builder addMenuItem(@NonNull String label, @NonNull PendingIntent pendingIntent) {
            if(this.mMenuItems == null) {
                this.mMenuItems = new ArrayList();
            }

            Bundle bundle = new Bundle();
            bundle.putString("android.support.customtabs.customaction.MENU_ITEM_TITLE", label);
            bundle.putParcelable("android.support.customtabs.customaction.PENDING_INTENT", pendingIntent);
            this.mMenuItems.add(bundle);
            return this;
        }

        public CustomTabsIntent.Builder setActionButton(@NonNull Bitmap icon, @NonNull String description, @NonNull PendingIntent pendingIntent, boolean shouldTint) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("android.support.customtabs.customaction.ICON", icon);
            bundle.putString("android.support.customtabs.customaction.DESCRIPTION", description);
            bundle.putParcelable("android.support.customtabs.customaction.PENDING_INTENT", pendingIntent);
            this.mIntent.putExtra("android.support.customtabs.extra.ACTION_BUTTON_BUNDLE", bundle);
            this.mIntent.putExtra("android.support.customtabs.extra.TINT_ACTION_BUTTON", shouldTint);
            return this;
        }

        public CustomTabsIntent.Builder setActionButton(@NonNull Bitmap icon, @NonNull String description, @NonNull PendingIntent pendingIntent) {
            return this.setActionButton(icon, description, pendingIntent, false);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public CustomTabsIntent.Builder setStartAnimations(@NonNull Context context, @AnimRes int enterResId, @AnimRes int exitResId) {
            this.mStartAnimationBundle = ActivityOptions.makeCustomAnimation(context, enterResId, exitResId).toBundle();
            return this;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public CustomTabsIntent.Builder setExitAnimations(@NonNull Context context, @AnimRes int enterResId, @AnimRes int exitResId) {
            Bundle bundle = ActivityOptions.makeCustomAnimation(context, enterResId, exitResId).toBundle();
            this.mIntent.putExtra("android.support.customtabs.extra.EXIT_ANIMATION_BUNDLE", bundle);
            return this;
        }

        public CustomTabsIntent build() {
            if(this.mMenuItems != null) {
                this.mIntent.putParcelableArrayListExtra("android.support.customtabs.extra.MENU_ITEMS", this.mMenuItems);
            }

            return new CustomTabsIntent(this.mIntent, this.mStartAnimationBundle);
        }
    }
}


