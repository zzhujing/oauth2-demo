package com.hujing.authentication.code.image.model;

import com.hujing.authentication.code.common.ValidateCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author hj
 * @create 2019-05-01 22:30
 * 图形校验码实体类
 */
@Data
@NoArgsConstructor
public class ImageCode extends ValidateCode {

    private static final long serialVersionUID = 7614616334824165586L;

    private BufferedImage image;


    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    /**
     * 传入过期的秒数构造过期时间
     *
     * @param image
     * @param code
     * @param expire
     */
    public ImageCode(BufferedImage image, String code, int expire) {
        super(code, expire);
        this.image = image;
    }
}