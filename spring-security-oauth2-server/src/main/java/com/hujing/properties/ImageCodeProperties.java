package com.hujing.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author hj
 * @create 2019-05-02 10:17
 */
@Data
@AllArgsConstructor
@Builder
public class ImageCodeProperties extends SmsCodeProperties {
    public ImageCodeProperties() {
        setLength(4);
    }
    private int width = 67;
    private int height = 23;
}
