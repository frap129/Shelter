package net.typeblog.shelter.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import net.typeblog.shelter.ui.CameraProxyActivity;
import net.typeblog.shelter.ui.DummyActivity;

public class SettingsManager {
    private static SettingsManager sInstance = null;

    public static void initialize(Context context) {
        sInstance = new SettingsManager(context);
    }

    public static SettingsManager getInstance() {
        return sInstance;
    }

    private LocalStorageManager mStorage = LocalStorageManager.getInstance();
    private Context mContext;

    private SettingsManager(Context context) {
        mContext = context;
    }

    private void syncSettingsToProfileBool(String name, boolean value) {
        Intent intent = new Intent(DummyActivity.SYNCHRONIZE_PREFERENCE);
        intent.putExtra("name", name);
        intent.putExtra("boolean", value);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utility.transferIntentToProfile(mContext, intent);
        mContext.startActivity(intent);
    }

    private void syncSettingsToProfileInt(String name, int value) {
        Intent intent = new Intent(DummyActivity.SYNCHRONIZE_PREFERENCE);
        intent.putExtra("name", name);
        intent.putExtra("int", value);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utility.transferIntentToProfile(mContext, intent);
        mContext.startActivity(intent);
    }

    // Enforce all settings
    public void applyAll() {
        applyFileShuttle();
        applyCameraProxy();
    }

