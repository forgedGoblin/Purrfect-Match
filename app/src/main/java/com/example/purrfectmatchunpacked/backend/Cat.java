    package com.example.purrfectmatchunpacked.backend;

    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;

    import androidx.annotation.NonNull;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.firestore.QuerySnapshot;

    import java.util.ArrayList;
    import java.util.concurrent.atomic.AtomicBoolean;

    public class Cat {
        public boolean isAdopted;
        public String age;
        public byte[] image;
        public String organization;
        public String name;
        public String sex;
        public String ID;
        public String getURL(){
            return "gs://purrfect-match-b4b6d.appspot.com/cats/" + ID;
        }

        public Bitmap getBitmap(){
            return BitmapFactory.decodeByteArray(this.image, 0, image.length);
        }


        public static ArrayList<Cat> getCats(){
            ArrayList<Cat> catList = new ArrayList<>();
    //        AtomicBoolean willReturn = new AtomicBoolean(false);
            var dbRef = Globals.db.collection("users").document("dustin@gmail.com");
            dbRef.get().addOnCompleteListener(i -> {
                Cat cat = new Cat();
                cat.name = (String)i.getResult().get("name");
                cat.age = (String)i.getResult().get("age");
                cat.sex = (String)i.getResult().get("sex");
                cat.isAdopted = (boolean) i.getResult().get("isAdopted");
                cat.organization = (String) i.getResult().get("org");
                catList.add(cat);

            }).addOnFailureListener( v->{
                System.out.println(v);
            });
            while (catList.size() == 0) {}
            return catList;
            /*
            dbRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    var queryDocumentSnapshots= task.getResult();
                    for (var i : queryDocumentSnapshots){
                        Cat cat = new Cat();
                        cat.name = (String)i.get("name");
                        cat.age = (String)i.get("age");
                        cat.sex = (String)i.get("sex");
                        cat.isAdopted = (boolean) i.get("isAdopted");
                        cat.organization = (String) i.get("org");
                        cat.ID = (String)i.get("imageID");
                       /* var storage = Globals.storage;
                        var blobRef = storage.getReference().child(cat.getURL());
                        blobRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                cat.image = bytes;
                                catList.add(cat);
                                Globals.loadAwait = true;
                            }
                        }).addOnFailureListener(v -> {
                            Globals.loadAwait = true;
                            System.out.println("Error loading image");
                        });
                    }
                }
            }).addOnFailureListener(v -> {
                System.out.println("Error loading cat");
                willReturn.set(true);
            });
            return catList; */
        }




    }
