package corybytez.updownex;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;
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
import android.widget.Chronometer;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;
import android.widget.Toast;



public class UpDown extends Activity implements OnClickListener, Runnable{
    final Handler delay = new Handler();
    ImageButton up; ImageButton curr;
    ImageButton down;
    Boolean hasResumed = false;
    //ImageView smile;

    Chronometer timer;

    String correct;
    boolean gotRight;
    int width;
    int height;
    int maxHeight;
    int maxWidth;
    String name1; String name2;
    Intent i;

    ArrayList<String> questions = new ArrayList<String>();
    HashMap<String, ArrayList<String[]>> selection = new HashMap<String, ArrayList<String[]>>();
    RelativeLayout myLayout;


    protected void play(){

        Random randGen = new Random();
        if (questions.size() == 0) {
            end();
            return;
        }
        System.out.println("First elem is " + questions.get(0));

        correct = questions.get(0);
        System.out.println("In play method, correct = " + correct);
        //instruction(correct);
        int index = randGen.nextInt(2);
        int above = 0;
        int below = 0;
        if (selection.get(correct).size() < 2) {
            index = 0;
            name1 = selection.get(correct).get(index)[index];
            name2 = selection.get(correct).get(index)[1 - index];
            above = getResources().getIdentifier(name1, "drawable", getPackageName());
            below = getResources().getIdentifier(name2, "drawable", getPackageName());

        } else {
            String[] remain = selection.get(correct).get(index);
            ArrayList<String[]> remaining = new ArrayList<String[]>();
            remaining.add(remain);
            name1 = selection.get(correct).get(index)[index];
            name2 = selection.get(correct).get(index)[1 - index];
            above = getResources().getIdentifier(name1, "drawable", getPackageName());
            below = getResources().getIdentifier(name2, "drawable", getPackageName());
            selection.put(correct, remaining);
        }

        System.out.println("After both buttons, we have correct as " + correct +" and buttons " +
                "as " + name1 + " " + name2);

        //selection.put(questions.get(0), remaining);
        System.out.println("Removing..." + questions.get(0));
        questions.remove(0);

        height = randGen.nextInt(maxHeight / 4);
        width = randGen.nextInt(maxWidth - 300);
        System.out.println(height);
        if (height < 20) {
            height = 0;
        } if (width < 50) {
            width = 0;
        }

        Bitmap top = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), above), 250, 250, true);
        //Bitmap top = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.airplane),250, 250, true);
        up.setImageBitmap(top);
        //up.setRight(500);
        up.setX(width);
        up.setY(height);
        up.setBackgroundColor(Color.TRANSPARENT);

        height = randGen.nextInt(maxHeight / 2) + (maxHeight / 3 * 2 + 50);
        width = randGen.nextInt(maxWidth - 300);

        if (height > maxHeight - 300) {
            height = 850;
        } if (width < 50) {
            width = 500;
        }
        System.out.println("The new height is " + height);

        Bitmap bottom = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),below), 250, 250, true);
        //Bitmap bottom = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car), 250, 250, true);
        down.setImageBitmap(bottom);
        //down.setX(500);
        //down.setY(800);
        down.setX(width);
        down.setY(height);
        down.setBackgroundColor(Color.TRANSPARENT);
        //up.setOnClickListener(this);
        //down.setOnClickListener(this);

        myLayout.removeAllViews();

        myLayout.addView(up);
        myLayout.addView(down);
        setContentView(myLayout);

        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

    }

    private void end() {
        //myLayout.removeAllViews();

        //setContentView(myLayout);
        System.out.println("Congratz");
        Toast.makeText(UpDown.this,
                "This concludes the game. Congratulations!", Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_down);
        timer = (Chronometer) findViewById(R.id.chronometer);

        //smile.setVisibility(View.INVISIBLE);
        maxWidth = getResources().getDisplayMetrics().widthPixels;
        maxHeight = getResources().getDisplayMetrics().heightPixels;
        i = new Intent(this, InstructionActivity.class);

        up = new ImageButton(this);
        down = new ImageButton(this);
        up.setOnClickListener(this);
        down.setOnClickListener(this);




        ArrayList<String[]> pair1 = new ArrayList<String[]>(); pair1.add(new String[]{"hat", "shoe"}); pair1.add(new String[]{"shoe", "hat"});
        ArrayList<String[]> pair2 = new ArrayList<String[]>(); pair2.add(new String[]{"eye", "feet"}); pair2.add(new String[]{"feet", "eye"});
        ArrayList<String[]> pair3 = new ArrayList<String[]>(); pair3.add(new String[]{"moon", "rock"}); pair3.add(new String[]{"rock", "moon"});
        ArrayList<String[]> pair4 = new ArrayList<String[]>(); pair4.add(new String[]{"star", "flower"}); pair4.add(new String[]{"flower", "star"});
        ArrayList<String[]> pair5 = new ArrayList<String[]>(); pair5.add(new String[]{"arm", "leg"}); pair5.add(new String[]{"leg", "arm"});
        ArrayList<String[]> pair6 = new ArrayList<String[]>(); pair6.add(new String[]{"airplane", "car"}); pair6.add(new String[]{"car", "airplane"});
        ArrayList<String[]> pair7 = new ArrayList<String[]>(); pair7.add(new String[]{"bee", "fish"}); pair7.add(new String[]{"fish", "bee"});
        ArrayList<String[]> pair8 = new ArrayList<String[]>(); pair8.add(new String[]{"balloon", "bike"}); pair8.add(new String[]{"bike", "balloon"});
        ArrayList<String[]> pair9 = new ArrayList<String[]>(); pair9.add(new String[]{"bird", "dog"}); pair9.add(new String[]{"dog", "bird"});
        ArrayList<String[]> pair10 = new ArrayList<String[]>(); pair10.add(new String[]{"butterfly", "mouse"}); pair10.add(new String[]{"mouse", "butterfly"});


        selection.put("hat", pair1); selection.put("shoe", pair1); selection.put("eye", pair2);
        selection.put("feet", pair2); selection.put("moon", pair3); selection.put("rock", pair3);
        selection.put("star", pair4); selection.put("flower", pair4); selection.put("arm", pair5);
        selection.put("leg", pair5); selection.put("airplane", pair6); selection.put("car", pair6);
        selection.put("bee", pair7); selection.put("fish", pair7); selection.put("balloon", pair8);
        selection.put("bike", pair8); selection.put("bird", pair9); selection.put("dog", pair9);
        selection.put("butterfly", pair10); selection.put("mouse", pair10);


        questions.add("hat"); questions.add("hat"); questions.add("shoe"); questions.add("shoe"); questions.add("eye");
        questions.add("eye"); questions.add("feet"); questions.add("feet"); questions.add("moon"); questions.add("moon");
        questions.add("rock"); questions.add("rock"); questions.add("star"); questions.add("star"); questions.add("flower");
        questions.add("flower"); questions.add("arm"); questions.add("arm"); questions.add("leg"); questions.add("leg");
        questions.add("airplane"); questions.add("airplane"); questions.add("car"); questions.add("car"); questions.add("bee");
        questions.add("bee"); questions.add("fish"); questions.add("fish"); questions.add("balloon"); questions.add("balloon");
        questions.add("bike"); questions.add("bike"); questions.add("bird"); questions.add("bird"); questions.add("dog");
        questions.add("dog"); questions.add("butterfly"); questions.add("butterfly"); questions.add("mouse"); questions.add("mouse");


        Collections.shuffle(questions);



        //Sets up the string names from our hashmap. We use index to increment up the string arrays.
        correct = questions.get(0);
        Intent j = new Intent(this, InstructionActivity.class);
        j.putExtra("blicket", correct);
        startActivity(j);
        System.out.println("Initially, the question is " + correct);
        //delay.postDelayed(this, 500);

//        Random choice = new Random();
//        int index = choice.nextInt(2);
//        name1 = selection.get(questions.get(0)).get(index)[index];
//        name2 = selection.get(questions.get(0)).get(index)[1 - index];
//        int above = getResources().getIdentifier(name1, "drawable", getPackageName());
//        int below = getResources().getIdentifier(name2, "drawable", getPackageName());
//
//
//        Bitmap top = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), above), 250, 250, true);
//        //Bitmap top = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.airplane),500, 500, true);
        //up = new ImageButton(this);
//        up.setImageBitmap(top);
//        up.setRight(500);
//
        //up.setOnClickListener(this);
//
        //down = new ImageButton(this);
//        Bitmap bottom = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), below), 250, 250, true);
//        //Bitmap bottom = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car),500, 500, true);
//        down.setImageBitmap(bottom);
//        down.setX(500);
//        down.setY(800);
        //down.setOnClickListener(this);
//
        myLayout = new RelativeLayout(this);
//        RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT);
//
//        myLayout.addView(up);
//        myLayout.addView(down);
//
//        setContentView(myLayout);
//        String[] remain = selection.get(questions.get(0)).get(1 - index);
//        ArrayList<String[]> remaining = new ArrayList<String[]>();
//        remaining.add(remain);
//        selection.put(questions.get(0), remaining);
//        Toast.makeText(UpDown.this,
//                "Please find the " + correct + ".", Toast.LENGTH_SHORT).show();
//        questions.remove(0);
//
//        timer.setBase(SystemClock.elapsedRealtime());
//        timer.start();

    }

    @Override
    public void onClick(View v) {
        if (correct == name1) {
            curr = up;
        } else {
            curr = down;
        }

        if (v != curr) {
            //Toast.makeText(UpDown.this,
            //        "This is not the " + correct + ", please try again!", Toast.LENGTH_SHORT).show();
            gotRight = false;
        } else {
            gotRight = true;
        }
        timer.stop();
        curr.setImageResource(getResources().getIdentifier("smile", "drawable", getPackageName()));
        curr.setX(maxWidth / 2);
        curr.setY(maxHeight / 3);
        curr.setClickable(false);
        myLayout.removeAllViews();
        myLayout.addView(curr);
        setContentView(myLayout);
        long elapsedMillis = SystemClock.elapsedRealtime() - timer.getBase();
        Toast.makeText(UpDown.this,
                "Wow, you got it " + gotRight + "!" + " Elapsed seconds: " + elapsedMillis / 1000.0, Toast.LENGTH_SHORT).show();
        //instruction();
        curr.setClickable(true);
        delay.postDelayed(this, 2500);
    }

    private void instruction() {
        if (questions.size() == 0) {
            end();
            return;
        }
        //smile.setVisibility(View.INVISIBLE);
        String blicket = questions.get(0);

        //Intent i = new Intent(this, InstructionActivity.class);
        i.putExtra("blicket", blicket);
        startActivity(i);
    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        if (!hasResumed) {
            hasResumed = true;
            return;
        }
        play();
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
        instruction();
    }


}
