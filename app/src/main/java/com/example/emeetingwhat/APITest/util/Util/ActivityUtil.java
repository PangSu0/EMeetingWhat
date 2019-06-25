package com.example.emeetingwhat.APITest.util.Util;

import android.app.Activity;
import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

/**
 * Activity 관련 유틸 클래스
 *
 */
public class ActivityUtil {

    /**
     * 안드로이드 리소스 사용 권한 체크
     *
     *  - 최초 호출시에는 "Never ask again" 체크박스가 없는 Andriod 권한 팝업(커스터마이징 불가)이 뜬다.
     *      - 허용했을 경우: 해당 권한이 허용되며, 이후 아무런 알림이 없다.
     *      - 거절했을 경우: 이후 호출시 하단 스낵바에 안내문구가 뜨고, 스낵바의 '설정'을 누르면 "Never ask again" 체크박스가 있는 Andriod 권한 팝업이 뜬다.
     *      - 거절했을 경우(Never ask again 체크): 해당 권한이 거절되며, 이후 아무런 알림이 없다.
     *
     *  - 정책상 Android 6.0 이상에서만 작동한다.
     *
     * @param permission
     * @param requestCode
     * @param activity
     * @return
     */
    public static boolean checkAccountsPermission(final String permission, final int requestCode, final String message, final Activity activity) {

        int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // We have the permission
            return true;

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

            // Need to show permission rationale, display a snackbar and then request
            // the permission again when the snackbar is dismissed.
            DrawerLayout layout = (DrawerLayout)activity.findViewById(R.id.drawer_layout);
            Snackbar.make(layout, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("설정", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Request the permission again.
                            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
                        }
                    }).show();
            return false;

        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            return false;
        }
    }

}