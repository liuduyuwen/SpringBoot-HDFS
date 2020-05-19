package com.ljl.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljl.hadoopconf.HdfsService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.BlockLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * @author ：
 * @date ：Created in 2019/11/7 15:25
 * @description：
 * @modified By：
 * @version:
 */
@RestController
@RequestMapping("/hadoop")
public class DemoController {
    private static Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    /**
     * 创建文件夹
     * @param path
     * @return
     * @throws Exception
     */
    @ApiOperation("创建文件夹")
    @RequestMapping(value = "mkdir", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject mkdir(@RequestParam("path") String path) throws Exception {
        JSONObject object = new  JSONObject( );
        if (StringUtils.isEmpty(path)) {
            object.put("message", "请求参数为空");
            LOGGER.debug("请求参数为空");
            return object;
        }
        // 创建空文件夹
        boolean isOk = HdfsService.mkdir(path);
        if (isOk) {
            LOGGER.debug("文件夹创建成功");
            object.put("message", "文件夹创建成功");
            return object;
        } else {
            LOGGER.debug("文件夹创建失败");
            object.put("message", "文件夹创建成功");
            return object;
        }
    }

    /**
     * 读取HDFS目录信息
     * @param path
     * @return
     * @throws Exception
     */
    @ApiOperation("读取HDFS目录信息")
    @PostMapping("/readPathInfo")
    public JSONObject  readPathInfo(@RequestParam("path") String path) throws Exception {
        List<Map<String, Object>> list = HdfsService.readPathInfo(path);
        JSONObject object = new  JSONObject( );
        object.put( "读取HDFS目录信息成功", list);
        return object;
    }


    /**
     * 添加文件
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/addFile")
    @ApiOperation("添加文件")
    public JSONObject createFile(@RequestParam("path") String path, @RequestParam("file") MultipartFile file)
            throws Exception {
        JSONObject object = new  JSONObject( );
        if (StringUtils.isEmpty(path) || null == file.getBytes()) {
            object.put("message", "请求参数为空");
            return object;
        }
        HdfsService.createFile(path, file);
        object.put("message", "添加文件成功");
        return object;
    }

    /**
     * 读取HDFS文件内容
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/readFile")
    @ApiOperation("读取HDFS文件内容")
    public JSONObject readFile(@RequestParam("path") String path) throws Exception {
        JSONObject object = new  JSONObject( );
        String targetPath = HdfsService.readFile(path);
        object.put("message", "读取HDFS文件内容success");
        object.put("data",targetPath);
        return object;

    }


    /**
     * 读取文件列表
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/listFile")
    @ApiOperation("读取文件列表")
    public JSONObject listFile(@RequestParam("path") String path) throws Exception {
        JSONObject object = new  JSONObject( );
        if (StringUtils.isEmpty(path)) {
            object.put("message","请求参数为空");
            return object;
        }
        List<Map<String, String>> returnList = HdfsService.listFile(path);
        object.put("message","读取文件列表成功");
        object.put("data",returnList);
        return object;
    }


    /**
     * 重命名文件
     * @param oldName
     * @param newName
     * @return
     * @throws Exception
     */
    @PostMapping("/renameFile")
    @ApiOperation("重命名文件")
    public JSONObject renameFile(@RequestParam("oldName") String oldName, @RequestParam("newName") String newName)
            throws Exception {
        JSONObject object = new  JSONObject( );
        if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
            object.put("message","请求参数为空");
            return object;
        }
        boolean isOk = HdfsService.renameFile(oldName, newName);
        if (isOk) {
            object.put("message","文件重命名成功");
            return object;
        } else {
            object.put("message","文件重命名失败");
            return object;
        }
    }

    /**
     * 删除文件
     * @param path
     * @return
     * @throws Exception
     */
    @ApiOperation("删除文件")
    @PostMapping("/deleteFile")
    public JSONObject deleteFile(@RequestParam("path") String path) throws Exception {
        JSONObject object = new  JSONObject( );
        boolean isOk = HdfsService.deleteFile(path);
        if (isOk) {
            object.put("message","delete file success");
            return object;
        } else {
            object.put("message","delete file fail");
            return object;
        }
    }



    /**
     * 上传文件
     * @param path
     * @param uploadPath
     * @return
     * @throws Exception
     */
    @ApiOperation("上传文件")
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("path") String path, @RequestParam("uploadPath") String uploadPath)
            throws Exception {
        HdfsService.uploadFile(path, uploadPath);
        return    "upload file success" ;
    }

    /**
     * 下载文件
     * @param path
     * @param downloadPath
     * @return
     * @throws Exception
     */
    @PostMapping("/downloadFile")
    @ApiOperation("下载文件")
    public String downloadFile(@RequestParam("path") String path, @RequestParam("downloadPath") String downloadPath)
            throws Exception {
        HdfsService.downloadFile(path, downloadPath);
        return  "download file success" ;
    }

    /**
     * HDFS文件复制
     * @param sourcePath
     * @param targetPath
     * @return
     * @throws Exception
     */
    @ApiOperation("HDFS文件复制")
    @PostMapping("/copyFile")
    public String copyFile(@RequestParam("sourcePath") String sourcePath, @RequestParam("targetPath") String targetPath)
            throws Exception {
        HdfsService.copyFile(sourcePath, targetPath);
        return  "copy file success" ;
    }

    /**
     * 查看文件是否已存在
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/existFile")
    @ApiOperation("查看文件是否已存在")
    public String existFile(@RequestParam("path") String path) throws Exception {
        boolean isExist = HdfsService.existFile(path);
        return   "file isExist: " + isExist ;
    }



}
