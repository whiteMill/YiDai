package com.sdot.yidai.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class UpLoadImageVo {

    /**
     * originalFileName : 1510911387210949.jpg
     * newFileName : 9f85f188-184b-40d4-b905-74c2a59e3407.jpg
     * fileSize : 52723
     * fileType : JPEG
     * uploadType : image
     * topcategory : 资产证明类
     * subcategory : 营业执照
     * actCode : null
     * imageInfo : [{"Type":3,"Size":0,"Width":0,"Height":0,"URL":"9f85f188-184b-40d4-b905-74c2a59e3407_198.jpg"},{"Type":2,"Size":0,"Width":0,"Height":0,"URL":"9f85f188-184b-40d4-b905-74c2a59e3407_720.jpg"},{"Type":1,"Size":0,"Width":1080,"Height":1920,"URL":"9f85f188-184b-40d4-b905-74c2a59e3407.jpg"}]
     */

    private String originalFileName;
    private String newFileName;
    private int fileSize;
    private String fileType;
    private String uploadType;
    private String topcategory;
    private String subcategory;
    private Object actCode;
    private List<ImageInfoBean> imageInfo;

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getTopcategory() {
        return topcategory;
    }

    public void setTopcategory(String topcategory) {
        this.topcategory = topcategory;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public Object getActCode() {
        return actCode;
    }

    public void setActCode(Object actCode) {
        this.actCode = actCode;
    }

    public List<ImageInfoBean> getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(List<ImageInfoBean> imageInfo) {
        this.imageInfo = imageInfo;
    }

    public static class ImageInfoBean {
        /**
         * Type : 3
         * Size : 0
         * Width : 0
         * Height : 0
         * URL : 9f85f188-184b-40d4-b905-74c2a59e3407_198.jpg
         */

        private int Type;
        private int Size;
        private int Width;
        private int Height;
        private String URL;

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public int getSize() {
            return Size;
        }

        public void setSize(int Size) {
            this.Size = Size;
        }

        public int getWidth() {
            return Width;
        }

        public void setWidth(int Width) {
            this.Width = Width;
        }

        public int getHeight() {
            return Height;
        }

        public void setHeight(int Height) {
            this.Height = Height;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }
    }
}
