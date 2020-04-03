package com.tianwen.springcloud.scoreapi.base;

import com.tianwen.springcloud.microservice.base.api.DictItemMicroApi;
import com.tianwen.springcloud.microservice.base.api.UserMicroApi;
import com.tianwen.springcloud.scoreapi.service.util.ECOService;
import com.tianwen.springcloud.scoreapi.service.util.EnvironmentService;
import com.tianwen.springcloud.scoreapi.service.util.cache.MemCacheService;
import com.tianwen.springcloud.scoreapi.service.util.cache.TokenCacheService;
import com.tianwen.springcloud.scoreapi.service.util.repo.IdRepositoryService;
import com.tianwen.springcloud.scoreapi.session.UserSessionBean;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * Created by kimchh on 11/7/2018.
 */
public abstract class AbstractController {

    @Autowired
    protected DictItemMicroApi dictItemMicroApi;

    @Autowired
    protected IdRepositoryService repositoryService;

    @Autowired
    protected UserMicroApi userMicroApi;

    @Autowired
    protected UserSessionBean sessionBean;

    @Autowired
    protected ECOService ecoService;

    @Autowired
    protected ServletContext servletContext;

    @Autowired
    protected HttpServletRequest httpServletRequest;

    @Autowired
    protected HttpSession httpSession;

    @Autowired
    protected EnvironmentService environment;

    @Autowired
    protected MemCacheService memCache;

    @Autowired
    protected TokenCacheService tokenCache;

    protected void prepareResponseForDownload(HttpServletResponse response, String contentType, String fileName) {

        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (Exception ex) {

        }

        response.setContentType("application/" + contentType);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    }
}
