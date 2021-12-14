package com.hashtagco.bussinesclient;


public class TrackingShipper {//implements OnMapReadyCallback {

   /* private GoogleMap mMap;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 11101;
    private SupportMapFragment mapFragment;
    private Switch btnSwitch;
    private Button btnCallClint;
    //----------------------------
    private FirebaseDatabase database;
    private DatabaseReference refDriversLocation;

    //-------------
    Marker markerYorShipper;
    Marker markerClintLocation;

    LocationRequest locationRequest;
    LocationCallback locationCallback;
    FusedLocationProviderClient fusedLocationProviderClient;
    GeoFire geoFireDataBase; //هخزن فيها خطوط الطول والعرض للمكان

    //---------Direction--------//
    LatLng shipperCurrentLocation, clintCurrentLocation;
    //-----------------
    String shipperPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_shipper);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        initViews();

       //-------init--------//
        database = FirebaseDatabase.getInstance();
        refDriversLocation = database.getReference(Common.DRIVER_LOCATION);



      fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
      geoFireDataBase = new GeoFire(refDriversLocation);

        //--Get Constant Location Clint
        if (getIntent() != null)
        {

            String latLngClint = getIntent().getStringExtra("Latlng");
            shipperPhone = getIntent().getStringExtra("shipperPhone");
            String[] separatedLocation = latLngClint.split(",");

            separatedLocation[0] = separatedLocation[0].trim();
            separatedLocation[1] = separatedLocation[1].trim();

           clintCurrentLocation = new LatLng(Double.parseDouble(separatedLocation[0]),
                  Double.parseDouble(separatedLocation[1]));
        }

        //--------Check open GPS ------------//
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }



    private void initViews() {
        //-------------Id------------------//
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnSwitch = (Switch) findViewById(R.id.pin);

         btnCallClint = (Button) findViewById(R.id.btnCall);


        //------Event---------------//

        //---Run Map and Gel your Location and Draw Rout to ClintLocation
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked) //True
                {
                    startGettingLocation();
                } else //False
                {

                    stopGettingLocation();
                }


            }
        });


        btnCallClint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                String uri = "tel:" + shipperPhone.trim();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));

                if (ActivityCompat.checkSelfPermission(TrackingShipper.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                    return;
                }
                startActivity(intent);



            }
        });


    }

    //---------Get Your Location-------//
    private void startGettingLocation()
    {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this
                    ,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}
                    ,MY_PERMISSIONS_REQUEST_LOCATION);
        }else
        {
            prepareLocationRequest();
            prepareCallBack();
            fusedLocationProviderClientPermission();
        }


    }

    private  void stopGettingLocation()
    {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        fusedLocationProviderClient.removeLocationUpdates(locationCallback); //هوقف الresponse اللى راجع من السيرفر
        mMap.setMyLocationEnabled(false);

        Snackbar.make(mapFragment.getView(),"You are disabled",Snackbar.LENGTH_SHORT).show();


    }

    private void fusedLocationProviderClientPermission()
    {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
        mMap.setMyLocationEnabled(true);

        Snackbar.make(mapFragment.getView(),"You are enabled ",Snackbar.LENGTH_SHORT).show();

    }

    private void prepareLocationRequest()
    {
        locationRequest=new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setSmallestDisplacement(.00001f);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    private void prepareCallBack()
    {
        locationCallback=new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult)
            {super.onLocationResult(locationResult);


                    //---Storage Location In Firebase
                    geoFireDataBase
                            .getLocation(shipperPhone, new com.firebase.geofire.LocationCallback() {
                        @Override
                        public void onLocationResult(String key, GeoLocation location)
                        {


                             //Marker Your Location Shipper
                            if (markerYorShipper !=null)//يعنى موجود على الخريطه وواخد location
                                markerYorShipper.remove(); //احذف العلامه ديه عشان هديله الlocation الاحدث لموقعى

                            //Add Marker On Your Location
                            markerYorShipper =mMap.addMarker(new MarkerOptions()
                                    .title("Shipper")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.markershipper))
                                    .position(new LatLng(location.latitude,location.longitude)));

                            //markerYorShipper.setDraggable(true);//عشان محدش يحركه بأيده

                            //معنى انك تضيف الanimatCamer هنا انك هتعملzome على الyourLocation بالمسافه اللى حددتها
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude,
                                    location.longitude),15) );

                            //Marker Clint
                            if (markerClintLocation!=null)//يعنى موجود على الخريطه وواخد location
                                markerClintLocation.remove(); //احذف العلامه ديه عشان هديله الlocation الاحدث لموقعى

                            //Add Clint Marker
                            markerClintLocation=mMap.addMarker(new MarkerOptions()
                                    .title("Your Location")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
                                    .position(new LatLng(clintCurrentLocation.latitude
                                            ,clintCurrentLocation.longitude) ));

                            //Add Circle Around Location Clint
                            CircleOptions circleOptions = new CircleOptions();
                            circleOptions.center(new LatLng(clintCurrentLocation.latitude,clintCurrentLocation.longitude));
                            circleOptions.radius(8500);
                            circleOptions.fillColor(Color.BLUE);
                            circleOptions.strokeColor(Color.RED);
                            circleOptions.strokeWidth(4);

                            mMap.addCircle(circleOptions);



                            //---After Call Back Draw And Chose Route between 2 Points
                            shipperCurrentLocation =new LatLng(location.latitude,location.longitude);


                            //getDirectionAndDraw();
                            if (shipperCurrentLocation!=null&&clintCurrentLocation!=null)
                            {

                                getDirectionAndDraw(shipperCurrentLocation,clintCurrentLocation);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError)
                        {

                        }
                    });

                }
        };


    }

    //Method Open Gps
    private void showGPSDisabledAlertToUser()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:

                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED)
               {
                    prepareLocationRequest();
                    prepareCallBack();
                    fusedLocationProviderClientPermission();

                }else
                {
                    Toast.makeText(this, "محتاجين السماحيات يابيه ", Toast.LENGTH_SHORT).show();
                }

                break;




        }



    }

    private void getDirectionAndDraw(LatLng shipperCurrentLocation, LatLng clintCurrentLocation)
    {
        //المكتبه هتحددلك الاتجاهات فقط وبعدين انت ترسم بأيدك
        GoogleDirection.withServerKey(getResources().getString(R.string.maps_api))
                .from(this.shipperCurrentLocation)
                .to(this.clintCurrentLocation)
                .transportMode(TransportMode.DRIVING) //بتحددله انك راكب عشان يقترحلك الطرق المناسبه
                //.avoid(AvoidType.HIGHWAYS) //بتقوله هنا تجنب الطرق العاليه
                //.avoid(AvoidType.FERRIES)
                .transitMode(TransitMode.BUS)
                .unit(Unit.METRIC)
                .execute(new DirectionCallback() {


                    @Override
                    public void onDirectionSuccess(@Nullable @org.jetbrains.annotations.Nullable
                                                           Direction direction) {
                        if (direction.isOK()) {

                            //قمنا بجلب الـ Leg
                            //  ثم استخدمنا الميثود getDirectionPoint والتى تعطى لنا امكانية الحصول على الاتجاهاات على هيئة مصفوفة من خطوط الطول والعرض المتتالية
                            // ثم قمنا باستخدام الميثود createPolyline الموجودة بكلاس DirectionCoverter والتى أخذت منا باراميترات (الكونتكست ، ليست الاتجاهات ، سمك الخط ، اللون )
                            // ثم قمنا باضافة هذا الـ polyline للخريطة .

                         *//*   Leg leg = direction.getRouteList().get(0).getLegList().get(0);

                            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(TrackingShipper.this, directionPositionList, 5, Color.RED);//.jointType(JointType.ROUND);

                            mMap.addPolyline(polylineOptions);*//*

                            List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                            ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(TrackingShipper.this, stepList, 5, Color.RED, 3, Color.BLUE);

                            for (PolylineOptions polylineOption : polylineOptionList)
                            {
                                mMap.addPolyline(polylineOption);
                            }




                            Log.e("Direction", "onDirectionSuccess: ");
                        } else {

                            Log.e("Direction", direction.getErrorMessage());

                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Log.e("Direction", "faild" + t.getLocalizedMessage());
                    }
                });
    }



*/
}
