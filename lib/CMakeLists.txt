cmake_minimum_required(VERSION 3.4.1)

set(CPP_DIR ${CMAKE_SOURCE_DIR}/src/main/cpp)
set(LIBS_DIR ${CMAKE_SOURCE_DIR}/src/main/jniLibs)
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${LIBS_DIR}/${ANDROID_ABI})

# 添加库
add_library(
        ffmpeg
        SHARED
        ${CPP_DIR}/cmdutils.c
        ${CPP_DIR}/ffmpeg.c
        ${CPP_DIR}/ffmpeg_filter.c
        ${CPP_DIR}/ffmpeg_opt.c
        ${CPP_DIR}/ffmpeg_cmd.c)

# 用于各种类型声音、图像编解码
add_library(avcodec
        SHARED
        IMPORTED)

set_target_properties(avcodec
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/${ANDROID_ABI}/libavcodec.so)

# 用于各种音视频封装格式的生成和解析，读取音视频帧等功能
add_library(avformat
        SHARED
        IMPORTED)

set_target_properties(avformat
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/${ANDROID_ABI}/libavformat.so)

# 包含一些公共的工具函数
add_library(avutil
        SHARED
        IMPORTED)

set_target_properties(avutil
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/${ANDROID_ABI}/libavutil.so)

# 提供了各种音视频过滤器
add_library(avfilter
        SHARED
        IMPORTED)

set_target_properties(avfilter
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/${ANDROID_ABI}/libavfilter.so)

# 用于音频重采样，采样格式转换和混合
add_library(swresample
        SHARED
        IMPORTED)

set_target_properties(swresample
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/${ANDROID_ABI}/libswresample.so)

# 用于视频场景比例缩放、色彩映射转换
add_library(swscale
        SHARED
        IMPORTED)

set_target_properties(swscale
        PROPERTIES IMPORTED_LOCATION
        ${LIBS_DIR}/${ANDROID_ABI}/libswscale.so)


find_library(log-lib
        log)

include_directories(../ffmpeg-4.0/
        ${CPP_DIR}/include/)

# Include libraries needed for ffmpeg lib
target_link_libraries(ffmpeg
        avcodec
        avformat
        avutil
        avfilter
        swresample
        swscale
        ${log-lib})