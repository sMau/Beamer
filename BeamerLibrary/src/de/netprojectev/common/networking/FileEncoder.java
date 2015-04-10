package de.netprojectev.common.networking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by samu on 10.04.15.
 */
public class FileEncoder extends MessageToMessageEncoder<IntermediateFileData> {

    @Override
    protected void encode(ChannelHandlerContext ctx, IntermediateFileData msg, List<Object> out) throws Exception {
        byte[] data = Files.readAllBytes(Paths.get(msg.getFile().toURI()));
        out.add(new Message(msg.getOpcode(), data, msg.getName()));
    }
}
