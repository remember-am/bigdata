package com.remember.serde;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.FileSinkOperator;
import org.apache.hadoop.hive.ql.io.HiveOutputFormat;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Progressable;

import java.io.IOException;
import java.util.Properties;

public class DocOutputFormat<k extends WritableComparator, v extends Writable>
        extends TextOutputFormat<k, v> implements HiveOutputFormat<k, v> {
    @Override
    public FileSinkOperator.RecordWriter getHiveRecordWriter(JobConf jobConf, Path path, Class<? extends Writable> aClass, boolean b, Properties properties, Progressable progressable) throws IOException {
        FileSystem fs = path.getFileSystem(jobConf);
        FSDataOutputStream fos = fs.create(path);
        return (FileSinkOperator.RecordWriter) new DocRecordWriter(fos);
    }

    @Override
    public RecordWriter<k, v> getRecordWriter(FileSystem fileSystem, JobConf jobConf, String s, Progressable progressable) throws IOException {
        return null;
    }

    @Override
    public void checkOutputSpecs(FileSystem fileSystem, JobConf jobConf) throws IOException {

    }
}
