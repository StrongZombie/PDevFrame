#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring
JNICALL Java_hbb_example_test_objload_MainActivity_hello(JNIEnv *env,jobject ) {
             std::string hello ="Hello from C++";
              return env->NewStringUTF(hello.c_str());
}