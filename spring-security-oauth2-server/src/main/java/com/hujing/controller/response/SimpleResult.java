package com.hujing.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hj
 * 2019-05-09 10:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleResult {

    private String content;
}
