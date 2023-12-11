package de.ebertp.HomeDroid.Communication.Sync;

import android.content.Context;
import android.os.Build;

import de.ebertp.HomeDroid.HomeDroidApp;
import de.ebertp.HomeDroid.Utils.PreferenceHelper;
import de.ebertp.HomeDroid.Utils.Util;
import timber.log.Timber;

public class PeriodSyncManager {

    private static int period;

    public static void next(Context context) {
        period = Util.getMinutesInMilis(PreferenceHelper
                .getPeriodicUpdateInterval(context));

        Timber.d("next() -> " + period);


        PeriodSyncJobService.scheduleJob(context, period);
    }

    public static void stop(Context context) {
        PeriodSyncJobService.cancelJob(context);
    }

    public static int getPeriod() {
        return period;
    }

    public static boolean isPeriodSyncRunning() {
        return PeriodSyncJobService.isJobScheduled(HomeDroidApp.getContext());
    }
}
