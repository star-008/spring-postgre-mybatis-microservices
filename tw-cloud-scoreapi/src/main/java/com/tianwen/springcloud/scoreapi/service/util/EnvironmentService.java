package com.tianwen.springcloud.scoreapi.service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by kimchh on 11/7/2018.
 */
@Service
@Scope("application")
public class EnvironmentService {

    enum EnvMode {
        LIVE, DEV;
    }

    @Value("${env.mode}")
    protected String mode;

    public boolean isLive() {
        return mode.toUpperCase().equals(EnvMode.LIVE.name());
    }
}
