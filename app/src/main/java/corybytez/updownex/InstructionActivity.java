package corybytez.updownex;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;


public class InstructionActivity extends ActionBarActivity implements OnClickListener {
    private MediaPlayer audio;
    private ImageButton greenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greenButton = (ImageButton)findViewById(R.id.imageButton);
        greenButton.setOnClickListener(this);
        greenButton.setBackgroundColor(Color.TRANSPARENT);
        String item = getIntent().getStringExtra("blicket");
        int res_id = getResources().getIdentifier(item, "raw", getPackageName());

        audio = MediaPlayer.create(this, res_id );
        audio.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (audio.isPlaying()) {
            audio.stop();
        }
        finish();
    }
}
