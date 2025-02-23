package ru.mrrex.enfusionstructparser.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import ru.mrrex.enfusionstructparser.type.base.SerializableEnfusionObject;

public class EnfusionObjectWriter implements AutoCloseable {

    private final Writer writer;

    public EnfusionObjectWriter(Writer writer) {
        this.writer = writer;
    }

    public EnfusionObjectWriter(String fileName) throws IOException {
        this(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
    }

    public EnfusionObjectWriter(File file) throws IOException {
        this(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
    }

    public EnfusionObjectWriter(Path filePath) throws IOException {
        this(Files.newBufferedWriter(filePath, StandardCharsets.UTF_8));
    }

    public Writer getWriter() {
        return writer;
    }

    public void write(SerializableEnfusionObject object) throws IOException {
        writer.write(object.serialize());
    }

    public void write(SerializableEnfusionObject... objects) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        for (SerializableEnfusionObject object : objects)
            stringBuilder.append(object.serialize());

        writer.write(stringBuilder.toString());
    }

    public void write(List<? extends SerializableEnfusionObject> objects) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        for (SerializableEnfusionObject object : objects)
            stringBuilder.append(object.serialize());

        writer.write(stringBuilder.toString());
    }

    public void append(SerializableEnfusionObject object) throws IOException {
        writer.append(object.serialize());
    }

    public void append(SerializableEnfusionObject... objects) throws IOException {
        for (SerializableEnfusionObject object : objects)
            writer.append(object.serialize());
    }

    public void append(List<? extends SerializableEnfusionObject> objects) throws IOException {
        for (SerializableEnfusionObject object : objects)
            writer.append(object.serialize());
    }

    @Override
    public void close() throws Exception {
        if (writer == null)
            writer.close();
    }
}
