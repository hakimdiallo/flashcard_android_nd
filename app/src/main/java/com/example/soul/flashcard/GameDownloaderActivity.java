package com.example.soul.flashcard;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class GameDownloaderActivity extends MenuActivity {
    private TextView good_url;
    private EditText url;
    private Button download;
    private String lien;
    private DownloadManager manager = null;
    private long lastDownload=-1L;
    private long enqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game_downloader);
        super.onCreateDrawer();
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.activity_game_downloader,(ViewGroup)findViewById(R.id.content_frame));

        good_url = (TextView) findViewById(R.id.good_url);
        download = (Button) findViewById(R.id.download);
        download.setEnabled(false);
        url = (EditText) findViewById(R.id.url);
        url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                download.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (!s.isEmpty()){
                    download.setEnabled(true);
                }

            }
        });

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        registerReceiver(onComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        //registerReceiver(onNotificationClick,new)
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(onComplete);
        //unregisterReceiver(onNotificationClick);
    }

    public void download(View v) {
        lien = url.getText().toString();
        if(URLUtil.isValidUrl(lien)) {
            Uri uri = Uri.parse(lien);

            //Environment
            //        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            //        .mkdirs();

            lastDownload = manager.enqueue(new DownloadManager.Request(uri)
                            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                            //.setAllowedOverRoaming(false)
                            .setTitle("jeux_file")
                            //.setDescription("Something useful. No, really.")
                            //.setDestinationInExternalFilesDir(this,Environment.DIRECTORY_DOWNLOADS, "file.xml")
                            //(.setDestinationInExternalFilesDir(this,Environment.DIRECTORY_DOWNLOADS,"")
                    );

            //v.setEnabled(false);
            //findViewById(R.id.query).setEnabled(true);
        }
    }

    public void queryStatus(View v) {
        Cursor c = manager.query(new DownloadManager.Query().setFilterById(lastDownload));

        if (c==null) {
            Toast.makeText(this, "Download not found!", Toast.LENGTH_LONG).show();
        }
        else {
            c.moveToFirst();

            Log.d(getClass().getName(), "COLUMN_ID: "+
                    c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID)));
            Log.d(getClass().getName(), "COLUMN_BYTES_DOWNLOADED_SO_FAR: "+
                    c.getLong(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
            Log.d(getClass().getName(), "COLUMN_LAST_MODIFIED_TIMESTAMP: "+
                    c.getLong(c.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)));
            Log.d(getClass().getName(), "COLUMN_LOCAL_URI: "+
                    c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
            Log.d(getClass().getName(), "COLUMN_STATUS: "+
                    c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)));
            Log.d(getClass().getName(), "COLUMN_REASON: "+
                    c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON)));

            Toast.makeText(this, statusMessage(c), Toast.LENGTH_LONG).show();
        }
    }

    public void viewLog(View v) {
        startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
    }

    private String statusMessage(Cursor c) {
        String msg="???";

        switch(c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                msg="Download failed!";
                break;

            case DownloadManager.STATUS_PAUSED:
                msg="Download paused!";
                break;

            case DownloadManager.STATUS_PENDING:
                msg="Download pending!";
                break;

            case DownloadManager.STATUS_RUNNING:
                msg="Download in progress!";
                break;

            case DownloadManager.STATUS_SUCCESSFUL:
                msg="Download complete!";
                break;

            default:
                msg="Download is nowhere in sight";
                break;
        }

        return(msg);
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(lastDownload == referenceId) {

                int ch;
                ParcelFileDescriptor file;
                try {
                    file = manager.openDownloadedFile(lastDownload);
                    FileParser f = new FileParser(file,getApplicationContext());
                    f.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //findViewById(R.id.start).setEnabled(true);
        }
    };

    BroadcastReceiver onNotificationClick = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Toast.makeText(ctxt, "Ummmm...hi!", Toast.LENGTH_LONG).show();
        }
    };
}
