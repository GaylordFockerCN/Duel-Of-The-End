//package com.gaboj1.hdl.item;
//
//import com.sk89q.worldedit.EditSession;
//import com.sk89q.worldedit.WorldEdit;
//import com.sk89q.worldedit.WorldEditException;
//import com.sk89q.worldedit.extension.platform.Actor;
//import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
//import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
//import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
//import com.sk89q.worldedit.function.operation.Operation;
//import com.sk89q.worldedit.function.operation.Operations;
//import com.sk89q.worldedit.math.BlockVector3;
//import com.sk89q.worldedit.session.ClipboardHolder;
//import com.sk89q.worldedit.world.World;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.context.UseOnContext;
//import java.awt.datatransfer.Clipboard;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;

//
//public class builder_bar extends Item {
//
//    public builder_bar() {
//        super(new Properties().stacksTo(1));
//    }

//    @Override
//    public InteractionResult useOn(UseOnContext pContext) {
//        Clipboard clipboard;
//        final String file_directory = "E:\\map\\Final1.schem";
//        File file = new File(file_directory);
//        ClipboardFormat format = ClipboardFormats.findByFile(file);
//
//        if (pContext.getLevel().isClientSide()) {
//            BlockPos positionClicked = pContext.getClickedPos();
//            Player player = (Player) pContext.getPlayer();
//            double x = positionClicked.getX();
//            double y = positionClicked.getY();
//            double z = positionClicked.getZ();
//            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
//                clipboard = (Clipboard) reader.read();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            try (EditSession editSession = WorldEdit.getInstance().newEditSession((World) player)) {
//                Operation operation = new ClipboardHolder((com.sk89q.worldedit.extent.clipboard.Clipboard) clipboard)
//                        .createPaste(editSession)
//                        .to(BlockVector3.at(x, y, z))
//                        .build();
//                Operations.complete(operation);
//            } catch (WorldEditException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return InteractionResult.SUCCESS;
//    }
//}
