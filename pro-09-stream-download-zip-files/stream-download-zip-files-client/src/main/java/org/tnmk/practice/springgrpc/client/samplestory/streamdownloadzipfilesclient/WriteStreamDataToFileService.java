package org.tnmk.practice.springgrpc.client.samplestory.streamdownloadzipfilesclient;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tnmk.common.utils.UnexpectedException;

import java.io.*;
import java.lang.invoke.MethodHandles;

@Service
public class WriteStreamDataToFileService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public OutputStream createStreamForWritingDataToFile(String fileSystemPath) {
        File file = new File(fileSystemPath);
        if (file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
            logger.info("Created an empty file "+ file.getAbsolutePath());
        } catch (IOException e) {
            throw new UnexpectedException("Cannot create file '" + file.getAbsolutePath() + "'", e);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileSystemPath);
            return fileOutputStream;
        } catch (FileNotFoundException e) {
            throw new UnexpectedException("Cannot create output stream to file '" + fileSystemPath + "'", e);
        }
    }

    public void writeBytesDataToFile(byte[] data, OutputStream outputStream) {
        try {
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            throw new UnexpectedException("Cannot write bytes data to outputStream: " + e.getMessage(), e);
        }
    }
}
