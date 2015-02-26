GivesMeHopeAndroidClient (Version 2.0.+) (Deprecated)
========================

![Feature Graphic](https://cloud.githubusercontent.com/assets/9499097/6401496/f288cb8c-bdce-11e4-9d6e-1121e2b3c02c.jpg)

An unofficial Gives Me Hope Android client for educational purposes.

This Android application allows the user to browse http://mobile.givesmehope.com/. Thus, it allows them to view the hottest and trending stories, to vote on new stories, and submit their own stories.

Disclaimer: This application was developed by an individual who does not have any affiliation with the content providers Gives Me Hope and affiliates. This application is for educational purposes only.

### APK Download

[**Direct Download**: (Version 2.0.3)](AnthologyForGMH_6.apk)

<a href="https://play.google.com/store/apps/details?id=com.jparkie.givesmehope">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_45.png" />
</a>

## Source

This source is open for anyone as an educational resource for learning. This branch is completely different from the "old-v1" branch as it is newly architecture project.

## Development Process

The second version of the application (2.0) was developed from February 25th, 2015 and pushed to GitHub on February 26th, 2015. The overall development time was approximately twelve hours. This second version was utilized to experiment a suitable modern architecture and development stack for Android development for me. 

Thus, the application utilizes the Model-View-Presenter architectural pattern. I utilized some design patterns like factories to enfore default or initial states to avoid null objects, and an interpreter to parse the HTML from http://mobile.givesmehope.com/ to their respective POJO models. 

All the `Interpreter`s, `Presenter`s, `Service`s, and `View`s were defined with extendable interfaces to enforce a contract of how implementers should function. This potentially allows ease of refactorability or mockability. 

Meanwhile, I utilized Dagger 1 for dependency injection of my `Interpreter`s, `Presenter`s, and `Service`s. For further modularity, instead of using listeners for communications between `View`s, I utilized Otto as an event bus. 

Finally, the repository by which data would be mapped to models was accomplished utilizing RxJava to create Observable APIs whose consumers can specify the concurrency while allowing the data to be highly composable as it is streamed to subscribers.

I experimented with Stetho to monitor network calls using Chrome Dev Tools. This branch is functionally. ~~yet I need to add different build variants for debugging and write some tests.~~ *Edit: I have unit tests for the `data` package to write left.*

### Libraries

- **Appcompat v7 (21.0.3)**: https://developer.android.com/tools/support-library/index.html
- **RxJava (1.0.7) and RxAndroid (0.24.0)**: https://github.com/ReactiveX/RxJava and https://github.com/ReactiveX/RxAndroid
- **Dagger (1.2.2)**: https://github.com/square/dagger
- **OkHttp (2.2.0)**: https://github.com/square/okhttp
- **Okio (1.2.0)**: https://github.com/square/okio
- **Otto (1.3.6)**: https://github.com/square/otto
- **Jsoup (1.8.1)**: http://jsoup.org/
- **ButterKnife (6.1.0)**: https://github.com/JakeWharton/butterknife
- **Picasso (2.5.0)**: https://github.com/square/picasso
- **FloatingActionButton (1.2.0)**: https://github.com/makovkastar/FloatingActionButton
- **Stetho (1.0.0)**: https://github.com/facebook/stetho

## Screen Shots
![1](https://cloud.githubusercontent.com/assets/9499097/4872520/132ad250-61e9-11e4-8137-940962de82e3.png)
![2](https://cloud.githubusercontent.com/assets/9499097/4872519/132629b2-61e9-11e4-871f-ec6849b97189.png)
![3](https://cloud.githubusercontent.com/assets/9499097/4872521/132cf5da-61e9-11e4-968d-8a8d118a0443.png)
![4](https://cloud.githubusercontent.com/assets/9499097/4872522/1335a3d8-61e9-11e4-9b30-d607c9797e7b.png)

## License

    Copyright 2015 Jacob Park
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
