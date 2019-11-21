package org.tnmk.practice.springgrpc.downloadfileserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tnmk.common.utils.IOUtils;

import java.lang.invoke.MethodHandles;

import static org.apache.commons.io.Charsets.UTF_8;

@Service
public class DownloadFileService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String FILE_PATH = "/samplefiles/SampleFile.txt";

    public byte[] getFileData(String fileName) {
        logger.info("You are downloading the file " + fileName + "? Sorry, I only have this file '" + FILE_PATH + "'");
        //return IOUtils.loadBinaryFileInClassPath(FILE_PATH);
        String fileContent = generateFileString();
        return generateFileDataFromText(fileContent);
    }

    private String generateFileString(){
        String fileContent = "title; I called just to say I love you";
        return fileContent;
    }

    private byte[] generateFileDataFromText(String text) {
        return text.getBytes(UTF_8);
    }
}
