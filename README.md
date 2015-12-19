# **# Gender Equality Infographic #** #

# Overview #
This application produces a single interactive infographic of the data extracted from the World Bank data-set. Primarily targeted for non-government and non-profit organisations like the UK Women’s institute, who support gender equality. Users are constantly challenged to keep themselves equipped with statistical information. This is not helped by the lack of interactive tools for data analysis. To encourage this development, the United Nations Statistics Division launched a data visualisation challenge as part of the celebration around World Statistics day. 
Our objective in this project is to make an infographic that delivers to the needs of our end users. It was therefore important we use indicators that are relevant for our target audience. To aid our choice, we have taken inspiration from the Millennium Development Goals of the Millennium Project initiated by the UN. To support this requirement, the following indicators have been selected:

* Literacy rate, males (15-24)

* Literacy rate, females (15-25)

* Persistence to grade 5 for males

* Persistence to grade 5 for females

* Women in wage employment in non-agricultural sector

* Seats held by women in national parliament

The infographic retrieves data in real-time and allows users to interact and explore the data for the selected country and selected indicator. This is made possible as we our infographic maps data to different locations allowing the finding of patterns and correlations spatially, and according to time easily. The application therefore facilitates a granular understanding of patterns and provides insights over and above a simple chart. 

### Hardware Requirements ###

Android Tablets,compatible with API 17 and above


### External Libraries ###

Listed below are the set of external libraries used:
* gson-2.4 – library for parsing JSON
* MPAndroidChart – for making pie charts
* Google Maps Android API – to display an interactive and exploratory world map.


### Installation Steps ###

The following steps must be carried out to set-up the application before running it:
1. Unzip the source code in a folder of your choice

2. Import the application in Android Studio

3. Since, the application uses the Google Maps Android API, it is required to register the application on the Google Developers Console, and get a Google API Key which would require to be added to the app. This process can be broken down into the following steps:

 * Generating the SHA1 Fingerprint key. This depends on whether you are using Mac or Windows:

	1. Mac Users: Open the terminal, and execute the following command keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android

	2. Windows Users: Navigate to the java bin directory via the command line. A typical install of java can be found in C:\Program Files\Java\jdk1.7.0_71\bin. Then execute the following command 
keytool -list -v –keystore c:/users/your_user_name/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
 
* Using an appropriate browser, navigate to https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend&keyType=CLIENT_SIDE_ANDROID&reusekey=true
 and follow the instruction on screen. This will further generate an Application API Key.

 * The last step is to add the key to the application:
	
       1. In AndroidManifest.xml, replace the “YOUR_API_KEY” with the above generated key in the value attribute as displayed below: 
<meta-data
android:name = “com.google.android.geo.API_KEY”
android:value = “YOUR_API_KEY”/>

       2. Under …res/values/google_maps_api.xml replace the existing key with the API key.

       3. Save and re-build the application. 

 * The application is now ready to use.