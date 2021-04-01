# FetcherListApp
## Android detection library

This library has to detect whether the user is a humain or a bot while displaying a list of albums.
We relay on 3 detect ways:
* gathering application informations (minSDK, screenSize, versionName...) and send it to a mocked API. Then we compare those informations with previous ones, if massive API call have been sent with that same informations, than it's probably a BOT
* detect scroll movement time intervals, if the intervals remain the same then it's a bot
* a dialog box with simple quizz

<!-- implementation -->
## Implementation
to integrate this library, please add those lines in your root build.gradle at the end of repositories: 
```JS allprojects {
  repositories {
    ...
      maven { url 'https://jitpack.io' }
    }
  }
   ```
and the dependency 
```JS 
dependencies {
  implementation 'com.github.ghadaLah:FetcherListApp:Tag'
} 
