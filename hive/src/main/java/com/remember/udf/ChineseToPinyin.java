package com.remember.udf;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class ChineseToPinyin extends GenericUDF {
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
        String name = deferredObjects[0].get().toString().replaceAll("\\s+", "");
        StringBuilder res = new StringBuilder();
        char[] c = name.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char value : c) {
            if (value > 128) {
                try {
                    res.append(PinyinHelper.toHanyuPinyinStringArray(value, format)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination ignored) {
                }
            } else {
                res.append(value);
            }
        }
        return res.toString();
    }

    @Override
    public String getDisplayString(String[] strings) {
        return null;
    }
}
