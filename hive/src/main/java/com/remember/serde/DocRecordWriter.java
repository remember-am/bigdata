package com.remember.serde;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class DocRecordWriter extends RecordWriter<LongWritable, Text> {

    private FSDataOutputStream out;

    private final String LINE_START = "<doc>";

    private final String LINE_END = "</doc>";

    private final String KEY_VALUE_SEPARATOR = "\u0001";

    public DocRecordWriter(FSDataOutputStream fos) {
        this.out = fos;
    }

    @Override
    public void write(LongWritable longWritable, Text text) throws IOException, InterruptedException {
        String[] lines = text.toString().split(KEY_VALUE_SEPARATOR);
        out.write(LINE_START.getBytes());
        out.write("\n".getBytes());
        for (String line : lines) {
            out.write(line.getBytes());
            out.write("\n".getBytes());
        }
        out.write(LINE_END.getBytes());
        out.write("\n".getBytes());
    }

    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        out.flush();
        out.close();
    }
}
