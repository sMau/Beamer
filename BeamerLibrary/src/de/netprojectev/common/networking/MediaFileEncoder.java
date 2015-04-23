package de.netprojectev.common.networking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by samu on 10.04.15.
 */
public class MediaFileEncoder extends MessageToMessageEncoder<IntermediateFileData> {

    @Override
    protected void encode(ChannelHandlerContext ctx, IntermediateFileData msg, List<Object> out) throws Exception {
        byte[] data = Files.readAllBytes(Paths.get(msg.getFile().toURI()));
        switch (msg.getOpcode()) {
            case CTS_ADD_THEMESLIDE:
                out.add(new Message(msg.getOpcode(), data, msg.getName(), msg.getTheme()));
                break;
            case CTS_ADD_IMAGE_FILE:
                out.add(new Message(msg.getOpcode(), data, msg.getName()));
                break;
            case CTS_ADD_VIDEO_FILE:
                break;
            default:
                break;
        }
    }
}
