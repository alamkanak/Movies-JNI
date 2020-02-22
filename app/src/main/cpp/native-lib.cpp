#include <jni.h>
#include <string>
#include "movie_controller.hpp"

static jclass jclass_movie;
static jclass jclass_detail;
static jclass jclass_actor;
static jmethodID jmethod_movie_init;
static jmethodID jmethod_movie_name;
static jmethodID jmethod_movie_lastupdated;
static jmethodID jmethod_detail_init;
static jmethodID jmethod_detail_name;
static jmethodID jmethod_detail_score;
static jmethodID jmethod_detail_actors;
static jmethodID jmethod_detail_description;
static jmethodID jmethod_actor_init;
static jmethodID jmethod_actor_name;
static jmethodID jmethod_actor_age;
static jmethodID jmethod_actor_image_url;

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    // Get POJO class names.
    jclass_movie = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("com/raquib/movies/model/Movie")));
    jclass_detail = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("com/raquib/movies/model/MovieDetail")));
    jclass_actor = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("com/raquib/movies/model/Actor")));

    if (jclass_movie  == nullptr) {
        return JNI_ERR;
    }

    // Get method IDs of Movie POJO.
    jmethod_movie_init = env->GetMethodID(jclass_movie, "<init>", "()V");
    jmethod_movie_name = env->GetMethodID(jclass_movie, "setName", "(Ljava/lang/String;)V");
    jmethod_movie_lastupdated = env->GetMethodID(jclass_movie, "setLastUpdated", "(I)V");

    // Get method IDs of MovieDetail POJO.
    jmethod_detail_init = env->GetMethodID(jclass_detail, "<init>", "()V");
    jmethod_detail_name = env->GetMethodID(jclass_detail, "setName", "(Ljava/lang/String;)V");
    jmethod_detail_score = env->GetMethodID(jclass_detail, "setScore", "(F)V");
    jmethod_detail_actors = env->GetMethodID(jclass_detail, "setActors", "([Lcom/raquib/movies/model/Actor;)V");
    jmethod_detail_description = env->GetMethodID(jclass_detail, "setDescription", "(Ljava/lang/String;)V");

    // Get mthod IDs of Actor POJO.
    jmethod_actor_init = env->GetMethodID(jclass_actor, "<init>", "()V");
    jmethod_actor_name = env->GetMethodID(jclass_actor, "setName", "(Ljava/lang/String;)V");
    jmethod_actor_age = env->GetMethodID(jclass_actor, "setAge", "(I)V");
    jmethod_actor_image_url = env->GetMethodID(jclass_actor, "setImageUrl", "(Ljava/lang/String;)V");
    return JNI_VERSION_1_6;
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_com_raquib_movies_JniHelper_getMovies(JNIEnv *env, jobject) {
    movies::MovieController* controller = new movies::MovieController();
    const std::vector<movies::Movie *> movies = controller->getMovies();
    jobjectArray jmovies = env->NewObjectArray(movies.size(), jclass_movie, 0);
    for (int i = 0; i < movies.size(); i++) {
        jobject jmovie = env->NewObject(jclass_movie, jmethod_movie_init);
        env->CallVoidMethod(jmovie, jmethod_movie_name, movies[i]->name.c_str());
        env->CallVoidMethod(jmovie, jmethod_movie_lastupdated, movies[i]->lastUpdated);
        env->SetObjectArrayElement(jmovies, i, jmovie);
    }
    return jmovies;
}

extern "C" JNIEXPORT jobject JNICALL Java_com_raquib_movies_JniHelper_getMovieDetail(JNIEnv *env, jobject, jstring jmovieName) {
    
}