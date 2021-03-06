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
        greenButton.setClickable(false);
        //greenButton.setVisibility(View.INVISIBLE);
        blicket = new ImageButton(this);
        thisLayout = new RelativeLayout(this);

        item = getIntent().getStringExtra("blicket");
        training = getIntent().getBooleanExtra("train", false);
        System.out.println("is training on? " + training);
        thisLayout.removeAllViews();
        if (training) {
            item2 = item + "s";
            item2 = item;
            //System.out.println(getResources().getIdentifier(item2, "drawable", getPackageName()));
            //item = item + "1";
            System.out.println("item 2 is " + item2 + " training: " + training);
            blicket.setImageResource(getResources().getIdentifier(item2, "drawable", getPackageName()));
            blicket.setX(getResources().getDisplayMetrics().widthPixels / 3);
            blicket.setY(getResources().getDisplayMetrics().heightPixels / 3);
            blicket.setBackgroundColor(Color.TRANSPARENT);
            blicket.setOnClickListener(this);
            thisLayout.addView(blicket);
        }

        greenButton = new ImageButton(this);


        greenButton.setOnClickListener(this);
        greenButton.setBackgroundColor(Color.TRANSPARENT);
        greenButton.setImageResource(getResources().getIdentifier("button", "drawable", getPackageName()));

        greenButton.setX(getResources().getDisplayMetrics().widthPixels / 3);
        greenButton.setY(getResources().getDisplayMetrics().heightPixels / 3);
        if(training) {
            greenButton.setVisibility(View.INVISIBLE);
        }
        //greenButton.setLayoutParams(lParams);



        thisLayout.addView(greenButton);
        setContentView(thisLayout);
        int res_id = getResources().getIdentifier(item, "raw", getPackageName());

        audio = MediaPlayer.create(this, res_id );

        audio.start();
        //while(audio.isPlaying()){}
        greenButton.setClickable(true);
        greenButton.setEnabled(false);
        //while(audio.isPlaying()) {}
        greenButton.setEnabled(true);
//        if (!training) {
//            greenButton.setClickable(false);
//            while (audio.isPlaying()) {}
//        }
////        for (int i = 0; i < 1000; i++) {}
////        while (!audio.isPlaying()) {
////            greenButton.setClickable(true);
////          } ;
//        if(!audio.isPlaying()) {
//            greenButton.setClickable(true);
//        }
        //greenButton.setClickable(true);

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
            //audio.stop();
            return;
        }
        if (training) {
            if (v == blicket) {
                System.out.println("You touched the blicket.");
                //thisLayout.removeAllViews();
                //thisLayout.addView(greenButton);
                item2 = item2 + "1";
                int res_id = getResources().getIdentifier(item2, "raw", getPackageName());
                //setContentView(thisLayout);
                blicket.setVisibility(View.INVISIBLE);
                greenButton.setVisibility(View.VISIBLE);
                //setContentView(thisLayout);
                while (audio.isPlaying()) {}

                audio = MediaPlayer.create(this, res_id);
                audio.start();
                setContentView(thisLayout);
                return;

            }
        }

        finish();
    }
}
