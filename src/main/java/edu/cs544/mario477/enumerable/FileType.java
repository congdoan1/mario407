package edu.cs544.mario477.enumerable;

import lombok.Getter;

@Getter
public enum FileType {
    IMAGE("image"),
    VIDEO("video"),
    OTHER("other");

    private String value;

    FileType(String value) {
        this.value = value;
    }
}
