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
    private ImageButton correction;
    boolean hasResumed = false;
    int total = 1;
    int rights;
    boolean trainingDone = false;

    Chronometer timer;

    String correct;
    boolean gotRight = true;
    boolean menu = false;
    //boolean train = true;
    int width;
    int height;
    int maxHeight;
    int maxWidth;
    String name1; String name2;
    Intent i;

    ArrayList<String> questions = new ArrayList<String>();
    ArrayList<String> trainings = new ArrayList<String>();
    ArrayList<String> trainings2 = new ArrayList<String>();
    HashMap<String, ArrayList<String[]>> selection = new HashMap<String, ArrayList<String[]>>();
    RelativeLayout myLayout;



    protected void play(){

        Random randGen = new Random();
        System.out.println("Total is " + total + " trainingDone is " + trainingDone);
        if (questions.size() == 0 || trainings.size() == 0) {
            end();
            return;
        }
        int above = 0;
        int below = 0;
        int index = randGen.nextInt(2);
        if (trainer()) {
            String direction;
            String direction2;
            name1 = trainings.get(0);
            name2 = trainings2.get(0);

            above = getResources().getIdentifier(name1, "drawable", getPackageName());
            below = getResources().getIdentifier(name2, "drawable", getPackageName());
            System.out.println("name1: " + name1 + " id: " + above);
            System.out.println("name2: " + name2 + " id: " + below);
            trainings.remove(0);
            trainings2.remove(0);
            correction.setImageResource(getResources().getIdentifier(correct, "drawable", getPackageName()));
            correction.setOnClickListener(this);
            correction.setBackgroundColor(Color.YELLOW);
            correction.setVisibility(View.INVISIBLE);

        } else {
            System.out.println("No longer training as train: " + trainer());
            correct = questions.get(0);
            System.out.println("In play method, correct = " + correct);

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

            questions.remove(0);
        }
        height = randGen.nextInt(maxHeight / 3 - 250);
        width = randGen.nextInt(maxWidth / 2) - 100;
        if (height < 20) {
            height = 0;
        } if (width < 50) {
            width = 0;
        }
//        Toast.makeText(UpDown.this,
//                "Top height is  " + height + "!" + " Top width is  " + width, Toast.LENGTH_LONG).show();

        Bitmap top = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), above), 500, 500, true);
        if (name1 == "balloon") {
            top = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), above), 300, 500, true);
        }
        up.setImageBitmap(top);
        up.setX(width);
        up.setY(height);
        up.setBackgroundColor(Color.TRANSPARENT);

        height = randGen.nextInt(maxHeight / 2) + (maxHeight / 2 + 200);
        width = randGen.nextInt(maxWidth);
        /**
         * height and width are located here and below.
         * You are going to need to save those values here
         * and below.
         * up = imageButton on top.
         * down = imageButton below.
         */

        if (height > maxHeight - 750) {
            height = 1780;
        } if (width > 1000) {
            width = 800;
        }
