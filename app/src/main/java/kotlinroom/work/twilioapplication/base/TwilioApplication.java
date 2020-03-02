package kotlinroom.work.twilioapplication.base;

import android.app.Application;

import kotlinroom.work.twilioapplication.BuildConfig;
import timber.log.Timber;

/*Created by MiQ0717 on 28-Feb-2020.*/
public class TwilioApplication extends Application {

    public static final String TAG = TwilioApplication.class.getSimpleName();

    private static TwilioApplication TwilioApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        TwilioApplication = this;
        initTimber();
//        LocaleHelper.setLocale(getApplicationContext(), new TinyDB(getApplicationContext()).getString(APP_LANGUAGE));
    }

    private void initTimber() {
        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree(){
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ": "+ element.getLineNumber();
                }
            });
        }
    }

    public static TwilioApplication getApplication(){
        return TwilioApplication;
    }
}
