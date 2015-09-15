package corybytez.updownex;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class InstructionActivity extends ActionBarActivity implements OnClickListener {
    private MediaPlayer audio;
    private ImageButton greenButton;
    private boolean training;
    private String item2;
    private String item;
    private ImageButton blicket;
    private RelativeLayout thisLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(R.anim.abc_slide_in_top);
        setContentView(R.layout.activity_main);
        greenButton = (ImageButton)findViewById(R.id.imageButton);
        greenButton.setOnClickListener(this);
        greenButton.setBackgroundColor(Color.TRANSPARENT);
        blicket = new ImageButton(this);
        thisLayout = new RelativeLayout(this);
//        RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        lParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        lParams.addRule(RelativeLayout.CENTER_VERTICAL);
        item = getIntent().getStringExtra("blicket");
        training = getIntent().getBooleanExtra("train", false);
        System.out.println("is training on? " + training);
        thisLayout.removeAllViews();
        if (training) {
            item2 = item;
            //System.out.println(getResources().getIdentifier(item2, "drawable", getPackageName()));
            item = item + "1";
            System.out.println("item 2 is " + item2 + " training: " + training);
            blicket.setImageResource(getResources().getIdentifier(item2, "drawable", getPackageName()));
            blicket.setOnClickListener(this);
            greenButton = new ImageButton(this);
            greenButton.setOnClickListener(this);
            greenButton.setBackgroundColor(Color.TRANSPARENT);
            greenButton.setImageResource(getResources().getIdentifier("button", "drawable", getPackageName()));
            greenButton.setX(getResources().getDisplayMetrics().widthPixels / 2);
            greenButton.setY(getResources().getDisplayMetrics().heightPixels / 2);
            //greenButton.setLayoutParams(lParams);


            thisLayout.addView(blicket);
            //thisLayout.addView(greenButton);
            setContentView(thisLayout);
        }
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
        if (training) {
            if (v == blicket) {
                System.out.println("You touched the blicket.");
                thisLayout.removeAllViews();
                thisLayout.addView(greenButton);
                item2 = item2 + "2";
                int res_id = getResources().getIdentifier(item2, "raw", getPackageName());
                setContentView(thisLayout);
                MediaPlayer audio2 = MediaPlayer.create(this, res_id);
                while (audio.isPlaying()) {}
                audio2.start();
                return;
            }
        }

        finish();
    }
}
