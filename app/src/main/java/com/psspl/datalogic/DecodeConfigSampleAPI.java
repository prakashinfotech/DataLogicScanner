package com.psspl.datalogic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.datalogic.decode.BarcodeManager;
import com.datalogic.decode.DecodeException;
import com.datalogic.decode.DecodeResult;
import com.datalogic.decode.ReadListener;
import com.datalogic.decode.configuration.LengthControlMode;
import com.datalogic.decode.configuration.ScannerProperties;
import com.datalogic.device.ErrorManager;
import com.datalogic.device.configuration.ConfigException;

/**
 * Created by haresh
 * on 24-11-2018.
 */

public class DecodeConfigSampleAPI extends AppCompatActivity {

    // Constant for log messages.
    private final String LOGTAG = getClass().getName();
    ReadListener listener = null;
    BarcodeManager manager = null;
    ScannerProperties configuration = null;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decode_config_sample_api);
        textView=findViewById(R.id.textView);
        // Create a BarcodeManager.
        manager = new BarcodeManager();

        // Pass it to ScannerProperties class.
        // ScannerProperties cannot be instantiated directly, instead call edit.
        configuration = ScannerProperties.edit(manager);

        // Now we can change some Scanner/Device configuration parameters.
        // These values are not applied, as long as store method is not called.
        configuration.code39.enable.set(true);
        configuration.code39.enableChecksum.set(false);
        configuration.code39.fullAscii.set(true);
        configuration.code39.Length1.set(20);
        configuration.code39.Length2.set(2);
        configuration.code39.lengthMode.set(LengthControlMode.TWO_FIXED);
        configuration.code39.sendChecksum.set(false);
        configuration.code39.userID.set('x');

        configuration.code128.enable.set(true);
        configuration.code128.Length1.set(6);
        configuration.code128.Length2.set(2);
        configuration.code128.lengthMode.set(LengthControlMode.RANGE);
        configuration.code128.userID.set('y');

        if (configuration.qrCode.isSupported()) {
            configuration.qrCode.enable.set(false);
        }

        // Change IntentWedge action and category to specific ones.
        configuration.intentWedge.action.set("com.datalogic.examples.decode_action");
        configuration.intentWedge.category.set("com.datalogic.examples.decode_category");

        // From here on, we would like to get a return value instead of an exception in case of error.
        ErrorManager.enableExceptions(false);

        // Now we are ready to store them.
        // Second parameter set to true saves configuration in a permanent way.
        // After boot settings will be still valid.
        int errorCode = configuration.store(manager, true);

        // Check return value.
        if(errorCode != ConfigException.SUCCESS) {
            Log.e(LOGTAG, "Error during store", ErrorManager.getLastError());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (manager == null) { // Remember an onPause call will set it to null.
            manager = new BarcodeManager();
        }
        // From here on, we want to be notified with exceptions in case of errors.
        ErrorManager.enableExceptions(true);
        try {

            // Create an anonymous class.
            listener = new ReadListener() {

                // Implement the callback method.
                @Override
                public void onRead(DecodeResult decodeResult) {
                    // Change the displayed text to the current received result.
                    textView.setText(decodeResult.getText());
                }

            };

            // Remember to add it, as a listener.
            manager.addReadListener(listener);

        } catch (DecodeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                startSettingsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Method called by displayed button
    public void buttonClicked(View v) {
        startSettingsActivity();
    }

    private void startSettingsActivity() {
        // Create and start an intent to pop up Android Settings
        Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }
}
