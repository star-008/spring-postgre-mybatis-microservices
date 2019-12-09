package com.tianwen.springcloud.scoreapi.service.util.repo;

import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.scoreapi.entity.export.ExportedFile;
import com.tianwen.springcloud.scoreapi.service.util.cache.MemCacheService;
import com.tianwen.springcloud.scoreapi.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by kimchh on 11/23/2018.
 */
@Service
@Lazy
public class ExportedFileRepositoryService {

    @Autowired
    @Lazy
    private MemCacheService memCache;

    public ExportedFile createNewExportedFile(String name, String contentType) throws IOException {

        ExportedFile exportedFile = new ExportedFile(name, contentType);
        exportedFile.setPath(File.createTempFile("export", "." + contentType).getAbsolutePath());

        exportedFile.setKey(SecurityUtil.generateHash(exportedFile.getPath()));
        memCache.put(exportedFile.getKey(), exportedFile);

        return exportedFile;
    }

    public void removeExportedFile(ExportedFile exportedFile) {
        try {
            File file = new File(exportedFile.getPath());
            if (file != null) {
                if (file.delete()) {
                    // ...
                }
            }
        } finally {
            memCache.remove(exportedFile.getKey());
        }
    }
}
