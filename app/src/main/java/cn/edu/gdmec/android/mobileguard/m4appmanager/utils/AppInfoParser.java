package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;

/**
 * Created by 11388 on 2017/11/9.
 */

public class AppInfoParser {

   /* 获取手机里面的所有的应用程序
    @parm context 上下文
     @return
     */

    public static List<AppInfo> getAppInfos(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packInfos = pm.getInstalledPackages(0);
        List<AppInfo> appinfos = new ArrayList<AppInfo>();
        for (PackageInfo packInfo : packInfos) {
            AppInfo appinfo = new AppInfo();
            String packname = packInfo.packageName;
            appinfo.packageName = packname;
            Drawable icon = packInfo.applicationInfo.loadIcon(pm);
            appinfo.icon = icon;
            String appname = packInfo.applicationInfo.loadLabel(pm).toString();
            appinfo.appName = appname;
           /*应用程序apk包的路径*/
            String apkpath = packInfo.applicationInfo.sourceDir;
            appinfo.apkPath = apkpath;
            File file = new File(apkpath);
            long appSize = file.length();
            appinfo.appSize = appSize;
            String version = packInfo.versionName;
            appinfo.version = version;
            appinfo.InstallTime = new Date(packInfo.firstInstallTime).toLocaleString();
            try {
                PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_SIGNATURES);
                byte[] ss = packinfo.signatures[0].toByteArray();
                CertificateFactory cf = CertificateFactory.getInstance("X509");
                X509Certificate cert = (X509Certificate) cf.generateCertificate(
                        new ByteArrayInputStream(ss));
                if (cert!=null){
                    appinfo.signature=cert.getIssuerDN().toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            PackageInfo packinfo1 = null;
            try {
                packinfo1 = pm.getPackageInfo(packname, PackageManager.GET_PERMISSIONS);
                if (packinfo1.requestedPermissions!=null){
                    for (String pio : packinfo1.requestedPermissions){
                        appinfo.permissions= appinfo.permissions+pio+"\n";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
           /*应用程序安装的位置*/
            int flags = packInfo.applicationInfo.flags;
            if ((ApplicationInfo.FLAG_EXTERNAL_STORAGE & flags) != 0) {
                //外部储存
                appinfo.isInRoom = false;
            } else {
                //手机内存
                appinfo.isInRoom = true;
            }
            if ((ApplicationInfo.FLAG_SYSTEM & flags) != 0) {
                //系统应用
                appinfo.isUserApp = false;
            } else {
                //用户应用
                appinfo.isUserApp = true;
            }
            appinfos.add(appinfo);
            appinfo = null;
        }
        return appinfos;

    }
}


