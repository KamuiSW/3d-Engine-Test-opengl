package com.base.engine.Utils;

import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;

public class Utils {
    public static FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }
}
