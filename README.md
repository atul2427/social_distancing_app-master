SoDapp - A Social Distancing App
----------------------------------------------------
This is SoDapp - a new app which helps you keep track of your social distance when outside.

### How it works
The app uses bluetooth to get nearby devices. It uses location information to detect if you have visited new places. Data analytics is performed on the server, to get the results based on age, gender, occupation etc. No data is sold or shared - everything remians anonymous.

### UI/UX
The UI is very minimalistic and easy to use. The screens are clutter free, with refreshing color schemes.

### Future features
Cough detection implementation is planned!

### Testing
1. For testing, you can reduce the crowd threshold by reducing the value of 
the crowdThreshold variable in the MapsActivityCurrentPlace.java. You can also remove the filters
to include all bluetooth devices in the onReceive() function in the BluetoothService file.  
2. You need to enter your own Google Maps API key with Places API and Maps API enabled.