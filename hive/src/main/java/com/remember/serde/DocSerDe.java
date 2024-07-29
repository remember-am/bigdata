package com.remember.serde;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.serde2.AbstractSerDe;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.SerDeStats;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import javax.annotation.Nullable;
import java.util.*;

public class DocSerDe extends AbstractSerDe {
    private ObjectInspector inspector;
    private List<String> columnNames;
    private List<TypeInfo> columnTypes;
    private int numCols;
    private List<Object> row;
    private final String LINE_SEPARATOR = "\u0001";
    private final String KEY_VALUE_SEPARATOR = "=";


    @Override
    public void initialize(@Nullable Configuration configuration, Properties properties) throws SerDeException {
        this.columnNames = Arrays.asList(properties.getProperty("columns").split(","));
        this.columnTypes = TypeInfoUtils.getTypeInfosFromTypeString(properties.getProperty("columns.types"));

        this.numCols = columnNames.size();
        List<ObjectInspector> columnOIs = new ArrayList<>(this.numCols);

        int i;
        for(i = 0; i < this.numCols; ++i) {
            columnOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        }
        this.inspector = ObjectInspectorFactory.getStandardStructObjectInspector(columnNames, columnOIs);
        this.row = new ArrayList<>(this.numCols);
    }

    @Override
    public Class<? extends Writable> getSerializedClass() {
        return Text.class;
    }

    @Override
    public Writable serialize(Object o, ObjectInspector objectInspector) throws SerDeException {
        if (!(objectInspector instanceof StructObjectInspector)) {
            throw new SerDeException("Expected a StructObjectInspector");
        }
        StructObjectInspector structInspector = (StructObjectInspector) objectInspector;
        List<Object> structFields = structInspector.getStructFieldsDataAsList(o);

        StringBuilder cache = new StringBuilder();
        for (int i = 0; i < this.numCols; i++) {
            if (Objects.isNull(structFields.get(i))) {
                String v = (String) structFields.get(i);
                String k = this.columnNames.get(i);
                if (cache.length() > 0) {
                    cache.append(LINE_SEPARATOR);
                }
                cache.append(k);
                cache.append(KEY_VALUE_SEPARATOR);
                cache.append(v);
            }
        }
        return new Text(cache.toString());
    }

    @Override
    public SerDeStats getSerDeStats() {
        return null;
    }

    @Override
    public Object deserialize(Writable writable) throws SerDeException {
        if (writable == null) {
            return null;
        }
        Text text = (Text) writable;
        Map<String, String> map = new HashMap<>();
        for (String line : text.toString().split(LINE_SEPARATOR)) {
            String[] kv = line.split(KEY_VALUE_SEPARATOR);
            if (kv.length == 2) {
                map.put(kv[0], kv[1]);
            }
        }
        for (int i = 0; i < this.numCols; i++) {
            TypeInfo typeInfo = columnTypes.get(i);
            Object obj = null;
            if (typeInfo.getCategory() == ObjectInspector.Category.PRIMITIVE) {
                PrimitiveTypeInfo pti = (PrimitiveTypeInfo) typeInfo;
                switch (pti.getPrimitiveCategory()) {
                    case STRING:
                        obj = map.getOrDefault(this.columnNames.get(i), null);
                        break;
                    case LONG:
                        try {
                            obj = Long.parseLong(map.getOrDefault(this.columnNames.get(i), null));
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                    case INT:
                        try {
                            obj = Integer.parseInt(map.getOrDefault(this.columnNames.get(i), null));
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                    case DOUBLE:
                        try {
                            obj = Double.parseDouble(map.getOrDefault(this.columnNames.get(i), null));
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                    default:
                }
            }
            this.row.add(obj);
        }
        return this.row;
    }

    @Override
    public ObjectInspector getObjectInspector() throws SerDeException {
        return this.inspector;
    }
}
