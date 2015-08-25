package corybytez.updownex;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.Random;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class UpDown extends Activity implements OnClickListener, Runnable{
    final Handler delay = new Handler();
    int index = 0;
    int direction = 0;
    boolean done = false;
    ImageButton up; ImageButton curr;
    ImageButton down;
    Chronometer timer;
    String correct;
    int width;
    int height;

    ArrayList<String[]> currList;
    ArrayList<String[]> up_pairs = new ArrayList<String[]>();
    ArrayList<String[]> down_pairs = new ArrayList<String[]>();
    HashMap<String, String> selection = new HashMap<String, String>();
    //String[] first = new String[]{"shoe", "feet", "rock", "flower", "leg", "car","mouse", "fish", "bike", "dog"};
    //String[] second = new String[]{"hat", "eye", "moon", "star", "arm", "airplane","butterfly", "bee", "balloon", "bird"};
    RelativeLayout myLayout;


    protected void play(){
        Random randGen = new Random();
        //direction = 0 -> up
        if (direction == 0 && index > 9) {
            index = 0;
            direction = 1;
        }
        else if (!done && index > 9 && direction == 1) {
            index = 0;
            direction = 0;
            currList = down_pairs;
            Collections.shuffle(currList);
            done = true;
        }
        else if (done && index > 9) {
            end();
            return;
        }

        int above = getResources().getIdentifier(selection.get(currList.get(index)[direction]) , "drawable", getPackageName());
        int below = getResources().getIdentifier(selection.get(currList.get(index)[1 - direction]), "drawable", getPackageName());

        height = randGen.nextInt(getResources().getDisplayMetrics().heightPixels / 3);
        width = randGen.nextInt(getResources().getDisplayMetrics().widthPixels - 300);
        System.out.println(width);
        if (height < 50) {
            height = 0;
        } if (width < 250) {
            width = 0;
        }

        Bitmap top = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), above), 250, 250, true);
        //Bitmap top = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.airplane),250, 250, true);
        up.setImageBitmap(top);
        //up.setRight(500);
        up.setX(width);
        up.setY(height);

        height = randGen.nextInt(getResources().getDisplayMetrics().heightPixels / 3 - 300) + getResources().getDisplayMetrics().heightPixels / 3;
        width = randGen.nextInt(getResources().getDisplayMetrics().widthPixels - 300);

        if (height < 250) {
            height = 500;
        } if (width < 250) {
            width = 0;
        }

        Bitmap bottom = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),below), 250, 250, true);
        //Bitmap bottom = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car), 250, 250, true);
        down.setImageBitmap(bottom);
        //down.setX(500);
        //down.setY(800);
        down.setX(width);
        down.setY(height);
        up.setOnClickListener(this);
        down.setOnClickListener(this);

        myLayout.removeAllViews();
        //((ViewGroup)myLayout.getParent()).removeView(myLayout);
        //Research about layouts and removing parents.
        //((ViewGroup)myLayout.getParent()).removeAllViews();

        myLayout.addView(up);
        myLayout.addView(down);
        setContentView(myLayout);

        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

        index++;

    }

    private void end() {
        //myLayout.removeAllViews();

        //setContentView(myLayout);
        Toast.makeText(UpDown.this,
                "This concludes the game. Congratulations!", Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_down);
        timer = (Chronometer) findViewById(R.id.chronometer);
        selection.put("shoe", "hat"); selection.put("feet","eye"); selection.put("rock", "moon");
        selection.put("flower","star"); selection.put("leg","arm"); selection.put("car","airplane");
        selection.put("mouse","butterfly"); selection.put("fish", "bee"); selection.put("bike", "balloon");
        selection.put("dog","bird"); selection.put("hat", "shoe"); selection.put("eye","feet");
        selection.put("moon", "rock");
        selection.put("star","flower"); selection.put("arm","leg"); selection.put("airplane","car");
        selection.put("butterfly","mouse"); selection.put("bee","fish"); selection.put("balloon","bike");
        selection.put("bird","dog");


        String[] pair1 = {"shoe", "hat"}; String[] pair2 = {"eye","feet"}; String[] pair3 = {"rock", "moon"};
        String[] pair4 = {"flower","star"}; String[] pair5 = {"leg","arm"}; String[] pair6 = {"car","airplane"};
        String[] pair7 = {"fish","bee"}; String[] pair8 = {"bike","balloon"}; String[] pair9 = {"dog","bird"};
        String[] pair10 = {"mouse","butterfly"};
        up_pairs.add(pair1); up_pairs.add(pair2); up_pairs.add(pair3); up_pairs.add(pair4); up_pairs.add(pair5);
        up_pairs.add(pair6);up_pairs.add(pair7); up_pairs.add(pair8); up_pairs.add(pair9); up_pairs.add(pair10);

        String[] pair11 = {"hat", "shoe"}; String[] pair12 = {"feet","eye"}; String[] pair13 = {"moon", "rock"};
        String[] pair14 = {"star","flower"}; String[] pair15 = {"arm","leg"}; String[] pair16 = {"airplane","car"};
        String[] pair17 = {"bee","fish"}; String[] pair18 = {"balloon","bike"}; String[] pair19 = {"bird","dog"};
        String[] pair20 = {"butterfly","mouse"};
        down_pairs.add(pair11); down_pairs.add(pair12); down_pairs.add(pair13); down_pairs.add(pair14);
        down_pairs.add(pair15); down_pairs.add(pair16); down_pairs.add(pair17); down_pairs.add(pair18);
        down_pairs.add(pair19); down_pairs.add(pair20);

        currList = up_pairs;
        Collections.shuffle(currList);



        //Sets up the string names from our hashtable. We use index to increment up the string arrays.

        int above = getResources().getIdentifier(selection.get(currList.get(index)[direction]) , "drawable", getPackageName());
        int below = getResources().getIdentifier(selection.get(currList.get(index)[1 - direction]), "drawable", getPackageName());

        Bitmap top = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), above), 250, 250, true);
        //Bitmap top = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.airplane),500, 500, true);
        up = new ImageButton(this);
        up.setImageBitmap(top);
        up.setRight(500);

        up.setOnClickListener(this);

        down = new ImageButton(this);
        Bitmap bottom = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), below), 250, 250, true);
        //Bitmap bottom = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car),500, 500, true);
        down.setImageBitmap(bottom);
        down.setX(500);
        down.setY(800);
        down.setOnClickListener(this);

        myLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        myLayout.addView(up);
        myLayout.addView(down);

        setContentView(myLayout);

        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        index++;


        //play();
    }

    @Override
    public void onClick(View v) {
        if (direction == 0) {
            curr = up;
        } else {
            curr = down;
        }

        if (v != curr) {
            Toast.makeText(UpDown.this,
                    "This is not a  bee, please try again!", Toast.LENGTH_SHORT).show();
            timer.stop();
            timer.start();
            return;
        }
        timer.stop();
        long elapsedMillis = SystemClock.elapsedRealtime() - timer.getBase();
        Toast.makeText(UpDown.this,
                "Elapsed seconds: " + elapsedMillis / 1000.0, Toast.LENGTH_SHORT).show();

        delay.postDelayed(this, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_up_down, menu);
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
    public void run() {
        play();
    }

}
