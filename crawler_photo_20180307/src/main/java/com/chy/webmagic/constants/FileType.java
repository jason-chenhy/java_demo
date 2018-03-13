package com.chy.webmagic.constants;

/**
 * @author chenhaoyu
 * @Created 2018-03-12 17:11
 */
public enum FileType {

    JPG("jpg"),
    PNG("png");

    private static final String[] fileTypes = new String[FileType.values().length];

    private String typeName;

    FileType(String typeName) {
        this.typeName = typeName;
    }

    public static final String[] getTypeNames() {
        for (int i=0; i<fileTypes.length; i++) {
            fileTypes[i] = FileType.values()[i].getTypeName();
        }
        return fileTypes;
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
