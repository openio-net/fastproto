package com.fastproto.compile;

import io.netty.buffer.ByteBuf;

public interface DecodeMethod {

    void decode(ByteBuf byteBuf,int tag,Object object);
}
