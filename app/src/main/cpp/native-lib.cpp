#include <jni.h>
#include <string>
#include "movie_controller.hpp"

static jclass jclass_movie;
static jmethodID jmethod_movie_init;
static jmethodID jmethod_movie_setname;
static jmethodID jmethod_movie_setlastupdated;
static jmethodID jmethod_detail_init;
static jmethodID jmethod_detail_setname;
static jmethodID jmethod_detail_score;
static jmethodID jmethod_detail_actors;
static jmethodID jmethod_detail_description;

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    // Find your class. JNI_OnLoad is called from the correct class loader context for this to work.
    jclass_movie = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("com/raquib/movies/model/Movie")));

    if (jclass_movie  == nullptr) {
        return JNI_ERR;
    }

    jmethod_movie_init = env->GetMethodID(jclass_movie, "<init>", "()V");
    jmethod_movie_setname = env->GetMethodID(jclass_movie, "setName", "(Ljava/lang/String;)V");
    jmethod_movie_setlastupdated = env->GetMethodID(jclass_movie, "setLastUpdated", "(I)V");

    jmethod_detail_init = env->GetMethodID(jclass_movie, "<init>", "()V");
    jmethod_detail_setname = env->GetMethodID(jclass_movie, "setName", "(Ljava/lang/String;)V");
    jmethod_detail_score = env->GetMethodID(jclass_movie, "setScore", "(F)V");
    jmethod_detail_actors = env->GetMethodID(jclass_movie, "setActors", "([Lcom/raquib/movies/model/Actor;)V");
    jmethod_detail_description = env->GetMethodID(jclass_movie, "setDescription", "(Ljava/lang/String;)V");

    return JNI_VERSION_1_6;
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_com_raquib_movies_JniHelper_getMovies(JNIEnv *env, jobject) {

}

extern "C" JNIEXPORT jobject JNICALL Java_com_raquib_movies_JniHelper_getMovieDetail(JNIEnv *env, jobject, jstring movieName) {

}