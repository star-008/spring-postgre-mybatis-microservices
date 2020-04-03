package com.tianwen.springcloud.scoreapi.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kimchh on 11/7/2018.
 */
@Service
public class URLService {

    @Autowired
    protected HttpServletRequest httpServletRequest;

    public String getRootURL() {
        return httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), "") +  httpServletRequest.getServletContext().getContextPath() + "/";
    }

    public String getExportedFileURL(String exportedFileKey) {
        return getRootURL() + "export/download/" + exportedFileKey;
    }

    public String getClientIP() {
        return httpServletRequest.getRemoteAddr();
    }
}
