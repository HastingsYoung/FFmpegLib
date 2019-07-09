package com.module.ffmpeglib.operators;

import com.module.ffmpeglib.base.FFmpeg;

public class VideoOperator extends FFmpeg {

    private static volatile VideoOperator instance = null;

    private VideoOperator() { }

    public static VideoOperator getInstance() {
        if (null == instance) {
            synchronized(VideoOperator.class) {
                if (null == instance) {
                    instance = new VideoOperator();
                }
            }
        }
        return instance;
    }

    // todo: add high-level video operation shortcuts
}