package net.strugee.freedombackup;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.support.v4.content.pm.PackageInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.*;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup pkglist = findViewById(R.id.pkglist);
        final PackageManager pm = getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_META_DATA);
        String pkgdump = "";
        for (PackageInfo pkg: packages) {
            ApplicationInfo app = pkg.applicationInfo;
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) continue;
            if ((app.flags & ApplicationInfo.FLAG_ALLOW_BACKUP) != 0) continue;

            Drawable icon = app.loadIcon(pm);
            icon.setBounds(0, 0, 128, 128);

            TextView text = new TextView(getApplicationContext());
            text.setText(app.loadLabel(pm));
            text.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null );

            pkglist.addView(text);

            pkgdump += pkg.packageName + ":";
            pkgdump += PackageInfoCompat.getLongVersionCode(pkg) + ":";
            pkgdump += pkg.versionName;
            pkgdump += "\n";
        }

        TextView pkgdumpview = new TextView(getApplicationContext());
        pkgdumpview.setText(pkgdump);
        pkglist.addView(pkgdumpview);
    }
}
