package org.tnmk.practice.springgrpc.streamdownloadzipfilesserver.service;

import java.io.*;
import java.util.concurrent.Future;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.tnmk.common.utils.UnexpectedException;

@Service
public class StreamDownloadZipFilesService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int PIPE_BUFFER_SIZE = 100;//16 * 1024;

    @Autowired
    private ZipEntriesStreaming zipEntriesStreaming;

    /**
     * @param numZipEntries
     * @return the InputStream of the zipFile. The client code must close this InputStream after finishing reading this file.
     */
    public InputStream downloadZipFile(long numZipEntries) {
        //TODO try to understand what is Thread interruption
        // Thread.currentThread().isInterrupted()

        logger.info("You are downloading a zip file with {} entries", numZipEntries);
        PipedOutputStream pipedOutputStreamForZipFile = new PipedOutputStream();

        //It looks like using BufferOutputStream for PipedOutputStream is redundant?
        ZipOutputStream zipOutputStream = new ZipOutputStream(pipedOutputStreamForZipFile);
        zipOutputStream.setMethod(ZipOutputStream.DEFLATED);

        PipedInputStream pipedInputStreamForZipFile = connectOutputStreamToInputStream(pipedOutputStreamForZipFile, PIPE_BUFFER_SIZE);

        //zipEntriesStreaming must close the zipOutputStream because this method doesn't know when to close that outputstream, it's the asynchronous process.
        //TODO actually, we can use Future function to close zipOutputStream here.
        zipEntriesStreaming.asyncStreamEntriesData(numZipEntries, zipOutputStream, pipedInputStreamForZipFile);
//        future.addCallback(
//                new ListenableFutureCallback<ZipOutputStream>() {
//                    @Override
//                    public void onFailure(Throwable ex) {
//                        closeZipOutputStream(zipOutputStream, pipedInputStreamForZipFile);
//                    }
//
//                    @Override
//                    public void onSuccess(ZipOutputStream result) {
//                        closeZipOutputStream(result, pipedInputStreamForZipFile);
//                    }
//                }
//        );

        //NOTE: The client code MUST close the InputStream, hence will close pipedOutputStreamForZipFile, too!
//        pipedOutputStreamForZipFile.close();
//        zipOutputStream.close();
        logger.info("Created pipedInputStreamForZipFile {} with BufferSize {}", pipedInputStreamForZipFile, PIPE_BUFFER_SIZE);
        return pipedInputStreamForZipFile;
    }

    private PipedInputStream connectOutputStreamToInputStream(PipedOutputStream pipedOutputStream, int bufferSize) throws
            UnexpectedException {
        try {
            PipedInputStream pipedInputStreamForZipFile = new PipedInputStream(pipedOutputStream, bufferSize);
            return pipedInputStreamForZipFile;
        } catch (IOException e) {
            throw new UnexpectedException("Cannot connect pipedOutputStream to InputStream: " + e, e);
        }
    }
}
