package org.tnmk.practice.springgrpc.streamdownloadzipfilesserver.service;

import java.io.*;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import org.tnmk.common.utils.UnexpectedException;

@Service
public class StreamDownloadZipFilesService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int PIPE_BUFFER_SIZE = 16 * 1024;

    @Autowired
    private ZipEntriesStreaming zipEntriesStreaming;

    public InputStream downloadZipFile(long numZipEntries) {
        //TODO try to understand what is Thread interruption
        // Thread.currentThread().isInterrupted()

        logger.info("You are downloading a zip file with {} entries", numZipEntries);
        PipedOutputStream pipedOutputStreamForZipFile = new PipedOutputStream();

        //It looks like using BufferOutputStream for PipedOutputStream is redundant?
        ZipOutputStream zipOutputStream = new ZipOutputStream(pipedOutputStreamForZipFile);
        zipOutputStream.setMethod(ZipOutputStream.DEFLATED);

        PipedInputStream pipedInputStreamForZipFile = connectOutputStreamToInputStream(pipedOutputStreamForZipFile, PIPE_BUFFER_SIZE);

        zipEntriesStreaming.asyncStreamEntriesData(numZipEntries, zipOutputStream);

        //The client code will close the InputStream, hence will close the ZioOutputStream and pipedOutputStreamForZipFile, too!
//        pipedOutputStreamForZipFile.close();
//        zipOutputStream.close();
        return pipedInputStreamForZipFile;
    }

    private PipedInputStream connectOutputStreamToInputStream(PipedOutputStream pipedOutputStream, int bufferSize) throws UnexpectedException{
        try {
            PipedInputStream pipedInputStreamForZipFile = new PipedInputStream(pipedOutputStream, bufferSize);
            return pipedInputStreamForZipFile;
        } catch (IOException e) {
            throw new UnexpectedException("Cannot connect pipedOutputStream to InputStream");
        }
    }
}
