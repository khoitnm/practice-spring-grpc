package org.tnmk.practice.springgrpc.streamdownloadzipfilesserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.tnmk.common.utils.IOUtils;
import org.tnmk.common.utils.UnexpectedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.lang.invoke.MethodHandles;

@Service
public class StreamDownloadZipFilesService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String FILE_PATH = "/samplefiles/SampleFile.txt";

    public InputStream downloadZipFile(long numZipEntries) {
        logger.info("You are downloading a zip file with {} entries", numZipEntries);
        InputStream inputStream = IOUtils.validateExistInputStreamFromClassPath(FILE_PATH);
        return inputStream;
    }
}
