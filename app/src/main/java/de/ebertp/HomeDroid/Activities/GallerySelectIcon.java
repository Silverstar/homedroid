package de.ebertp.HomeDroid.Activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import de.ebertp.HomeDroid.DbAdapter.ConcreteHelpers.IconDbAdapter;
import de.ebertp.HomeDroid.DbAdapter.DataBaseAdapterManager;
import de.ebertp.HomeDroid.HomeDroidApp;
import de.ebertp.HomeDroid.R;
import de.ebertp.HomeDroid.ViewAdapter.ImageAdapter;

public class GallerySelectIcon extends AppCompatActivity {


    protected DataBaseAdapterManager dbM;
    private IconDbAdapter mIconsDb;
    private int mRowId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.HomeDroidDark);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.homedroid_primary));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon_gallery);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mRowId = extras.getInt("rowId");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbM = HomeDroidApp.db();
        mIconsDb = dbM.iconDbAdapter;

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @SuppressWarnings("rawtypes")
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                int icon = position;
                Cursor c = mIconsDb.fetchItem(mRowId);
                if (c.getCount() == 0) {
                    mIconsDb.createItem(mRowId, icon);
                } else {
                    mIconsDb.updateItem(mRowId, icon);
                }
                de.ebertp.HomeDroid.Utils.Util.closeCursor(c);

                dbM.externalIconDbAdapter.deleteItem(mRowId);

                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
