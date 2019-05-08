package com.hujing.springsecurityoauth2resource.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hujing.springsecurityoauth2resource.domain.TbContent;
import com.hujing.springsecurityoauth2resource.mapper.TbContentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hujing
 * @since 2019-04-27
 */
@RestController
@Slf4j
public class TbContentController {

    @Autowired
    private TbContentMapper tbContentMapper;

    @GetMapping("/")
    @ResponseBody
    public List<TbContent> findContentList() {
        return tbContentMapper.selectList(null);
    }

    @GetMapping("/view")
    public TbContent findFirstContent() {
        return tbContentMapper.selectById(28);
    }
    /**
     * 添加
     * @param tbContent
     * @return
     */
    @PostMapping("/insert")
    public TbContent insertContent(TbContent tbContent) {
        int insert = tbContentMapper.insert(tbContent);
        if (insert > 0) {
            log.info("【收到自增主键 ， id : {}】", tbContent.getId());
            return tbContentMapper.selectById(tbContent.getId());
        }
        return null;
    }
    @PutMapping("/update")
    public TbContent updateContent(TbContent tbContent) {
        int update = tbContentMapper.update(tbContent, null);
        if (update > 0) {
            return tbContentMapper.selectOne(Wrappers.query(tbContent));
        }
        return null;
    }
    @DeleteMapping("/delete")
    public String deleteContent(Long id) {
        int result = tbContentMapper.deleteById(id);
        if (result > 0) {
            return "删除成功";
        }
        return "删除失败";
    }













































}
