package com.hashtagco.bussinesclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity  {
/*

    private static final int MY_CAM_REQUST_CODE = 555;
    private static final int MY_RSULT_LAOD_TAG = 666;
    String email;
    */
/*@BindView(R.id.toolbar)
    MaterialToolbar materialToolbar;*//*


    @BindView(R.id.image_preview)
    ImageView image_preview;

    @BindView(R.id.image_image)
    ImageView image_image;

    @BindView(R.id.image_camera)
    ImageView image_camera;

    @BindView(R.id.image_send)
    ImageView image_send;

    @BindView(R.id.txt_message)
    AppCompatEditText txt_message;


  RecyclerView recyclerViewcha;
    FirebaseAuth auth;

   // Token serverToken;
    LinearLayoutManager linearLayoutManager;

    Uri fileUri;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference chatRef, offSetRef;
    DatabaseReference admins;
    IloadTimeFromFirebaseListner listner;

    FirebaseRecyclerAdapter<ChatMessageModel, RecyclerView.ViewHolder> adapter;
    FirebaseRecyclerOptions<ChatMessageModel> options;
    StorageReference storageReference;

    @OnClick(R.id.image_send)
    void onSubmitChatClick() {

        offSetRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                long offset = snapshot.getValue(Long.class);
                long estimatedServerTimeInMs = System.currentTimeMillis() + offset;
                listner.onLoadOnlyTimeSuccess(estimatedServerTimeInMs);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @OnClick(R.id.image_image)
    void onSelectImageClick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, MY_RSULT_LAOD_TAG);
    }

    @OnClick(R.id.image_camera)
    void onCaptureImageClick() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, MY_CAM_REQUST_CODE);
    }
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
       /* recyclerViewcha = findViewById(R.id.recyclerView_chat);
        recyclerViewcha.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerViewcha.setLayoutManager(linearLayoutManager);
        listner = this;
        firebaseDatabase = FirebaseDatabase.getInstance();

        offSetRef = firebaseDatabase.getReference(".info/serverTimeOffset");
       // initViews();
//linearLayoutManager = new LinearLayoutManager(this);
    //    ButterKnife.bind(this);

       loadChatContent();*/


    }

    /*private void loadChatContent() {
        DatabaseReference referenceTokens = FirebaseDatabase.getInstance().
                getReference("Admin");
        Query queryData = referenceTokens.orderByChild("email")
                .equalTo(true);// get All node isServerToken is True


        queryData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Admin serverToken = snapshot.getValue(Admin.class);
                    Common.currentAdmin = serverToken;
                }

                chatRef = firebaseDatabase.getReference("ResturantRef")
                        .child(Common.currentAdmin.getEmail());
               *//* Query query = chatRef.child(Common.generateChatRoomId
                        (Common.currentoken.getToken(), Common.currentUser.getUid()));*//*
                Query query = chatRef;

                options = new FirebaseRecyclerOptions.
                        Builder<ChatMessageModel>().setQuery
                        (query, ChatMessageModel.class).build();
                adapter = new FirebaseRecyclerAdapter<ChatMessageModel, RecyclerView.ViewHolder>(options) {

                    @Override
                    public int getItemViewType(int position) {
                        return adapter.getItem(position).isIspicture() ? 1 : 0;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                                    int position, @NonNull ChatMessageModel model) {
                        if (holder instanceof ChatTextHolder) {
                            ChatTextHolder chatTextHolder = (ChatTextHolder) holder;
                            chatTextHolder.txt_email.setText(model.getName());
                            chatTextHolder.txt_chat_message.setText(model.getContent());
                            chatTextHolder.txt_time.setText(
                                    DateUtils.getRelativeTimeSpanString(model.getTimestamp(),
                                            Calendar.getInstance().getTimeInMillis(), 0).toString());
                        } else {
                            ChatPictureHolder chatPictureHolder = (ChatPictureHolder) holder;
                            chatPictureHolder.txt_email.setText(model.getName());
                            chatPictureHolder.txt_chat_message.setText(model.getContent());
                            chatPictureHolder.txt_time.setText(
                                    DateUtils.getRelativeTimeSpanString(model.getTimestamp(),
                                            Calendar.getInstance().getTimeInMillis(), 0).toString());
                            Glide.with(ChatActivity.this)
                                    .load(model.getPicturlink())
                                    .into(chatPictureHolder.image_preview);
                        }
                    }

                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                        View view;
                        //Log.d("Chat", String.valueOf(viewType));
                        if (viewType == 0) {
                            view = LayoutInflater.from(viewGroup.getContext())
                                    .inflate(R.layout.layout_mssag_txt, viewGroup, false);
                            return new ChatTextHolder(view);
                        } else {
                            view = LayoutInflater.from(viewGroup.getContext())
                                    .inflate(R.layout.layout_mssag_picture, viewGroup, false);
                            return new ChatPictureHolder(view);
                        }
                    }
                };

                adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onItemRangeInserted(int positionStart, int itemCount) {
                        super.onItemRangeInserted(positionStart, itemCount);
                        int friendlyMessageCount = adapter.getItemCount();
                        int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                        if (lastVisiblePosition == -1 ||
                                (positionStart >= (friendlyMessageCount - 1) &&
                                        lastVisiblePosition == (positionStart - 1))) {
                            recyclerViewcha.scrollToPosition(positionStart);
                        }

                    }
                });
                adapter.startListening();
                recyclerViewcha.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }


        });



    }








    @Override
    public void onLoadOnlyTimeSuccess(long estimateTimeInMs) {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setName(Common.currentUser.getName());
        chatMessageModel.setContent(txt_message.getText().toString());
        chatMessageModel.setTimestamp(estimateTimeInMs);
        if (fileUri == null) {
            chatMessageModel.setIspicture(false);
            submitChatToFirebase(chatMessageModel, chatMessageModel.isIspicture());
        } else {
            uploadPicture(fileUri, chatMessageModel);
        }
    }



    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private File getOutputMediaFile() {
        File mediastoreDir = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES), "EatItV2");
        if (!mediastoreDir.exists()) {
            if (!mediastoreDir.mkdir())
                return null;
        }
        String time_stamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());
        File mediafile = new File(new StringBuilder
                (mediastoreDir.getPath()).append(File.separator).append("IMG_")
                .append(time_stamp).append("_")
                .append(new Random().nextInt()).append(".jpg").
                        toString());
        return mediafile;
    }


    private void uploadPicture(Uri fileUri, ChatMessageModel chatMessageModel) {

        if (fileUri != null) {
            AlertDialog dialog = new AlertDialog.Builder(ChatActivity.this).
                    setCancelable(false).setMessage("Please wait ...").create();
            dialog.show();
            String fileName = Common.getFileName
                    (getContentResolver(), fileUri);
            String path = new StringBuilder(Common.currentoken.getToken())
                    .append("/").append(fileName).toString();
            storageReference = FirebaseStorage.getInstance().getReference(path);
            UploadTask uploadTask = storageReference.putFile(fileUri);
            Task<Uri> task = uploadTask.continueWithTask(task1 -> {
                if (!task1.isSuccessful())
                    Toast.makeText(this, "Faild Uploading .."
                            , Toast.LENGTH_SHORT).show();
                return storageReference.getDownloadUrl();
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Uri> task2) {
                    if (task2.isSuccessful()) {
                        String url = task2.getResult().toString();
                        dialog.dismiss();
                        chatMessageModel.setPicturlink(url);
                        chatMessageModel.setIspicture(true);
                        submitChatToFirebase(chatMessageModel, chatMessageModel.isIspicture());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void submitChatToFirebase(ChatMessageModel chatMessageModel,
                                      boolean ispicture) {

        DatabaseReference referenceTokens = FirebaseDatabase.getInstance().
                getReference("Admin");
        Query queryData = referenceTokens.orderByChild("email")
        .equalTo(true);// get All node isServerToken is True


        queryData.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
      Admin   serverToken = snapshot.getValue(Admin.class);
        Common.currentAdmin = serverToken;
    }
    chatRef = firebaseDatabase.getReference("ResturantRef")
            .child(Common.currentAdmin.getEmail());
           chatRef .push().setValue(chatMessageModel).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull @NotNull Exception e) {
            Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull @NotNull Task<Void> task) {
            if (task.isSuccessful()) {
                txt_message.setText("");
                txt_message.requestFocus();
//                if (adapter != null) {
//                    adapter.notifyDataSetChanged();
//                    if (ispicture) {
//                        fileUri = null;
//                        //  img_
//                    }
//                }
            }
        }
    });
}

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });





}




    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_CAM_REQUST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = null;
                ExifInterface exifInterface = null;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap
                            (getContentResolver(), fileUri);
                    exifInterface = new ExifInterface(getContentResolver().openInputStream(fileUri));
                    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION
                            , ExifInterface.ORIENTATION_UNDEFINED);
                    Bitmap rotatbitmap = null;
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotatbitmap = rotatbitmap(bitmap, 90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotatbitmap = rotatbitmap(bitmap, 180);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotatbitmap = rotatbitmap(bitmap, 270);
                            break;
                        default:
                            rotatbitmap = bitmap;
                            break;
                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == MY_RSULT_LAOD_TAG) {
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imagUri = data.getData();
                        InputStream inputStream = getContentResolver().openInputStream(imagUri);
                        Bitmap selctdImage = BitmapFactory.decodeStream(inputStream);
                        image_preview.setImageBitmap(selctdImage);
                        image_preview.setVisibility(View.VISIBLE);
                        fileUri = imagUri;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private Bitmap rotatbitmap(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate(i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.
                getWidth(), bitmap.getHeight(), matrix, true);
    }



    //Stop Adapter
   *//* @Override
    protected void onStop() {
        super.onStop();
            adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();

           adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();

            adapter.startListening();

    }*/
}