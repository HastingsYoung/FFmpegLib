## FFmpegLib

A Android wrapper module of the leading multimedia framework [FFmpeg](https://ffmpeg.org/about.html).

### FFmpeg Version

This library is dependent on `FFmpeg 4.0`.

### Arch Supports
1. armeabi-v7a
2. arm64-v8a

### Installation
1. Download and build the project from **Android Studio**->**Build**->**Rebuild Project**.
2. Under the root directory of `lib`, browse to `lib/build/outputs/aar`, the AAR library should have already been generated.
3. Copy the AAR file to your project's `libs` directory under `app`, for example:

```
app
|---build
|   build.gradle
|---libs
|   |   lib-arm8-debug.aar
|   |   ...
|
|   proguard-rules.pro
|---src
|	|	...
|
|
gradle
|
build.gradle
```

### API Document

```java
final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();


// Option 1: via CmdBuilder
final CmdBuilder.FFmpegCmd cmd = CmdBuilder.newCmd()
                .addInputPath(PATH + "/DCIM/Camera/potato.mp4")			// accepts input stream(s) from storage
                .setVideoOutputFrames(50)								// sets the number of video frames to output
                .setOverwriteOutput(true)								// overwrites if the output file already exists
                .setOutputPath(PATH + "/DCIM/Camera/potato_100.gif");	// sets the output path in storage

int resultCode = VideoOperator.getInstance().runCmd(cmd);


// Option 2: via raw FFmpeg command-line options if you're more familiar with the native API calls
String[] cmd = new String[]{
	"ffmpeg",
	"-i",
	PATH + "/DCIM/Camera/potato.mp4",
	"-frames:v",
	"50",
	"-y",
	PATH + "/DCIM/Camera/potato_100.gif"
}

int resultCode = VideoOperator.getInstance().run(cmd);
```