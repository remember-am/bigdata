package com.remember.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.Arrays;

/**
 * 全角转半角
 */
public class FullToHalf extends GenericUDF {
    @Override
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        if (objectInspectors.length != 1) {
            throw new UDFArgumentException("该方法必须指定一个唯一参数！");
        }
        if (objectInspectors[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
            throw new UDFArgumentException("该方法只接受基础数据类型的参数！");
        }
        PrimitiveObjectInspector poi = (PrimitiveObjectInspector) objectInspectors[0];
        if (poi.getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("该方法只接受String数据类型的参数！");
        }
        return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {
        if (deferredObjects == null || deferredObjects[0] == null || deferredObjects[0].get() == null) {
            return null;
        }
        String str = deferredObjects[0].get().toString().replaceAll("\\s+", "");
        char[] cs = str.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == 12288) {
                //全角空各为12288，半角空格为32
                cs[i] = (char) 32;
            } else if(cs[i] >= 65281 && cs[i] <= 65374) {
                // 全角字符通常位于unicode码点65281-65374之间，半角字符位于unicode码点33-126之间
                cs[i] = (char)(cs[i] - 65248);
            }
        }
        return new String(cs);
    }

    @Override
    public String getDisplayString(String[] strings) {
        return Arrays.toString(strings);
    }
}
