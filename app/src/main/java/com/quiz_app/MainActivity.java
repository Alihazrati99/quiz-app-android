package com.quiz_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener,DialogInterface.OnDismissListener {
    private Button truebtn;
    private Button falsebtn;
    ImageView imgbtn;
    int curr_question=0;
    int user_score=0;
    TextView txtview;
ProgressBar pgbar;
    Quiz_Questions[] questions=new Quiz_Questions[]
            {new Quiz_Questions(R.string.Q_1,true,R.color.c1),
                    new Quiz_Questions(R.string.Q_2,false,R.color.c2),
                    new Quiz_Questions(R.string.Q_3,true,R.color.c3),
                    new Quiz_Questions(R.string.Q_4,false,R.color.c4),
                    new Quiz_Questions(R.string.Q_5,true,R.color.c5),
                    new Quiz_Questions(R.string.Q_6,false,R.color.c6),
            };
     int Progress=(int) Math.ceil(100/questions.length);

     Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        String lang=Locale.getDefault().getLanguage();
      //  Toast.makeText(this, ""+lang, Toast.LENGTH_SHORT).show();
        if (!lang.equals("en")){
            Set_Language1(activity);
        }
        setContentView(R.layout.activity_main2);
        if (!lang.equals("en")){

        }
        txtview=findViewById(R.id.textView);
        pgbar=findViewById(R.id.progressBar);
        txtview.setText(questions[curr_question].getQuestionID());
        falsebtn=findViewById(R.id.btnfalse);
        truebtn=findViewById(R.id.btntrue);
        imgbtn=findViewById(R.id.imageButton);
        falsebtn.setOnClickListener(this);
        truebtn.setOnClickListener(this);
        imgbtn.setOnClickListener(this);
        int n=getSharedPreferences("Quiz", Context.MODE_PRIVATE).getInt("Number",0);
      //  Toast.makeText(this, ""+n, Toast.LENGTH_SHORT).show();
        if (n>0){
            Quiz_Questions[] arr=questions;
            Quiz_Questions[] arr2=new Quiz_Questions[n];
            for (int i=0;i<n;i++){
                arr2[i]=arr[i];
            }
            questions=arr2;
            Progress=(int) Math.ceil(100/questions.length);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnfalse:
                checkanswer(false);
                break;
            case R.id.btntrue:
                checkanswer(true);
                break;
            case R.id.imageButton:
                curr_question=(curr_question+1)%questions.length;
                updatequestion();
                break;

        }

    }


    private void checkanswer(boolean right_answer) {
        Boolean rightAnswer = questions[curr_question].isAnswer();
        if (right_answer == rightAnswer) {
            Toast.makeText(MainActivity.this, R.string.Right_ans, Toast.LENGTH_SHORT).show();
            user_score=user_score+1;
            curr_question=(curr_question+1)%questions.length;
            updatequestion();
        } else {
            Toast.makeText(MainActivity.this, R.string.Wrong_ans, Toast.LENGTH_SHORT).show();
            curr_question=(curr_question+1)%questions.length;
            updatequestion();
        }
    }
    private void updatequestion() {
        txtview.setText(questions[curr_question].getQuestionID());
        txtview.setBackgroundColor(questions[curr_question].getQuestionID());
        pgbar.incrementProgressBy(Progress);
        if (curr_question==0){
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Quiz has been finished\nResult");
            alert.setMessage("Your Score is:"+user_score+" out of "+questions.length);
            alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String date_time=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(new Date());
                    SaveModel saveModel=new SaveModel(""+user_score,date_time,""+System.currentTimeMillis(),""+questions.length);
                    Gson gson=new Gson();
                    String json=getSharedPreferences("Quiz",MODE_PRIVATE).getString("result","");
                    ArrayList<SaveModel> saveModelArrayList=new ArrayList<>();
                    if (!json.equals("")){
                        ArrayList<SaveModel> lstArrayList2 = gson.fromJson(json,
                                new TypeToken<List<SaveModel>>(){}.getType());
                        if (lstArrayList2.size()>0){
                            saveModelArrayList.addAll(lstArrayList2);
                        }
                    }
                    saveModelArrayList.add(saveModel);
                    String s=gson.toJson(saveModelArrayList);
                    getSharedPreferences("Quiz",MODE_PRIVATE).edit().putString("result",s).commit();
                    Toast.makeText(MainActivity.this, "Result Saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();
                }
            });
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.ava){
            Gson gson=new Gson();
            String json=getSharedPreferences("Quiz",MODE_PRIVATE).getString("result","");
            ArrayList<SaveModel> saveModelArrayList=new ArrayList<>();
            if (!json.equals("")){
                ArrayList<SaveModel> lstArrayList2 = gson.fromJson(json,
                        new TypeToken<List<SaveModel>>(){}.getType());
                if (lstArrayList2.size()>0){
                    saveModelArrayList.addAll(lstArrayList2);
                    int t=0,t1=0;
                    for(int i=0;i<saveModelArrayList.size();i++){
                        t=t+Integer.parseInt(saveModelArrayList.get(i).getScore());
                        t1=t1+Integer.parseInt(saveModelArrayList.get(i).getTotal());
                    }
                    float n1=(float) t;
                    float n2=(float) t1;
                    float ave=n1/n2;
                    DecimalFormat df = new DecimalFormat("0.00");
                    AlertDialog.Builder alert=new AlertDialog.Builder(this);
                    alert.setTitle("Average Score");
                    alert.setMessage("Your Average Score "+(int)n1+"/"+(int)n2+" : "+df.format(ave));
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.show();
                }else {
                    Toast.makeText(this, "No Any Result Saved", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "No Any Result Saved", Toast.LENGTH_SHORT).show();
            }
        }
        if (item.getItemId()==R.id.reset){
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Reset Result");
            alert.setMessage("Are You Sure You Want To Reset Result...?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getSharedPreferences("Quiz",MODE_PRIVATE).edit().putString("result","").commit();
                    Toast.makeText(MainActivity.this, "Reset Successfully", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert.show();
        }
        if (item.getItemId()==R.id.num){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            DialogFragment dialogFragment = new Dialog_Number();
            dialogFragment.show(ft, "dialog");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        startActivity(new Intent(MainActivity.this,MainActivity.class));
        finish();
    }
    public static void  Set_Language1(Activity activity){
        Locale myLocale = new Locale("ar");
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

}