//        Toast.makeText(UpDown.this,
//                "Bottom height is  " + height + "!" + " Bottom width is  " + width, Toast.LENGTH_SHORT).show();

        Bitmap bottom = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),below), 500, 500, true);
        if (name2 == "balloon") {
            bottom = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), below), 300, 500, true);
        }
        down.setImageBitmap(bottom);
        down.setX(width);
        down.setY(height);
        down.setBackgroundColor(Color.TRANSPARENT);

        myLayout.removeAllViews();

        correction.setX(maxWidth / 3);
        correction.setY(maxHeight / 3);

        myLayout.addView(up);
        myLayout.addView(down);
        myLayout.addView(correction);
        setContentView(myLayout);

        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

    }

    private void end() {

        //System.out.println("Congratz");
        Toast.makeText(UpDown.this,
                "This concludes the game. Congratulations!", Toast.LENGTH_SHORT).show();
        Intent j = new Intent(this, EndActivity.class);
        startActivity(j);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_down);
        timer = (Chronometer) findViewById(R.id.chronometer);

        maxWidth = getResources().getDisplayMetrics().widthPixels;
        maxHeight = getResources().getDisplayMetrics().heightPixels;
        i = new Intent(this, InstructionActivity.class);

        up = new ImageButton(this);
        down = new ImageButton(this);
        correction = new ImageButton(this);
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
        questions.add("dog"); questions.add("butterfly"); questions.add("butterfly"); questions.add("mouse");
        questions.add("mouse");


        Collections.shuffle(questions);
        Collections.shuffle(questions);
        Collections.shuffle(questions);
        Collections.shuffle(questions);
        Collections.shuffle(questions);

        trainings.add("blicket"); trainings.add("dax"); trainings.add("tunk"); trainings.add("tanzar"); trainings.add("lep");
        trainings.add("tima"); trainings.add("wug"); trainings.add("pank"); trainings.add("koba"); trainings.add("zav");

        trainings2.add("tima"); trainings2.add("wug"); trainings2.add("pank");  trainings2.add("koba");  trainings2.add("zav");
        trainings2.add("blicket"); trainings2.add("dax"); trainings2.add("tunk"); trainings.add("tanzar"); trainings.add("lep");





        //Sets up the string names from our hashmap. We use index to increment up the string arrays.
        //correct = questions.get(0);
        myLayout = new RelativeLayout(this);
        if (!menu) {
            Intent j = new Intent(this, GetInfo.class);
            startActivity(j);
            return;
        }
    }

    @Override
    public void onClick(View v) {
        if (correct == name1) {
            curr = up;
        } else {
            curr = down;
        }


        if (v != curr) {
//            Toast.makeText(UpDown.this,
//                    "This is not the " + correct + ", please try again!", Toast.LENGTH_SHORT).show();
            gotRight = true;
            if (!trainingDone) {
                //curr.setVisibility(View.INVISIBLE);
                curr.setBackgroundColor(Color.YELLOW);
                myLayout.removeView(curr);
                myLayout.addView(curr);
                setContentView(myLayout);
                //correction.setVisibility(View.VISIBLE);
                gotRight = false;

                rights = 0;
            }
        } else {
            gotRight = true;
            if (!trainingDone) {
                rights++;
            }
        }
        if (!trainingDone) {
            total++;
        }
        timer.stop();

        if (gotRight) {
            curr.setImageResource(getResources().getIdentifier("smile", "drawable", getPackageName()));
            curr.setX(maxWidth / 3);
            curr.setY(maxHeight / 3);

            myLayout.removeAllViews();
            myLayout.addView(curr);
            setContentView(myLayout);
        }
        curr.setVisibility(View.VISIBLE);

        long elapsedMillis = SystemClock.elapsedRealtime() - timer.getBase();
        Toast.makeText(UpDown.this,
                "Wow, you got it " + gotRight + "!" + " Elapsed seconds: " + elapsedMillis / 1000.0, Toast.LENGTH_SHORT).show();

        /**
         * gotRight = whether he/she got it right or not.
         * curr = correct image.
         * v = image the person clicked.
         * elapsedMillis = reaction time.
         */
        curr.setClickable(true);
        delay.postDelayed(this, 1500);
    }
    private boolean trainer() {
        System.out.println("Currently, the total is...." + total + " and trainingDone is " + trainingDone);
        if (total > 8 && !trainingDone) {
            end();
            finish();
            return false;
        }
        else if (total < 9 && rights == 3){
            trainingDone = true;
            return false;
        } else {
            return true;
        }
    }

    private void instruction() {
        if (questions.size() == 0 || trainings.size() == 0) {
            end();
            return;
        }
        //smile.setVisibility(View.INVISIBLE);
        String blicket = questions.get(0);
        System.out.println("The size of trainings is ...." + trainings.size());
        if (trainer()) {
            Random rand = new Random();
            int index = rand.nextInt(2);
            if (index == 0) {
                correct = trainings.get(0);
            } else {
                correct = trainings2.get(0);
            }
            blicket = correct;
        }

        Intent i = new Intent(this, InstructionActivity.class);
        i.putExtra("blicket", blicket);
        i.putExtra("train", trainer());
        startActivity(i);
    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        if (!hasResumed) {
            hasResumed = true;
            return;
        } else if (!menu && !trainingDone) {
            menu = true;
            System.out.println("Nice screen");
            instruction();
            return;
        } else if (trainingDone && questions.size() == 0) {
            Toast.makeText(UpDown.this, "Thanks for participating!", Toast.LENGTH_LONG).show();
            end();
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