    // Read and apply the enabled state of the cross profile file chooser
    public void applyFileShuttle() {
        boolean enabled = mStorage.getBoolean(LocalStorageManager.PREF_FILE_SHUTTLE);
        mContext.getPackageManager().setComponentEnabledSetting(
                new ComponentName(mContext, CrossProfileDocumentsProvider.class),
                enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    // Read and apply the enabled state of the camera proxy
    public void applyCameraProxy() {
        boolean enabled = mStorage.getBoolean(LocalStorageManager.PREF_CAMERA_PROXY);
        mContext.getPackageManager().setComponentEnabledSetting(
                new ComponentName(mContext, CameraProxyActivity.class),
                enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    // Set the enabled state of the cross profile file chooser
    public void setFileShuttleEnabled(boolean enabled) {
        mStorage.setBoolean(LocalStorageManager.PREF_FILE_SHUTTLE, enabled);
        getFileShuttleEnabled();
        syncSettingsToProfileBool(LocalStorageManager.PREF_FILE_SHUTTLE, enabled);
    }

    // Get the enabled state of the cross profile file chooser
    public boolean getFileShuttleEnabled() {
        return mStorage.getBoolean(LocalStorageManager.PREF_FILE_SHUTTLE);
    }

    // Set the enabled state of the cross profile file chooser
    public void setCameraProxyEnabled(boolean enabled) {
        mStorage.setBoolean(LocalStorageManager.PREF_CAMERA_PROXY, enabled);
        applyCameraProxy();
        syncSettingsToProfileBool(LocalStorageManager.PREF_CAMERA_PROXY, enabled);
    }

    // Get the enabled state of the cross profile file chooser
    public boolean getCameraProxyEnabled() {
        return mStorage.getBoolean(LocalStorageManager.PREF_CAMERA_PROXY);
    }

    // Set the blocked state of cross-profile contacts searching
    public void setBlockContactsSearchingEnabled(boolean enabled) {
        mStorage.setBoolean(LocalStorageManager.PREF_BLOCK_CONTACTS_SEARCHING, enabled);
        syncSettingsToProfileBool(LocalStorageManager.PREF_BLOCK_CONTACTS_SEARCHING, enabled);
    }

    // Get the blocked state of cross-profile contacts searching
    public boolean getBlockContactsSearchingEnabled() {
        return mStorage.getBoolean(LocalStorageManager.PREF_BLOCK_CONTACTS_SEARCHING);
    }

    // Set the enabled state of the auto freeze service
    // This does NOT need to be synchronized nor applied across profile
    public void setAutoFreezeServiceEnabled(boolean enabled) {
        mStorage.setBoolean(LocalStorageManager.PREF_AUTO_FREEZE_SERVICE, enabled);
    }

    // Get the enabled state of the auto freeze service
    public boolean getAutoFreezeServiceEnabled() {
        return mStorage.getBoolean(LocalStorageManager.PREF_AUTO_FREEZE_SERVICE);
    }

    // Set the delay for auto freeze service (in seconds)
    public void setAutoFreezeDelay(int seconds) {
        mStorage.setInt(LocalStorageManager.PREF_AUTO_FREEZE_DELAY, seconds);
        syncSettingsToProfileInt(LocalStorageManager.PREF_AUTO_FREEZE_DELAY, seconds);
    }

    // Get the delay for auto freeze service
    public int getAutoFreezeDelay() {
        int ret = mStorage.getInt(LocalStorageManager.PREF_AUTO_FREEZE_DELAY);
        if (ret == Integer.MIN_VALUE) {
            // Default delay is 0 seconds
            ret = 0;
        }
        return ret;
    }

    // Set the enabled state of "skip foreground"
    public void setSkipForegroundEnabled(boolean enabled) {
        mStorage.setBoolean(LocalStorageManager.PREF_DONT_FREEZE_FOREGROUND, enabled);
        syncSettingsToProfileBool(LocalStorageManager.PREF_DONT_FREEZE_FOREGROUND, enabled);
    }

    // Get the enabled state of "skip foreground"
    public boolean getSkipForegroundEnabled() {
        return mStorage.getBoolean(LocalStorageManager.PREF_DONT_FREEZE_FOREGROUND);
    }

    // Set the enabled state of "parent pick folder"
    public void setParentPickFolderEnabled(boolean enabled) {
        mStorage.setBoolean(LocalStorageManager.PREF_PARENT_PICK_FOLDER, enabled);
        syncSettingsToProfileBool(LocalStorageManager.PREF_PARENT_PICK_FOLDER, enabled);
    }

    // Get the enabled state of "parent pick folder"
    public boolean getParentPickFolderEnabled() {
        return mStorage.getBoolean(LocalStorageManager.PREF_PARENT_PICK_FOLDER);
    }


    // Set the enabled state of "managed pick folder"
    public void setManagedPickFolderEnabled(boolean enabled) {
        mStorage.setBoolean(LocalStorageManager.PREF_MANAGED_PICK_FOLDER, enabled);
        syncSettingsToProfileBool(LocalStorageManager.PREF_MANAGED_PICK_FOLDER, enabled);
    }

    // Get the enabled state of "managed pick folder"
    public boolean getManagedPickFolderEnabled() {
        return mStorage.getBoolean(LocalStorageManager.PREF_MANAGED_PICK_FOLDER);
    }

    // Set the enabled state of "parent pick file"
    public void setParentPickFileEnabled(boolean enabled) {
        mStorage.setBoolean(LocalStorageManager.PREF_PARENT_PICK_FILE, enabled);
        syncSettingsToProfileBool(LocalStorageManager.PREF_PARENT_PICK_FILE, enabled);
    }

    // Get the enabled state of "parent pick file"
    public boolean getParentPickFileEnabled() {
        return mStorage.getBoolean(LocalStorageManager.PREF_PARENT_PICK_FILE);
    }


    // Set the enabled state of "managed pick file"
    public void setManagedPickFileEnabled(boolean enabled) {
        mStorage.setBoolean(LocalStorageManager.PREF_MANAGED_PICK_FILE, enabled);
        syncSettingsToProfileBool(LocalStorageManager.PREF_MANAGED_PICK_FILE, enabled);
    }

    // Get the enabled state of "managed pick file"
    public boolean getManagedPickFileEnabled() {
        return mStorage.getBoolean(LocalStorageManager.PREF_MANAGED_PICK_FILE);
    }

    // Set the enabled state of "parent use file picker"
    public void setParentUseFilePickerEnabled(boolean enabled) {
        mStorage.setBoolean(LocalStorageManager.PREF_PARENT_USE_FILE_PICKER, enabled);
        syncSettingsToProfileBool(LocalStorageManager.PREF_PARENT_USE_FILE_PICKER, enabled);
    }

    // Get the enabled state of "parent use file picker"
    public boolean getParentUseFilePickerEnabled() {
        return mStorage.getBoolean(LocalStorageManager.PREF_PARENT_USE_FILE_PICKER);
    }


    // Set the enabled state of "managed use file picker"
    public void setManagedUseFilePickerEnabled(boolean enabled) {
        mStorage.setBoolean(LocalStorageManager.PREF_MANAGED_USE_FILE_PICKER, enabled);
        syncSettingsToProfileBool(LocalStorageManager.PREF_MANAGED_USE_FILE_PICKER, enabled);
    }

    // Get the enabled state of "managed use file picker"
    public boolean getManagedUseFilePickerEnabled() {
        return mStorage.getBoolean(LocalStorageManager.PREF_MANAGED_USE_FILE_PICKER);
    }
}
