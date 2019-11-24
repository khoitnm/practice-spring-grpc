package org.tnmk.practice.springgrpc.streamdownloadzipfilesserver.service;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.tnmk.common.utils.IOUtils;
import org.tnmk.common.utils.UnexpectedException;

import java.lang.invoke.MethodHandles;

@Service
public class StreamDownloadZipFilesService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String FILE_PATH = "/samplefiles/SampleFile.txt";
    private static final int PIPE_BUFFER_SIZE = 16 * 1024;

    public InputStream downloadZipFile(long numZipEntries) {
        //TODO try to understand what is Thread interruption
        // Thread.currentThread().isInterrupted()

        logger.info("You are downloading a zip file with {} entries", numZipEntries);
        PipedOutputStream pipedOutputStreamForZipFile = new PipedOutputStream();
        PipedInputStream pipedInputStreamForZipFile = new PipedInputStream(pipedOutputStreamForZipFile, PIPE_BUFFER_SIZE);
//        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(pipedOutputStream, PIPE_BUFFER_SIZE));
        //It looks like using BufferOutputStream for PipedOutputStream is redundant?
        ZipOutputStream zipOutputStream = new ZipOutputStream(pipedOutputStreamForZipFile);
        zipOutputStream.setMethod(ZipOutputStream.DEFLATED);
        for (int i = 0; i < numZipEntries; i++) {
            ZipEntry zipEntry = new ZipEntry(FILE_PATH);
            zipOutputStream.putNextEntry(zipEntry);
            InputStream zipEntryInputStream = IOUtils.validateExistInputStreamFromClassPath(FILE_PATH);
            StreamUtils.copy(zipEntryInputStream, zipOutputStream);
            zipEntryInputStream.close();
        }


        return pipedInputStreamForZipFile;
    }
}
