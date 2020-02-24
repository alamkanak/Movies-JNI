#include <jni.h>
#include <string>
#include "movie_controller.hpp"

static jclass jclass_movie;
static jclass jclass_detail;
static jclass jclass_actor;
static jmethodID jmethod_movie_init;
static jmethodID jmethod_detail_init;
static jmethodID jmethod_actor_init;
movies::MovieController *controller;

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    // Initialize movie controller.
    controller = new movies::MovieController();

    // Get POJO class names.
    jclass_movie = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("com/raquib/movies/model/Movie")));
    jclass_detail = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("com/raquib/movies/model/MovieDetail")));
    jclass_actor = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("com/raquib/movies/model/Actor")));

    if (jclass_movie  == nullptr) {
        return JNI_ERR;
    }

    // Get init method IDs of the POJOs.
    jmethod_movie_init = env->GetMethodID(jclass_movie, "<init>", "(Ljava/lang/String;I)V");
    jmethod_actor_init = env->GetMethodID(jclass_actor, "<init>", "(Ljava/lang/String;ILjava/lang/String;)V");
    jmethod_detail_init = env->GetMethodID(jclass_detail, "<init>", "(Ljava/lang/String;F[Lcom/raquib/movies/model/Actor;Ljava/lang/String;)V");
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return;
    }

    env->DeleteGlobalRef(jclass_movie);
    env->DeleteGlobalRef(jclass_detail);
    env->DeleteGlobalRef(jclass_actor);
}

extern "C" JNIEXPORT jobjectArray JNICALL Java_com_raquib_movies_utils_JniHelper_getMovies(JNIEnv *env, jobject) {
    const std::vector<movies::Movie *> movies = controller->getMovies();
    jobjectArray jmovies = env->NewObjectArray(movies.size(), jclass_movie, 0);
    for (int i = 0; i < movies.size(); i++) {
        jobject jmovie = env->NewObject(jclass_movie, jmethod_movie_init, env->NewStringUTF(movies[i]->name.c_str()), movies[i]->lastUpdated);
        env->SetObjectArrayElement(jmovies, i, jmovie);
    }
    return jmovies;
}

extern "C" JNIEXPORT jobject JNICALL Java_com_raquib_movies_utils_JniHelper_getMovieDetail(JNIEnv *env, jobject, jstring jmovieName) {
    const char *movieName = env->GetStringUTFChars(jmovieName, 0);
    movies::MovieDetail *detail = controller->getMovieDetail(movieName);

    jobjectArray jactors = env->NewObjectArray(detail->actors.size(), jclass_actor, 0);
    for (int i = 0; i < detail->actors.size(); i++) {
        jobject jactor = env->NewObject(jclass_actor, jmethod_actor_init, env->NewStringUTF(detail->actors[i].name.c_str()), detail->actors[i].age, env->NewStringUTF(detail->actors[i].imageUrl.c_str()));
        env->SetObjectArrayElement(jactors, i, jactor);
    }
    jobject jdetail = env->NewObject(jclass_detail, jmethod_detail_init, env->NewStringUTF(detail->name.c_str()), detail->score, jactors, env->NewStringUTF(detail->description.c_str()));
    return jdetail;
}
