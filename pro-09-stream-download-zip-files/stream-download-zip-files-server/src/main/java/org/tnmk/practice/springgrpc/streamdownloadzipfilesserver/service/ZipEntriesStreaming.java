package org.tnmk.practice.springgrpc.streamdownloadzipfilesserver.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.tnmk.common.utils.IOUtils;
import org.tnmk.common.utils.UnexpectedException;

@Service
public class ZipEntriesStreaming {
    public static final Logger logger = LoggerFactory.getLogger(ZipEntriesStreaming.class);
    public static final String SOURCE_FILE_NAME = "SampleFile";
    public static final String SOURCE_FILE_EXTENSION = ".txt";
    public static final String SOURCE_FILE_PATH = "/samplefiles/" + SOURCE_FILE_NAME + SOURCE_FILE_EXTENSION;

    /**
     *
     * @param numZipEntries
     * @param zipOutputStream
     * @param pipedInputStreamForZipFile this is just for logging so that we can understand more about the process. It has nothing to oo with the actual logic.
     */
    @Async
    public void asyncStreamEntriesData(long numZipEntries, ZipOutputStream zipOutputStream, PipedInputStream pipedInputStreamForZipFile) {
        //TODO can we add zip entries in parallel?
        for (int i = 0; i < numZipEntries; i++) {
            String destinationFileFullName = SOURCE_FILE_NAME + i + SOURCE_FILE_EXTENSION;
            try {
                logger.info("ZipEntry: {}\t creating zipEntry: start. pipedInputStreamForZipFile.available {}", destinationFileFullName, pipedInputStreamForZipFile.available());
                ZipEntry zipEntry = addZipEntry(destinationFileFullName, zipOutputStream);
                logger.info("ZipEntry: {}\t creating zipEntry: end {}", destinationFileFullName, zipEntry);


                logger.info("ZipEntry: {}\t copy data from zipEntry to parent zipOutput: start. pipedInputStreamForZipFile.available {} ", destinationFileFullName, pipedInputStreamForZipFile.available());
                int copiedBytes = copyZipEntryFileDataToZipOutputStream(SOURCE_FILE_PATH, zipOutputStream);
                logger.info("ZipEntry: {}\t copy data from zipEntry to parent zipOutput : end {}. pipedInputStreamForZipFile.available {}", destinationFileFullName, copiedBytes, pipedInputStreamForZipFile.available());


                zipOutputStream.closeEntry();
                logger.info("ZipEntry: {}\t closed entry. pipedInputStreamForZipFile.available {}", destinationFileFullName, pipedInputStreamForZipFile.available());
            } catch (IOException e) {
                throw new UnexpectedException("Cannot close zipEntry" + destinationFileFullName + ": " + e, e);
            }
        }
        logger.info("Finish adding zip entries");
        closeZipOutputStream(zipOutputStream, pipedInputStreamForZipFile);
//        return new AsyncResult<>(zipOutputStream);
    }

    private void closeZipOutputStream(ZipOutputStream zipOutputStream, PipedInputStream pipedInputStreamForZipFile) {
        // DO NOT need to flush output stream
        try {
            logger.info("pipedInputStreamForZipFile.available BEFORE closing zipOutputStream: " + pipedInputStreamForZipFile.available());

            zipOutputStream.close();
            logger.info("Close zipOutputStream.");

            logger.info("pipedInputStreamForZipFile.available AFTER closing zipOutputStream: " + pipedInputStreamForZipFile.available());

            //We MUST close stream. Otherwise, the client app will wait for it until timeout!?
        } catch (IOException e) {
            throw new UnexpectedException("Cannot close zipOutputStream: " + e.getMessage(), e);
        }
    }

    private ZipEntry addZipEntry(String zipEntryFileName, ZipOutputStream zipOutputStream) {
        ZipEntry zipEntry = new ZipEntry(zipEntryFileName);
        try {
            zipOutputStream.putNextEntry(zipEntry);
        } catch (IOException e) {
            throw new UnexpectedException("Cannot create new zipEntry " + zipEntryFileName + ": " + e, e);
        }
        return zipEntry;
    }

    private int copyZipEntryFileDataToZipOutputStream(String zipEntryFilePath, ZipOutputStream zipOutputStream) {
        try (InputStream zipEntryInputStream = IOUtils.validateExistInputStreamFromClassPath(zipEntryFilePath)) {
            //TODO Should we copy stream data in chunks?
            return StreamUtils.copy(zipEntryInputStream, zipOutputStream);
        } catch (IOException e) {
            throw new UnexpectedException("Cannot read data from zip entry file " + zipEntryFilePath + ": " + e, e);
        }
    }
}
