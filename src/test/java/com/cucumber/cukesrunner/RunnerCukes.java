package com.cucumber.cukesrunner;

import com.cucumber.parallel.CucumberParallelThread;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Test;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by saikrisv on 30/03/16.
 */
public class RunnerCukes {

    @Test
    public void testCukesRunner() throws Exception {
        File dir = new File(System.getProperty("user.dir") + "/src/test/java/output/");
        System.out.println("Getting all files in " + dir.getCanonicalPath() + " including those in subdirectories");
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File file : files) {
        Path path = Paths.get(file.getAbsoluteFile().toString());
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.add(0, "package output;");
        lines.add(12, "plugin = {\"com.cucumber.listener.ExtentCucumberFormatter:\"},");
        Files.write(path, lines, StandardCharsets.UTF_8);
        FileChannel channel = new RandomAccessFile(file.getAbsoluteFile(), "rw").getChannel();
        FileLock lock = channel.lock();
        try{
            lock = channel.tryLock();
            System.out.println("File is locked"+file.getAbsoluteFile().toString());
            // Ok. You get the lock
        } catch (OverlappingFileLockException e) {
            // File is open by someone else
            System.out.println("File is open"+file.getAbsoluteFile().toString());
        } finally {
            System.out.println("Finally Locking the file"+file.getAbsoluteFile().toString());
            lock.release();
        }
            System.gc();
    }
        CucumberParallelThread cucumberParallelThread = new CucumberParallelThread();
        cucumberParallelThread.distributeTests(1);
    }
}
