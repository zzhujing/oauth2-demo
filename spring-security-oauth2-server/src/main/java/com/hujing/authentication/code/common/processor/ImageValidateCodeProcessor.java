package com.hujing.authentication.code.common.processor;

import com.hujing.authentication.code.image.model.ImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @author hj
 * 2019-05-09 10:06
 */
@Component("imageValidateCodeProcessor")
@Slf4j
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {


    @Override
    public void send(ServletWebRequest request, ImageCode code) {
        try {
            ImageIO.write(code.getImage(), "JPEG", request.getResponse().getOutputStream());
        } catch (IOException e) {
            log.error("【发送验证码失败】");
        }
    }
}
