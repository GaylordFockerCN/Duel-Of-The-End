package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class Book extends WrittenBookItem {
    String BOOK_AUTHOR;
    String key;
    public Book(String key) {
        super((new Item.Properties()).stacksTo(16));
        this.key = key;
        BOOK_AUTHOR = TheCasketOfReveriesMod.MOD_ID + ".book.author." + key;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        System.out.println("used");
        ItemStack book = player.getItemInHand(hand);
        ListTag bookPages = new ListTag();

        for (int i = 1; i <= 2; i++)
            bookPages.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable(TheCasketOfReveriesMod.MOD_ID + ".book." + key + "." + i))));

        book.addTagElement("pages", bookPages);
        book.addTagElement("generation", IntTag.valueOf(3));
        book.addTagElement("author", StringTag.valueOf(BOOK_AUTHOR));
        book.addTagElement("title", StringTag.valueOf(TheCasketOfReveriesMod.MOD_ID + ".book." + key));
        return super.use(level, player, hand);
    }
}
