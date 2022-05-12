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
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    public static int parseInt(String s){
        int i=Integer.parseInt(s);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_panel);
        getSupportActionBar().hide();
        //User_data q=new User_data();
       // User_data qq=new User_data();
       // qq.getEmail();
       // Toast.makeText(Panel.this, String.valueOf(qq.getEmail()), Toast.LENGTH_SHORT).show();
       // q.setScore(229);
        Bundle arguments = getIntent().getExtras();
        String mail = arguments.get("mail").toString();
        String password = arguments.get("password").toString();
        String uid = arguments.get("uid").toString();
        System.out.println("key is!+++++==="+uid);
        DatabaseReference re=FirebaseDatabase.getInstance().getReference().child("User_data").child(uid).child("score");
        System.out.println("score is===="+re);
        final TextView score= findViewById(R.id.score);
        score.setText(String.valueOf(mCount));
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDbRef = mDatabase.getReference().child("User_data").child(uid).child("score");
        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
// This method is called once with the initial value and again
                // whenever data at this location is updated.
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
//        myRef= FirebaseDatabase.getInstance().getReference();
//        FirebaseUser user = mAuth.getCurrentUser();

//        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                GenericTypeIndicator<User_data> t= new GenericTypeIndicator<User_data>() {};
//              //  ss[0] = snapshot.child("score").getValue(t);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        final TextView first= findViewById(R.id.num1);
        final TextView second= findViewById(R.id.num2);
        final TextView third= findViewById(R.id.answer);
//        final TextView score= findViewById(R.id.score);
//        score.setText(String.valueOf(mCount));
        final TextView sign=findViewById(R.id.sign);
        sign.setText("+");

        final int[] number1 = {new Random().nextInt(6)};
        final int[] number2 = {new Random().nextInt(6)};
        final int[] answer = {new Random().nextInt(11)};


        first.setText(String.valueOf(number1[0]));
        second.setText(String.valueOf(number2[0]));
        third.setText(String.valueOf(answer[0]));
//        score.setText(String.valueOf(mCount));
        Button cor= findViewById(R.id.correct);
        Button incor= findViewById(R.id.incorrect);
//слушатель бд
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference("User_data");

//        ref.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            User_data post = dataSnapshot.getKey(User_data.class);
//            System.out.println("VALUE===== "+post);
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//            System.out.println("The read failed: ");
//        }
//    });

//        ref.child("User_data")
//                .orderByChild("email")
//                .equalTo(mail)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot childSnapshot:dataSnapshot.getChildren()){
//                            cor.setText(childSnapshot.getKey());
//                            incor.setText("123");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        cor.setText("error");
//                    }
//                });

//        ref.child("emailToUid").child(mail).addListenerForSingleValueEvent(new ValueEventListener(){
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        System.out.println("key is====="+key);

//       String key1= reference.push().getKey();
//        DatabaseReference reference1 = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("User_data").child(key1).child("score");
//        reference1.updateChildren(data);
//        if (user != null) {
//
//            System.out.println("VALUE===== "+user.getUid());
//        } else {
//            cor.setText("SOSAT");
//        }


        cor.setOnClickListener(v->{
            Map<String, Object> data= new HashMap<>();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("User_data").child(user.getUid());

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
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("User_data").child(user.getUid());
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


//    public void onClick(View view) {
//        if (view.getId() == R.id.correct) {
//            if (number1 + number2 == answer) {
//                score++;
//                sc.setText(score);
//                number1 = new Random().nextInt(6);
//                number2 = new Random().nextInt(6);
//                answer = new Random().nextInt(11);
//
//
//            } else {
//                score--;
//                sc.setText(score);
//                number1 = new Random().nextInt(6);
//                number2 = new Random().nextInt(6);
//                answer = new Random().nextInt(11);
//            }
//        }
//    }
//        if(view.getId() == R.id.incorrect) {
//            if (number1 + number2 != answer) {
//                score++;
//                sc.setText(score);
//                number1 = new Random().nextInt(6);
//                number2 = new Random().nextInt(6);
//                answer = new Random().nextInt(11);
//
//            } else {
//                score--;
//                sc.setText(score);
//                number1 = new Random().nextInt(6);
//                number2 = new Random().nextInt(6);
//                answer = new Random().nextInt(11);
//
//            }
//
//        }
//    }
}