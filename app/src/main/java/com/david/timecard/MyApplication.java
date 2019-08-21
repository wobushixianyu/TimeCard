package com.david.timecard;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.david.timecard.utils.db.RecordDBHelper;
import com.david.timecard.utils.db.ScheduleDBHelper;

import java.lang.ref.WeakReference;

import timber.log.Timber;

public class MyApplication extends Application {

    private static WeakReference<MyApplication> mWeakApplication;

    public static MyApplication getInstance(){
        return mWeakApplication.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWeakApplication = new WeakReference<>(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
        //初始化数据库
        ScheduleDBHelper.getInstance().init();
        RecordDBHelper.getInstance().init();
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, @NonNull String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
