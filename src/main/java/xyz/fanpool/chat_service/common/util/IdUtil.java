package xyz.fanpool.chat_service.common.util;

import com.github.f4b6a3.tsid.TsidCreator;

public class IdUtil {

    // long 타입 식별자 생성
    public static long create() {
        return TsidCreator.getTsid().toLong();
    }
}
