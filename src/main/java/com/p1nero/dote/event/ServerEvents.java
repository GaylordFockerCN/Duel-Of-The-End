package com.p1nero.dote.event;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.archive.DOTEArchiveManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

/**
 * 控制服务端SaveUtil的读写
 */
@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID)
public class ServerEvents {

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event){
        copyDuelDirectory(event.getServer());
    }

    public static void copyDuelDirectory(MinecraftServer server) {
        Path gameDir = FMLPaths.GAMEDIR.get();
        Path sourceDir = gameDir.resolve("duel_of_the_end");
        Path savesDir = gameDir.resolve("saves");

        // 检查源目录是否存在
        if (!Files.isDirectory(sourceDir)) {
            System.err.println("源目录不存在: " + sourceDir);
            return;
        }

        //服务端
        if(!Files.exists(savesDir)) {
            copyToDimensions(gameDir.resolve(server.getWorldData().getLevelName()), sourceDir);
        } else {
            try (Stream<Path> saveFolders = Files.list(savesDir)) {
                saveFolders.filter(Files::isDirectory)
                        .forEach(saveFolder -> copyToDimensions(saveFolder, sourceDir));
            } catch (IOException e) {
                DuelOfTheEndMod.LOGGER.error("DOTE: Failed to copy dimension!", e);
            }
        }

    }

    private static void copyToDimensions(Path saveFolder, Path sourceDir) {
        Path dimensionsDir = saveFolder.resolve("dimensions");
        Path targetDir = dimensionsDir.resolve("duel_of_the_end");

        // 若目标目录已存在，则跳过
        if (Files.exists(targetDir)) {
            return;
        }

        try {
            // 确保dimensions目录存在
            Files.createDirectories(dimensionsDir);
            // 递归复制目录
            copyDirectoryRecursive(sourceDir, targetDir);
        } catch (IOException e) {
            DuelOfTheEndMod.LOGGER.error("DOTE: Failed to copy dimension! 复制到 " + targetDir + " 失败！", e);
        }
    }

    private static void copyDirectoryRecursive(Path source, Path target) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path relativePath = source.relativize(dir);
                Path destPath = target.resolve(relativePath);
                Files.createDirectories(destPath); // 创建目标目录
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path destFile = target.resolve(source.relativize(file));
                Files.copy(file, destFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * stop的时候TCRBiomeProvider.worldName已经初始化了，无需处理
     */
    @SubscribeEvent
    public static void onServerStop(ServerStoppedEvent event){
        DOTEArchiveManager.clear();
    }

}
