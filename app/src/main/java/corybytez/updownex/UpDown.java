package corybytez.updownex;

import android.app.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.os.SystemClock;

import android.util.Log;
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
    int total = 0;
    int rights;
    boolean trainingDone = false;
    boolean kill = false;

    Chronometer timer;

    String correct;
    boolean gotRight = true;
    boolean menu = false;
    boolean accurate = false;
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

    String ordered_pairs =new String();
    String X1 =new String();
    String X2 = new String();
    String Y1 = new String();
    String Y2 = new String();
    String time = new String();
    String accuracy = new String();
    String corr_img = new String();
    String trial_or_not= new String();
    String target_position = new String();
    //indicates if the target image is on the top or the bottom of the screen
    String curr_ID;
    String curr_name;
    int ordered_pairs_size = 0;

    protected void play(){

        Random randGen = new Random();
        System.out.println("Total is " + total + " trainingDone is " + trainingDone + " size of training is " + trainings.size());
        if (questions.size() == 0 || trainings.size() == 0) {
            end();
            return;
        }
        int above = 0;
        int below = 0;
        int index = randGen.nextInt(8);
        if (trainer()) {
            String direction;
            String direction2;
            if(index < 4){
                name1 = trainings2.get(0);
                name2 = trainings.get(0);
            } else {
                name1 = trainings.get(0);
                name2 = trainings2.get(0);
            }
            //name1 = trainings.get(0);
            //name2 = trainings2.get(0);

            //store_object_names(name1.toString(), name2.toString());
            ordered_pairs = ordered_pairs + "("+name1+","+name2+")" + "\n";
            ordered_pairs_size++;
            trial_or_not= trial_or_not + "trial \n";

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

                //System.out.println("Inside real trial.");
                ordered_pairs = ordered_pairs + "("+name1+","+name2+")" + "\n";
                ordered_pairs_size++;
                trial_or_not= trial_or_not + "test \n";

                above = getResources().getIdentifier(name1, "drawable", getPackageName());
                below = getResources().getIdentifier(name2, "drawable", getPackageName());

            } else {
                /*
                String[] remain = selection.get(correct).get(index);
                ArrayList<String[]> remaining = new ArrayList<String[]>();
                remaining.add(remain);
                name1 = selection.get(correct).get(index)[index];
                name2 = selection.get(correct).get(index)[1 - index];

                System.out.println("Inside real trial.");
                ordered_pairs = ordered_pairs + "("+name1+","+name2+")" + "\n";
                ordered_pairs_size++;

                above = getResources().getIdentifier(name1, "drawable", getPackageName());
                below = getResources().getIdentifier(name2, "drawable", getPackageName());
                selection.put(correct, remaining);
                */
                String[] remain = selection.get(correct).get(1 - index);
                ArrayList<String[]> remaining = new ArrayList<String[]>();
                remaining.add(remain);
                name1 = selection.get(correct).get(index)[0];
                name2 = selection.get(correct).get(index)[1];

                ordered_pairs = ordered_pairs + "("+name1+","+name2+")" + "\n";
                ordered_pairs_size++;
                trial_or_not= trial_or_not + "test \n";

                above = getResources().getIdentifier(name1, "drawable", getPackageName());
                below = getResources().getIdentifier(name2, "drawable", getPackageName());

                selection.put(correct, remaining);
            }
            System.out.println("Top is " + name1 + " and bottom is " + name2); // ADDED
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

        X1 = X1 + width +  "\n";
        Y1 = Y1 + height + "\n";

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

        X2 = X2 + width +"\n";
        Y2 = Y2 + height + "\n";

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

    private void store_final_values()
    {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //System.out.println("ordered_pairs_size = "+ ordered_pairs_size);
        System.out.println(ordered_pairs);

        ContentValues values1 = new ContentValues();
        values1.put(FeedReaderContract.Table1.COLUMN_NAME_COL8, ordered_pairs);
        values1.put(FeedReaderContract.Table1.COLUMN_NAME_COL10, X1);
        values1.put(FeedReaderContract.Table1.COLUMN_NAME_COL11, Y1);
        values1.put(FeedReaderContract.Table1.COLUMN_NAME_COL12, X2);
        values1.put(FeedReaderContract.Table1.COLUMN_NAME_COL13, Y2);
        values1.put(FeedReaderContract.Table1.COLUMN_NAME_COL14, time);
        values1.put(FeedReaderContract.Table1.COLUMN_NAME_COL15, accuracy);
        values1.put(FeedReaderContract.Table1.COLUMN_NAME_COL17, target_position);
        values1.put(FeedReaderContract.Table1.COLUMN_NAME_COL18, corr_img);
        values1.put(FeedReaderContract.Table1.COLUMN_NAME_COL19, trial_or_not);

        long newRowId1 = db.update(
                FeedReaderContract.Table1.TABLE_NAME, values1,"_id "+"="+curr_ID, null);
    }

    private void end() {

        //System.out.println("Congratz");
        //Toast.makeText(UpDown.this,
                //"This concludes the game. Congratulations!", Toast.LENGTH_SHORT).show();
        store_final_values();
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
        //ArrayList<String[]> pair3 = new ArrayList<String[]>(); pair3.add(new String[]{"moon", "rock"}); pair3.add(new String[]{"rock", "moon"});
        //ArrayList<String[]> pair4 = new ArrayList<String[]>(); pair4.add(new String[]{"star", "flower"}); pair4.add(new String[]{"flower", "star"});
        //ArrayList<String[]> pair5 = new ArrayList<String[]>(); pair5.add(new String[]{"arm", "leg"}); pair5.add(new String[]{"leg", "arm"});
        ArrayList<String[]> pair3 = new ArrayList<String[]>(); pair3.add(new String[]{"helicopter", "truck"}); pair3.add(new String[]{"truck", "helicopter"});
        ArrayList<String[]> pair4 = new ArrayList<String[]>(); pair4.add(new String[]{"cloud", "flower"}); pair4.add(new String[]{"flower", "cloud"});
        ArrayList<String[]> pair5 = new ArrayList<String[]>(); pair5.add(new String[]{"flag", "chair"}); pair5.add(new String[]{"chair", "flag"});
       //END OF ADDED.
        ArrayList<String[]> pair6 = new ArrayList<String[]>(); pair6.add(new String[]{"airplane", "car"}); pair6.add(new String[]{"car", "airplane"});
        ArrayList<String[]> pair7 = new ArrayList<String[]>(); pair7.add(new String[]{"bee", "fish"}); pair7.add(new String[]{"fish", "bee"});
        ArrayList<String[]> pair8 = new ArrayList<String[]>(); pair8.add(new String[]{"balloon", "bike"}); pair8.add(new String[]{"bike", "balloon"});
        ArrayList<String[]> pair9 = new ArrayList<String[]>(); pair9.add(new String[]{"bird", "dog"}); pair9.add(new String[]{"dog", "bird"});
        ArrayList<String[]> pair10 = new ArrayList<String[]>(); pair10.add(new String[]{"butterfly", "mouse"}); pair10.add(new String[]{"mouse", "butterfly"});

        /*
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
        */
        selection.put("hat", pair1); selection.put("shoe", pair1); selection.put("eye", pair2);
        selection.put("feet", pair2); selection.put("helicopter", pair3); selection.put("truck", pair3);
        selection.put("cloud", pair4); selection.put("flower", pair4); selection.put("flag", pair5);
        selection.put("chair", pair5); selection.put("airplane", pair6); selection.put("car", pair6);
        selection.put("bee", pair7); selection.put("fish", pair7); selection.put("balloon", pair8);
        selection.put("bike", pair8); selection.put("bird", pair9); selection.put("dog", pair9);
        selection.put("butterfly", pair10); selection.put("mouse", pair10);


        questions.add("hat"); questions.add("shoe"); questions.add("eye");
        questions.add("feet"); questions.add("helicopter");
        questions.add("truck"); questions.add("cloud"); questions.add("flower");
        questions.add("flag"); questions.add("chair");
        questions.add("airplane"); questions.add("car"); questions.add("bee");
        questions.add("fish"); questions.add("balloon");
        questions.add("bike"); questions.add("bird"); questions.add("dog");
        questions.add("butterfly"); questions.add("mouse");
        questions.add("hat"); questions.add("shoe"); questions.add("eye");
        questions.add("feet"); questions.add("helicopter");
        questions.add("truck"); questions.add("cloud"); questions.add("flower");
        questions.add("flag"); questions.add("chair");
        questions.add("airplane"); questions.add("car"); questions.add("bee");
        questions.add("fish"); questions.add("balloon");
        questions.add("bike"); questions.add("bird"); questions.add("dog");
        questions.add("butterfly"); questions.add("mouse");
        //END OF ADDED.

        Collections.shuffle(questions);
        Collections.shuffle(questions);
        Collections.shuffle(questions);
        Collections.shuffle(questions);
       // Collections.shuffle(questions);

        trainings.add("blicket"); trainings.add("dax"); trainings.add("tunk"); trainings.add("tanzar"); trainings.add("lep");
        trainings.add("tima"); trainings.add("wug"); trainings.add("pank"); trainings.add("koba"); trainings.add("zav");

        trainings2.add("blickets"); trainings2.add("daxs"); trainings2.add("tunks");  trainings2.add("tanzars");  trainings2.add("leps");
        trainings2.add("timas"); trainings2.add("wugs"); trainings2.add("panks"); trainings2.add("kobas"); trainings2.add("zavs");





        //Sets up the string names from our hashmap. We use index to increment up the string arrays.
        //correct = questions.get(0);
        myLayout = new RelativeLayout(this);
        if (!menu) {
            Intent j = new Intent(this, GetInfo.class);
            startActivityForResult(j, 88); //Make a log of request codes!!!
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (88) : {
                if (resultCode == Activity.RESULT_OK) {
                    curr_ID = data.getStringExtra("RowID");
                    //Log.d("SerahTag", curr_ID);
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (correct == name1) {
            curr = up;
            curr_name = name1;
            target_position = target_position + "Up \n";
        } else {
            curr = down;
            curr_name = name2;
            target_position = target_position + "Down \n";
        }


        if (v != curr) {
//            Toast.makeText(UpDown.this,
//                    "This is not the " + correct + ", please try again!", Toast.LENGTH_SHORT).show();
            gotRight = true;
            accurate = false; //ADDED
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
            accurate = true;
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
//        Toast.makeText(UpDown.this,
//                "Wow, you got it " + gotRight + "!" + " Elapsed seconds: " + elapsedMillis / 1000.0, Toast.LENGTH_SHORT).show();

        /**
         * gotRight = whether he/she got it right or not.
         * curr = correct image.
         * v = image the person clicked.
         * elapsedMillis = reaction time.
         */

       // accuracy = accuracy + gotRight+"\n";
        accuracy = accuracy + accurate +"\n"; //ADDED!
        time = time + elapsedMillis / 1000.0 + "\n";
        corr_img = corr_img + curr_name + "\n";

        curr.setClickable(true);
        delay.postDelayed(this, 1500);
    }
    private boolean trainer() {
        System.out.println("Currently, the total is...." + total + " and trainingDone is " + trainingDone);
        if (total >= 10 && !trainingDone) {
            end();
            finish();
            kill = true;
            return false;
        }
        else if (total < 10 && rights == 3){
            trainingDone = true;
            return false;
        } else {
            return true;
        }
    }

    private void instruction() {
        if (questions.size() == 0 || trainings.size() == 0) {
            end();
            finish();
            //return;
        }
        //smile.setVisibility(View.INVISIBLE);
        String blicket = questions.get(0);
        System.out.println("The size of trainings is ...." + trainings.size());
        if (trainer()) {
            Random rand = new Random();
            int index = rand.nextInt(2);
//            if (index == 0) {
//                correct = trainings.get(0);
//            } else {
//                correct = trainings2.get(0);
//            }
            correct = trainings.get(0);
            blicket = correct;
        }
        if(!kill) {
            Intent i = new Intent(this, InstructionActivity.class);
            i.putExtra("blicket", blicket);
            i.putExtra("train", trainer());
            startActivity(i);
        }
    } //z
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
