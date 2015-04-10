package de.netprojectev.common.networking;

import java.io.File;

/**
 * Created by samu on 10.04.15.
 */
public class IntermediateFileData {

    private OpCode opcode;
    private File file;
    private String name;

    public IntermediateFileData(OpCode opcode, File file) {
        this.file = file;
        this.opcode = opcode;
        this.name = file.getName();
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
