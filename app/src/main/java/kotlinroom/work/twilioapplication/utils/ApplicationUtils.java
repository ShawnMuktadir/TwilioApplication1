package kotlinroom.work.twilioapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import kotlinroom.work.twilioapplication.R;
import timber.log.Timber;

/*Created by ShawnMuktadir on 28-Feb-2020.*/
public class ApplicationUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        Timber.e("addFragmentToActivity -> %s", fragment.getClass().getSimpleName());
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    public static void addFragmentToActivityWithBackStack(@NonNull FragmentManager fragmentManager,
                                                          @NonNull Fragment fragment, int frameId, String title) {
        Timber.e("addFragmentToActivityWithBackStack -> %s", fragment.getClass().getSimpleName());
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(android.R.anim.fade_in,
//                android.R.anim.fade_out);
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(title);
        transaction.commit();
    }

    public static void removeFragments(FragmentManager fragmentManager, boolean isPopBackStack, String from) {
//        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
//            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        }
        Timber.e("fragmentManager-> %s", fragmentManager.getFragments().size());
        for (Fragment fragment : fragmentManager.getFragments()) {
            Timber.e("removeFragments-> %s", fragment.getClass().getSimpleName());
//            fragmentManager.beginTransaction().remove(fragment).commit();
            fragmentManager.popBackStack();
        }
    }

    public static boolean checkInternet(Context context) {
        ConnectivityManager check = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = check.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void hideKeyboard(final Activity activity) {
        Timber.e("hideKeyboard -> ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (activity != null) {
                                final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                                final View view = activity.getCurrentFocus();
                                if (view != null) {
                                    final IBinder binder = view.getWindowToken();
                                    imm.hideSoftInputFromWindow(binder, 0);
                                    imm.showSoftInputFromInputMethod(binder, 0);
                                }
                            }
                        } catch (final Exception e) {
                            Timber.d(e, "-> %s Exception to hide keyboard", ApplicationUtils.class.getSimpleName());
                        }
                    }
                });
            }
        }).start();
    }

    public static void showMessageDialog(String message, Context context) {
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message);
            builder.setCancelable(true);
            builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
