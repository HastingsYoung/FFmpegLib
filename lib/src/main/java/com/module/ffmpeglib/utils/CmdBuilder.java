package com.module.ffmpeglib.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class CmdBuilder {

    private final static String CMD_FFMPEG = "ffmpeg";
    private final static String CMD_FFPROBE = "ffprobe";

    private final static String FLAG_FMT = "-f";
    private final static String FLAG_INPUT = "-i";
    private final static String FLAG_OVERWRITE_OUTPUT = "-y";
    private final static String FLAG_KEEP_EXISTING_OUTPUT = "-n";
    private final static String FLAG_SEEK = "-ss";
    private final static String FLAG_FRAMESD = "-frames:d";
    private final static String FLAG_FRAMESV = "-frames:v";
    private final static String FLAG_FRAMESA = "-frames:a";
    private final static String FLAG_CODECV = "-codec:v";
    private final static String FLAG_CODECA = "-codec:a";
    private final static String FLAG_DURATION_LIMIT = "-t";

    private CmdBuilder() {
    }

    public static FFmpegCmd newCmd() {
        return new FFmpegCmd();
    }

    public static class FFmpegCmd extends ArrayList<String> {

        private ArrayList<String> inputStreams;
        private LinkedHashSet<String> inputArgs;
        private LinkedHashSet<String> outputArgs;
        private String outputPath;

        private ArrayList<String> fullCmd;
        private boolean built;


        private FFmpegCmd() {
            inputStreams = new ArrayList<String>();
            inputArgs = new LinkedHashSet<>();
            outputArgs = new LinkedHashSet<>();
            built = false;
        }

        public boolean isBuilt() {
            return built;
        }

        public FFmpegCmd addInputPath(String inputPath) {
            inputStreams.add(FLAG_INPUT);
            inputStreams.add(inputPath);
            return this;
        }

        public FFmpegCmd setOutputPath(String outputPath) {
            this.outputPath = outputPath;
            return this;
        }

        public FFmpegCmd setOverwriteOutput(boolean isTrue) {
            if (isTrue) {
                outputArgs.add(FLAG_OVERWRITE_OUTPUT);
                return this;
            }
            outputArgs.add(FLAG_KEEP_EXISTING_OUTPUT);
            return this;
        }

        public FFmpegCmd setInputSeekPoint(float s) {
            inputArgs.add(FLAG_SEEK);
            inputArgs.add(new DecimalFormat("0.##").format(s));
            return this;
        }

        public FFmpegCmd setInputSeekPoint(int hr, int min, float sec) {
            inputArgs.add(FLAG_SEEK);
            inputArgs.add(hr + ":" + min + ":" + new DecimalFormat("0.##").format(sec));
            return this;
        }

        public FFmpegCmd setOutputSeekPoint(float s) {
            outputArgs.add(FLAG_SEEK);
            outputArgs.add(new DecimalFormat("0.##").format(s));
            return this;
        }

        public FFmpegCmd setOutputSeekPoint(int hr, int min, float sec) {
            outputArgs.add(FLAG_SEEK);
            outputArgs.add(hr + ":" + min + ":" + new DecimalFormat("0.##").format(sec));
            return this;
        }

        public FFmpegCmd setInputDurLimit(float s) {
            inputArgs.add(FLAG_DURATION_LIMIT);
            inputArgs.add(new DecimalFormat("0.##").format(s));
            return this;
        }

        public FFmpegCmd setInputDurLimit(int hr, int min, float sec) {
            inputArgs.add(FLAG_DURATION_LIMIT);
            inputArgs.add(hr + ":" + min + ":" + new DecimalFormat("0.##").format(sec));
            return this;
        }

        public FFmpegCmd setOutputDurLimit(float s) {
            outputArgs.add(FLAG_DURATION_LIMIT);
            outputArgs.add(new DecimalFormat("0.##").format(s));
            return this;
        }

        public FFmpegCmd setOutputDurLimit(int hr, int min, float sec) {
            outputArgs.add(FLAG_DURATION_LIMIT);
            outputArgs.add(hr + ":" + min + ":" + new DecimalFormat("0.##").format(sec));
            return this;
        }

        public FFmpegCmd setDataOutputFrames(int f) {
            outputArgs.add(FLAG_FRAMESD);
            outputArgs.add(Integer.toString(f));
            return this;
        }

        public FFmpegCmd setVideoOutputFrames(int f) {
            outputArgs.add(FLAG_FRAMESV);
            outputArgs.add(Integer.toString(f));
            return this;
        }

        public FFmpegCmd setAudioOutputFrames(int f) {
            outputArgs.add(FLAG_FRAMESA);
            outputArgs.add(Integer.toString(f));
            return this;
        }

        public FFmpegCmd build() {
            if (inputStreams.size() < 1 || null == outputPath)
                throw new Error("You must specify input path and output path to continue.");
            fullCmd = new ArrayList<String>();
            fullCmd.add(CMD_FFMPEG);
            fullCmd.addAll(inputArgs);
            fullCmd.addAll(inputStreams);
            fullCmd.addAll(outputArgs);
            fullCmd.add(outputPath);
            built = true;
            return this;
        }

        public String[] getExecCmd() {
            String[] strArr = new String[fullCmd.size()];
            fullCmd.toArray(strArr);
            return strArr;
        }
    }
}
