package org.tnmk.practice.springgrpc.downloadfileserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tnmk.common.utils.IOUtils;

import java.lang.invoke.MethodHandles;

@Service
public class DownloadFileService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String FILE_PATH = "/samplefiles/SampleFile.txt";

    public byte[] getFileData(String fileName) {
        logger.info("You are downloading the file "+fileName+"? Sorry, I only have this file '"+FILE_PATH+"'");
        return IOUtils.loadBinaryFileInClassPath(FILE_PATH);
    }
}
