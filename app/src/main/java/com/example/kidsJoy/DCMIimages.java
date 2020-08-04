package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DCMIimages extends AppCompatActivity {
    Button uploadImg,dwnldImg;
    ImageView img;

    private StorageReference Folder;
    FirebaseDatabase fdatabase,fdatabase2;
    DatabaseReference ref,ref2,ref3;

    RecyclerView recyclerView;
    ArrayList<imageModel> listImages;
    myAdapterImagesDC adapterX;


    DayCaremodel dayCaremodel;
    imageModel modelImg;


    private static final int ImageBack = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcmiimages);


        recyclerView = (RecyclerView)findViewById(R.id.RVimagesCenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listImages = new ArrayList<imageModel>();


        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        fdatabase = FirebaseDatabase.getInstance();
        ref = fdatabase.getReference("DayCares");

        uploadImg = (Button)findViewById(R.id.newImages);
       // dwnldImg = (Button)findViewById(R.id.viewImages);
       // img = (ImageView) findViewById(R.id.pico);






        FirebaseDatabase.getInstance().getReference("DayCares")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("images")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){

                            modelImg = ds.getValue(imageModel.class);
                            listImages.add(modelImg);
                        }
                        if(listImages.isEmpty()){
                            Toast.makeText(DCMIimages.this, "You have not Uploaded any Picture", Toast.LENGTH_SHORT).show();
                        }
                        adapterX = new myAdapterImagesDC(DCMIimages.this,listImages);
                        recyclerView.setAdapter(adapterX);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





    }

    public void uploadImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/");
        startActivityForResult(intent,ImageBack);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ImageBack){
            if(resultCode==RESULT_OK){
                Uri ImageData = data.getData();

                StorageReference imageName = Folder.child("image"+ImageData.getLastPathSegment());

                imageName.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                imageModel modelImg = new imageModel(uri.toString());
                                FirebaseDatabase.getInstance().getReference("DayCares")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("images").push().setValue(modelImg)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(DCMIimages.this, "Uploaded Success", Toast.LENGTH_SHORT).show();
                                                }
                                                if(task.isSuccessful()==false){
                                                    Toast.makeText(DCMIimages.this, "NOT Uploaded", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });


                                String currentUserID = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
                                Map<String, Object> update = new HashMap<>();
                                update.put("/" + currentUserID + "/imageUrl",String.valueOf(uri));
                                ref.updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(DCMIimages.this, "ImageUrl Updated", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                });

            }
        }

    }

    /*
    public void YourImages(View view) {



        FirebaseDatabase.getInstance().getReference("DayCares")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("images")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){

                            modelImg = ds.getValue(imageModel.class);
                            listImages.add(modelImg);
                        }
                        if(listImages.isEmpty()){
                            Toast.makeText(DCMIimages.this, "You have not Uploaded any Picture", Toast.LENGTH_SHORT).show();
                        }
                        adapterX = new myAdapterImagesDC(DCMIimages.this,listImages);
                        recyclerView.setAdapter(adapterX);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });










        fdatabase2 = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref2 = fdatabase2.getReference("DayCares").child(userId);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable <DataSnapshot> allData = dataSnapshot.getChildren();

                String url = dataSnapshot.child("imageUrl").getValue().toString();
                Picasso.get().load(url).into(img);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

     */


}
