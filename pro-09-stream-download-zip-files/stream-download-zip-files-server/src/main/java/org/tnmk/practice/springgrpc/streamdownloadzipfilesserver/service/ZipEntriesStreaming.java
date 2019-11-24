package org.tnmk.practice.springgrpc.streamdownloadzipfilesserver.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.tnmk.common.utils.IOUtils;
import org.tnmk.common.utils.UnexpectedException;

@Service
public class ZipEntriesStreaming {
    public static final String FILE_NAME = "SampleFile.txt";
    public static final String FILE_PATH = "/samplefiles/" + FILE_NAME;

    @Async
    public void asyncStreamEntriesData(long numZipEntries, ZipOutputStream zipOutputStream) {
        //TODO can we add zip entries in parallel?
        for (int i = 0; i < numZipEntries; i++) {
            ZipEntry zipEntry = addZipEntry(FILE_NAME, zipOutputStream);
            copyZipEntryFileDataToZipOutputStream(FILE_PATH, zipOutputStream);
            try {
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                throw new UnexpectedException("Cannot close zipEntry" + zipEntry.getName());
            }
        }
    }

    private ZipEntry addZipEntry(String zipEntryFileName, ZipOutputStream zipOutputStream) {
        ZipEntry zipEntry = new ZipEntry(zipEntryFileName);
        try {
            zipOutputStream.putNextEntry(zipEntry);
        } catch (IOException e) {
            throw new UnexpectedException("Cannot create new zipEntry " + zipEntryFileName);
        }
        return zipEntry;
    }

    private void copyZipEntryFileDataToZipOutputStream(String zipEntryFilePath, ZipOutputStream zipOutputStream) {
        try (InputStream zipEntryInputStream = IOUtils.validateExistInputStreamFromClassPath(zipEntryFilePath)) {
            //TODO Should we copy stream data in chunks?
            StreamUtils.copy(zipEntryInputStream, zipOutputStream);
        } catch (IOException e) {
            throw new UnexpectedException("Cannot read data from zip entry file " + zipEntryFilePath);
        }
    }
}
