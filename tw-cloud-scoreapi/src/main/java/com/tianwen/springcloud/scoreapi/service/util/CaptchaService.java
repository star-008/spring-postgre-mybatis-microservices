package com.tianwen.springcloud.scoreapi.service.util;

import com.tianwen.springcloud.commonapi.base.response.ServerResult;
import com.tianwen.springcloud.scoreapi.constant.IErrorMessageConstants;
import com.tianwen.springcloud.scoreapi.exception.LoginFailureException;
import com.tianwen.springcloud.scoreapi.service.util.cache.MemCacheService;
import com.tianwen.springcloud.scoreapi.util.captcha.color.SingleColorFactory;
import com.tianwen.springcloud.scoreapi.util.captcha.predefined.*;
import com.tianwen.springcloud.scoreapi.util.captcha.service.Captcha;
import com.tianwen.springcloud.scoreapi.util.captcha.service.ConfigurableCaptchaService;
import com.tianwen.springcloud.scoreapi.util.captcha.word.RandomWordFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Created by kimchh on 11/7/2018.
 */
@Service
@Lazy
public class CaptchaService {

    private static final String IMAGE_DIR_PATH =  "assets/img/captcha/";

    private static final String CACHE_KEY_PREFIX_CAPTCHA_CODE = "CAPTCHA-CODE";
    private static final String CACHE_KEY_PREFIX_LAST_LOGIN_FAILURE_TIME = "LAST-LOGIN-FAILURE-TIME";

    private static final int CAPTCHA_REQUIRE_INTERVAL = 10;

    @Autowired
    protected ServletContext servletContext;

    @Autowired
    protected URLService urlService;

    @Autowired
    protected MemCacheService memCache;

    private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
    private static Random random = new Random();

    CaptchaService() {
        cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
        cs.setHeight(60);
        cs.setWidth(200);
        RandomWordFactory wf = new RandomWordFactory();

        wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
        wf.setMaxLength(4);
        wf.setMinLength(4);
        cs.setWordFactory(wf);
    }

    public Captcha generateCaptcha() {
        switch (random.nextInt(5)) {
            case 0:
                cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
                break;
            case 1:
                cs.setFilterFactory(new MarbleRippleFilterFactory());
                break;
            case 2:
                cs.setFilterFactory(new DoubleRippleFilterFactory());
                break;
            case 3:
                cs.setFilterFactory(new WobbleRippleFilterFactory());
                break;
            case 4:
                cs.setFilterFactory(new DiffuseRippleFilterFactory());
                break;
            default:
                break;
        }

        return cs.getCaptcha();
    }

    public String saveCaptcha(String code, String verifyKey, BufferedImage image) throws IOException {
        String imageDirPath = servletContext.getRealPath("/") + IMAGE_DIR_PATH;
        new File(imageDirPath).mkdirs();

        File imageFile = new File(imageDirPath + verifyKey + ".png");
        ImageIO.write(image, "png", imageFile);

        memCache.put(CACHE_KEY_PREFIX_CAPTCHA_CODE, verifyKey, code);

        return urlService.getRootURL() + IMAGE_DIR_PATH + verifyKey + ".png";
    }

    public void verifyCode(String code, String verifyKey) {
        if (StringUtils.isEmpty(code)) {
            throw new LoginFailureException(new ServerResult(
                    "-4",
                    IErrorMessageConstants.ERR_MESSAGE_CAPTCHA_REQUIRED
            ));
        }
        if (!code.equalsIgnoreCase((String)memCache.get(CACHE_KEY_PREFIX_CAPTCHA_CODE, verifyKey))) {
            throw new LoginFailureException(new ServerResult(
                    "-4",
                    IErrorMessageConstants.ERR_MESSAGE_CAPTCHA_INVALID
            ));
        }
    }

    public void deleteCaptcha(String verifyKey) {
        String imageDirPath = servletContext.getRealPath("/") + IMAGE_DIR_PATH;
        File imageFile = new File(imageDirPath + verifyKey + ".png");
        if (imageFile.delete()) {
            memCache.remove(CACHE_KEY_PREFIX_CAPTCHA_CODE, verifyKey);
        }
    }

    public boolean doesRequireCaptcha() {
        return (new Date().getTime() - NumberUtils.toLong((String)memCache.get(CACHE_KEY_PREFIX_LAST_LOGIN_FAILURE_TIME, urlService.getClientIP())) < CAPTCHA_REQUIRE_INTERVAL * 1000);
    }

    public void saveLastLoginFailureTime() {
        memCache.put(CACHE_KEY_PREFIX_LAST_LOGIN_FAILURE_TIME, urlService.getClientIP(), Long.toString(new Date().getTime()));
    }

    public void removeLastLoginFailureTime() {
        memCache.remove(CACHE_KEY_PREFIX_LAST_LOGIN_FAILURE_TIME, urlService.getClientIP());
    }
}
