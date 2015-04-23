package de.netprojectev.common.networking;

import java.io.File;
import java.util.UUID;

/**
 * Created by samu on 10.04.15.
 */
public class IntermediateFileData {

    private OpCode opcode;
    private File file;
    private String name;
    private UUID theme;

    public IntermediateFileData(OpCode opcode, File file) {
        this.file = file;
        this.opcode = opcode;
        this.name = file.getName();
    }

    public IntermediateFileData(OpCode opcode, File file, String name, UUID theme) {
        this.file = file;
        this.opcode = opcode;
        this.name = name;
        this.theme = theme;
    }

    public UUID getTheme() {
        return theme;
    }

    public OpCode getOpcode() {
        return opcode;
    }

    public void setOpcode(OpCode opcode) {
        this.opcode = opcode;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public String getName() {
        return name;
    }
}
