//
// Created by Administrator on 2020/6/12.
//

#ifndef TEST_I_DECODER_H
#define TEST_I_DECODER_H

#endif //TEST_I_DECODER_H
class IDecoder{
public :
    virtual void GoOn() = 0;
    virtual void Pause() = 0;
    virtual void Stop() = 0;
    virtual bool IsRunning() = 0;
    virtual long GetDuration() = 0;
    virtual long GetCurPos() = 0;
};