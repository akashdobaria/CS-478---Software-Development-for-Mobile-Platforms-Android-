# CS-478---Software-Development-for-Mobile-Platforms-Android-

<h3>Project 1</h3>
<i>(Activities, Intents, GUI, User Inputs and Interactions)</i><br><br>
Simple project that is created to understand activitis, intents and user interaction with the application. The app has two activities. On clicking the button on the first activity, it takes user to the 2nd activity. On the 2nd activity the user is asked to enter a name and on clicking the button, the user is taked to the default contacts app of the phone to enter more contact details. The result from the contacts app is read and appropriate message is shown on the screen. <br><br>

<h3>Project 2</h3>
<i>(GeidView, Custom Adapter(Adaptor Design Pattern), ListView, WebView, Context Menu)</i><br><br>
The main activity in the app displays a grid. Each grid cell contains a thumbnail picture of a car above the name of the car’s manufacturer. Each grid cell supports two kinds of functionality, depending on whether an app viewer performs a short vs. a long click on a cell. A short click brings up a new activity that shows the entire picture of the selected car on the entire device display. The user can return to the grid view by selecting the back soft button on the phone. However, if a user clicks on the displayed picture instead, the app  opens a browser activity showing the web site of the car’s manufacturer. A long click on a grid cell brings up a context menu showing the following three options for the car under consideration:
<br>(1) View the entire picture
<br>(2) Show the official web page of the car manufacturer in a new activity
<br>(3) bring up a list containing the names and addresses of at least three car dealers for the selected manufacturer in greater Chicago area. When user clicks on a phone number or the address shown in the list, a phone dialer or defaul map app is opened. <br><br>

<h3>Project 3</h3>
<i>(Fragments, Broadcast Receiver, Permissions, ListView, WebView, GridView)</i><br>
<h4>Application 1 </h4>
This app consists of a single activity containing two fragments. The first fragment consists of a list naming few Chicago landmarks. When user selects any landmark, the selected item stays highlighted and the second fragment displays the web page of the highlighted item. The app also defines an options menu with two items: exiting the application and launching the application2. Application1 launches Application2 by sending a system broadcast. When the device is in portrait mode the two fragments are displayed on different screens: First, the device will show only the first fragment and when the user selects a landmark, the the first fragment disappears and the second fragment is shown. Pressing the back soft button on the device, will return the device to the original configuration (first fragment only). When the device is in landscape mode, the app initially shows only the first fragment across the entire width of the screen. As soon as a user selects an item, the first fragment is shrunk to about 1/3 of the screen’s width. This fragment will appear in the left-hand side of the screen, with the second fragment taking up the remaining 2/3 of the display on the right. The state of application is retained across device rotations (the selected list item (in the first fragment) and the page displayed in the second fragment will be kept during configuration changes).
<h4>Application 2 </h4>
Application 2 is a picture repository as an Android Gallery. The gallery shows images from each of the landmarks contained in Application 1. Application 1 first starts the gallery in Application 2 by sending a global broadcast. A2 contains a broadcast receiver with appropriate filters to catch Application 1’s broadcast. When it receives a broadcast, Application 2 launches the gallery. In addition, Application 2 defines a new, dangerous-level permission. The broadcast receiver contained in Application 2 requires that the broadcast sender own this permission in order to respond to a broadcast.<br><br>

<h3>Project 4</h3>
<i>(Threading, Looper, Handlers)</i><br>
The project is to implement a GUESS FOUR game. In the game There are two opponents each guesses an initial 4 digit number with no repeatative digit. Then each player guess the oponent's number and the first one to guess the correct number wins. During the game the players are also given feedback about the number they guessed like correct digits and correct digits at correct positions. Each player has its own thread and they communicate with the UI thread using messages.<br><br>

<h3>Project 5</h3>
<i>(Bound service, AIDL, Shared Preferences, Network I/O, JSON)</i><br><br>
<h4>Application 1 </h4>
The first app acts as a client for the bound service in the Application 2. This app has two activities. The main acivity defines appropriate widgets in order to allow a user to select and enter input for one of the three APIs supported by the service in Application 2. When user submits the query, activity forwards a request to the service and reads the response and stores in local data structure. The app also has a widget to unbind the bound service. Finally the activity has another widget that calls the second activity and that consists of two fragments that are displayed side by side in 1:3 ratio.The left fragment contains history list of all query mades and when the user selects any one of them, fragment on the right will display the results that were received from the service.
<h4>Application 2 </h4>
This application defines a bound service with 3 APIs that can be used by the clients and an activity which shows the status of the service. In order to keep track of status of the service the app uses SharedPreferences which is accessed by service in order to update its status based on the method called and by the activity to read the status of the service. The application also defines an AIDL in order to allow inter process communication with the clients.
