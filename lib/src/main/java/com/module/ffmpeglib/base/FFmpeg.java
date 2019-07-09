package com.module.ffmpeglib.base;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.module.ffmpeglib.utils.CmdBuilder;

public abstract class FFmpeg {

    static {
        System.loadLibrary("ffmpeg");
    }

    /**
     * An alias of the native run() constructed by CmdBuilder.
     * @param cmd
     * @return Program exit code.
     */
    public int runCmd(@NonNull CmdBuilder.FFmpegCmd cmd) {
        if (!cmd.isBuilt()) {
            cmd.build();
        }
        return run(cmd.getExecCmd());
    }

    /**
     * Run in command-line style.
     * @param cmd
     * @return Program exit code.
     */
    public native int run(@NonNull String[] cmd);

    /**
     * Get current compiled ABI.
     * @return ABI (armeabi/armeabi-v7a/arm64-v8a etc.)
     */
    public native String getCompiledABI();
}
