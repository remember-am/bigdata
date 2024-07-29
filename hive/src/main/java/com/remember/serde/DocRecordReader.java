package com.remember.serde;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DocRecordReader extends RecordReader<LongWritable, Text> {

    private BufferedReader br;

    private String currentLine;

    private LongWritable currentKey;

    private Text currentValue;

    private StringBuilder keyValueCache;

    private boolean inDoc;

    private final String LINE_START = "<doc>";

    private final String LINE_END = "</doc>";

    private final String KEY_VALUE_SEPARATOR = "\u0001";

    @Override
    public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        Configuration conf = taskAttemptContext.getConfiguration();
        Path path = ((FileSplit) inputSplit).getPath();
        FileSystem fs = FileSystem.get(conf);
        FSDataInputStream fis = fs.open(path);
        br = new BufferedReader(new InputStreamReader(fis));
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        keyValueCache.setLength(0);
        inDoc = false;
        while (true) {
            if ((currentLine = br.readLine()) == null) {
                break;
            }
            if (!inDoc) {
                if (LINE_START.equals(currentLine)) {
                    inDoc = true;
                }
            } else {
                if (LINE_END.equals(currentLine)) {
                    inDoc = false;
                    currentKey.set(currentKey.get() + 1);
                    currentValue.set(keyValueCache.toString());
                    return true;
                } else {
                    if (keyValueCache.length() != -1) {
                        keyValueCache.append(KEY_VALUE_SEPARATOR);
                    }
                    keyValueCache.append(currentKey);
                }
            }
        }
        return false;
    }

    @Override
    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        return currentKey;
    }

    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {
        return currentValue;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {
        br.close();
    }
}
