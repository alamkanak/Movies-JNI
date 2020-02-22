#include <jni.h>
#include <string>
#include "movie_controller.hpp"

extern "C" JNIEXPORT jstring JNICALL
Java_com_raquib_movies_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
