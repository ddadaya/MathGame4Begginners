package com.example.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Panel extends AppCompatActivity {

    private int mCount;

    private TextView score;
    private Button correct;
    private Button incorrect;

    public static int parseInt(String s){
        int i=Integer.parseInt(s);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_panel);
        getSupportActionBar().hide();

        Bundle arguments = getIntent().getExtras();
        String uid = arguments.get("uid").toString();
        boolean state = (boolean) arguments.get("state");

        score=findViewById(R.id.textView);
        correct=findViewById(R.id.correct);
        incorrect=findViewById(R.id.incorrect);
        if (state){
            score.setText("Текущий счет:");
            correct.setText("верно");
            incorrect.setText("неверно");
        } else {
            score.setText("Total score:");
            correct.setText("correct");
            incorrect.setText("incorrect");
        }

        final TextView score= findViewById(R.id.score);
        score.setText(String.valueOf(mCount));
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDbRef = mDatabase.getReference().child("User_data").child(uid).child("score");
        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                mCount=parseInt(value);
                final TextView score= findViewById(R.id.score);
                score.setText(String.valueOf(mCount));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            // Failed to read value
            }
        });

        final TextView first= findViewById(R.id.num1);
        final TextView second= findViewById(R.id.num2);
        final TextView third= findViewById(R.id.answer);

        final int[] number1 = {new Random().nextInt(6)};
        final int[] number2 = {new Random().nextInt(6)};
        final int[] answer = {new Random().nextInt(11)};

        first.setText(String.valueOf(number1[0]));
        second.setText(String.valueOf(number2[0]));
        third.setText(String.valueOf(answer[0]));
        Button cor= findViewById(R.id.correct);
        Button incor= findViewById(R.id.incorrect);

        cor.setOnClickListener(v->{
            Map<String, Object> data= new HashMap<>();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User_data").child(user.getUid());

            if(number1[0] + number2[0] == answer[0]){
                 mCount++;
                 data.put("score", mCount);
                 reference.updateChildren(data);

                 number1[0] =new Random().nextInt(3);
                 number2[0] =new Random().nextInt(3);
                 answer[0] =new Random().nextInt(4);

                 score.setText(String.valueOf(mCount));
                 first.setText(String.valueOf(number1[0]));
                 second.setText(String.valueOf(number2[0]));
                 third.setText(String.valueOf(answer[0]));
            } else{
                 mCount--;
                 data.put("score", mCount);
                 reference.updateChildren(data);

                 number1[0]=new Random().nextInt(3);
                 number2[0]=new Random().nextInt(3);
                 answer[0]=new Random().nextInt(4);

                 score.setText(String.valueOf(mCount));
                 first.setText(String.valueOf(number1[0]));
                 second.setText(String.valueOf(number2[0]));
                 third.setText(String.valueOf(answer[0]));
            }
        });

        incor.setOnClickListener(v->{
            Map<String, Object> data= new HashMap<>();
            data.put("score", mCount);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User_data").child(user.getUid());
            if(number1[0] + number2[0] != answer[0]){
                mCount++;
                data.put("score", mCount);
                reference.updateChildren(data);

                number1[0]=new Random().nextInt(3);
                number2[0]=new Random().nextInt(3);
                answer[0]=new Random().nextInt(4);

                score.setText(String.valueOf(mCount));
                first.setText(String.valueOf(number1[0]));
                second.setText(String.valueOf(number2[0]));
                third.setText(String.valueOf(answer[0]));
            } else{
                mCount--;
                data.put("score", mCount);
                reference.updateChildren(data);

                number1[0]=new Random().nextInt(3);
                number2[0]=new Random().nextInt(3);
                answer[0]=new Random().nextInt(4);

                score.setText(String.valueOf(mCount));
                first.setText(String.valueOf(number1[0]));
                second.setText(String.valueOf(number2[0]));
                third.setText(String.valueOf(answer[0]));
            }
        });
    }
}