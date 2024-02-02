package com.gaboj1.tcr.worldgen.tf;

import java.util.Optional;

@SuppressWarnings({"JavadocReference", "unused", "RedundantSuppression", "deprecation"})
public class ASMHooks {

    /**
     * Minecraft Overworld seed, unique and from the save's WorldOptions. A deep bastion for supporting many features unique to the Twilight Forest dimension.
     */
    public static long seed;

    /**
     * Injection Point:<br>
     * {@link net.minecraft.world.level.levelgen.WorldOptions#WorldOptions(long, boolean, boolean, Optional)} <br>
     * [BEFORE FIRST PUTFIELD]
     */
    public static long seed(long seed) {
        ASMHooks.seed = seed;
        return seed;
    }

}
