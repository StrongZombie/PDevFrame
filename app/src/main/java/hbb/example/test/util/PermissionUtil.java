package hbb.example.test.util;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

/**
 * Created by wyq on 2017/6/23.
 */

public class PermissionUtil {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    public static void initPhone(Activity context, String text) {
//        if (PhoneCallPermission(context)) {
        // 检查是否获得了权限（Android6.0运行时权限）
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
// 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CALL_PHONE)) {
// 返回值：
//如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
// 弹窗需要解释为何需要该权限，再次请求授权
                Toast.makeText(context, "请授权获得通话权限！", Toast.LENGTH_LONG).show();
// 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            } else {
// 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        } else {


            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + text));
            context.startActivity(intent);
        }

    }

    public static boolean PhoneCallPermission(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean permission = false;
        try {
            permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.CALL_PHONE", context.getPackageName()));
        } catch (Exception e) {
            permission = false;
            e.printStackTrace();
        }
        return permission;
    }

    public static boolean initQQ(Activity context, String text) {
        if (isQQClientAvailable(context)) {

            String url;
//            直接打开指定用户
            url = "mqqwpa://im/chat?chat_type=wpa&uin=" + text + "&version=1";

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivityForResult(intent, 1001);
//            joinQQGroup(context , "ZrWpPjEzik6Rhy6v-6rP7CS-t_R2I4_F");
            return true;
        } else {
            Toast.makeText(context.getApplicationContext(), "请先安装手机QQ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    private static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean initWeChat(Context context, String text) {
        try {
            if (isWeixinAvilible(context)) {
                //复制到手机粘贴板
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", text);
                cm.setPrimaryClip(mClipData);

                Toast.makeText(context.getApplicationContext(), "已复制", Toast.LENGTH_SHORT).show();

                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                intent.setAction(Intent.ACTION_VIEW);
                context.startActivity(intent);
                return true;
            } else {
                Toast.makeText(context.getApplicationContext(), "请先安装手机微信", Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断微信是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {

                    return true;
                }
            }
        }

        return false;
    }

    /****************
     * 发起添加群流程。群号：爱约玩-10(99569587) 的 key 为： ZrWpPjEzik6Rhy6v-6rP7CS-t_R2I4_F
     * 调用 joinQQGroup(ZrWpPjEzik6Rhy6v-6rP7CS-t_R2I4_F) 即可发起手Q客户端申请加群 爱约玩-10(99569587)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public static boolean joinQQGroup(Activity context, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivityForResult(intent, 1002);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(context.getApplicationContext(), "未安装手Q或安装的版本不支持", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
