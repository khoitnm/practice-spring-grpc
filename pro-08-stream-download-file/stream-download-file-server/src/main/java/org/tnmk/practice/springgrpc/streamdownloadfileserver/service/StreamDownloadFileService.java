package org.tnmk.practice.springgrpc.streamdownloadfileserver.service;

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
public class StreamDownloadFileService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String FILE_PATH = "/samplefiles/SampleFile.txt";

    /**
     * @param fileName
     */
    public InputStream getFileData(String fileName) {
        logger.info("You are downloading the file " + fileName + "? Sorry, I only have this file '" + FILE_PATH + "'");
        InputStream inputStream = IOUtils.validateExistInputStreamFromClassPath(FILE_PATH);
        return inputStream;
    }
}
