package com.tianwen.springcloud.scoreapi.controller;

import com.tianwen.springcloud.scoreapi.base.AbstractController;
import com.tianwen.springcloud.scoreapi.entity.export.ExportedFile;
import com.tianwen.springcloud.scoreapi.service.util.repo.ExportedFileRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by kimchh on 11/7/2018.
 */
@Controller
@RequestMapping("/export/download")
public class ExportedFileDownloadController extends AbstractController {

    @Autowired
    @Lazy
    private ExportedFileRepositoryService exportedFileRepositoryService;

    @RequestMapping(value = "/{exportedFileKey}", method = RequestMethod.GET)
    public void download(@PathVariable("exportedFileKey") String exportedFileKey, HttpServletResponse response) throws IOException {

        ExportedFile exportedFile = (ExportedFile)memCache.get(exportedFileKey);
        if (exportedFile == null) {
            return;
        }

        prepareResponseForDownload(response, exportedFile.getContentType(), exportedFile.getName());

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(exportedFile.getPath());
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
            response.getOutputStream().flush();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
                exportedFileRepositoryService.removeExportedFile(exportedFile);
            }
        }
    }
}
