//
// Created by Administrator on 2020/6/12.
//

#ifndef TEST_BASE_DECODER_H
#define TEST_BASE_DECODER_H

#include <jni.h>
#include <string>
#include <thread>
#include "../../utils/logger.h"
#include "i_decoder.h"
#include "decode_state.h"

extern "C" {
#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libavutil/frame.h>
#include <libavutil/time.h>
};
#include "i_decoder.h"
#include "../../ffmpeg/include/libavformat/avformat.h"

class base_decoder : public IDecoder{
private:
    const char *TAG = "BaseDecoder";
    //解码信息上下文
    AVFormatContext *m_format_ctx = NULL;

};


#endif //TEST_BASE_DECODER_H
