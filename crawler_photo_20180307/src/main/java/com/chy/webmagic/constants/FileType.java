package com.chy.webmagic.constants;

/**
 * @author chenhaoyu
 * @Created 2018-03-12 17:11
 */
public enum FileType {

    JPG("jpg"),
    PNG("png");

    private String typeName;

    FileType(String typeName) {
        this.typeName = typeName;
    }

    public static FileType getFileType(String typeName) {
        for (FileType fileType : FileType.values()) {
            if (fileType.getTypeName().equalsIgnoreCase(typeName)) {
                return fileType;
            }
        }
        return null;
    }

    public String getTypeName() {
        return typeName;
    }
}
