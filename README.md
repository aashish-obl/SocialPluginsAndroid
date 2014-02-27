SocialPluginsAndroid
====================

##Initial setup:

- Create new Project in Eclipse.
- To authenticate and communicate with the Google+ APIs,you must first register your digitally signed .apk file's public certificate in the Google APIs Console:
   (for ref: https://developers.google.com/+/mobile/android/getting-started).
   Once you get the client id your project is registered successfully.
   
- Import google-play-service_lib or directly download from https://developers.google.com/+/downloads/
- Import GooglePlusLibrary project.
- Add GooglePlusLibray into your Main project
    ######To add a reference to a library project, follow these steps:

    * Make sure that both the project library and the application project that depends on it are in your workspace. If one of the projects is missing, import it into your workspace.
    * In the Package Explorer, right-click the dependent project and select Properties.
    * In the Properties window, select the "Android" properties group at left and locate the Library properties at right.
    * Click Add to open the Project Selection dialog.
    * From the list of available library projects, select a project and click OK.
    * When the dialog closes, click Apply in the Properties window.
    * Click OK to close the Properties window.
    
- Declare permissions
    * <code> &lt;uses-permission android:name="android.permission.INTERNET"&gt; </code>
    * <code> &lt;uses-permission android:name="android.permission.GET_ACCOUNTS"&gt; </code>
    * <code> &lt;uses-permission android:name="android.permission.USE_CREDENTIALS"&gt; </code>
    
##API Documentation 

####Google+ Library:

You can see the demo code present in SampleFacebookCode Folder.

######Login/Logout:
* `OBLGooglePlusLogin`
     - Refer to **[OBLGooglePlus.java](https://github.com/ObjectLounge/SocialPluginsAndroid/blob/beta/library/GooglePlus/Classes/OBLGooglePlusLogin.java)**
     - OBLGooglePlusLogin class provides basic utilites like login, logout.
     - User has to call loginUsingInstalledApp(Context context,Activity activity) from the Main Activity to create the object of PlusClient if the object is created successfully the 
    







