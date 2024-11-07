package com.gaboj1.tcr.mixin;

import com.mojang.serialization.Codec;
import org.spongepowered.asm.mixin.Mixin;

/**
 * 移除size的限制
 */
@Mixin(value = Codec.class, remap = false)
public class CodecMixin {

}
