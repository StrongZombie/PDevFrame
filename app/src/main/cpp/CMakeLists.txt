# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

#1.定义so库和头文件所在目录
set(ffmpeg_lib_dir ${CMAKE_SOURCE_DIR}/../jniLibs/armeabi-v7a)
set(ffmpeg_head_dir ${CMAKE_SOURCE_DIR}/ffmpeg)

#2.添加头文件目录
include_directories(${ffmpeg_head_dir}/include)

#3.添加ffmpeg相关so库
#######Libavutil.so
add_library(
        avutil
        SHARED
        IMPORTED
)
set_target_properties( avutil
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libavutil.so )

#######ffmpeg.include.libswresample.so
add_library( swresample
        SHARED
        IMPORTED )
set_target_properties( swresample
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libswresample.so )

######ffmpeg.include.libavcodec.so
add_library( avcodec
        SHARED
        IMPORTED )
set_target_properties( avcodec
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libavcodec.so )


######ffmpeg.include.libavfilter.so
add_library( avfilter
        SHARED
        IMPORTED)
set_target_properties( avfilter
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libavfilter.so )

######ffmpeg.include.libswscale.so
add_library( swscale
        SHARED
        IMPORTED)
set_target_properties( swscale
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libswscale.so )


#####ffmpeg.include.libavformat.so
add_library( avformat
        SHARED
        IMPORTED)
set_target_properties( avformat
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libavformat.so )

#####ffmpeg.include.libavdevice.so
add_library( avdevice
        SHARED
        IMPORTED)
set_target_properties( avdevice
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libavdevice.so )


add_library( # Sets the name of the library.
            main

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             main.cpp )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

# 查找代码中使用到的系统库
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.


#连接FFmpeg相关库
target_link_libraries( # Specifies the target library.
                        main
                #FFmpeg 相关库
#                        avutil
#                        swresample
#                        avcodec
#                        avfilter
#                        swscale
#                        avformat
#                        avdevice
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )