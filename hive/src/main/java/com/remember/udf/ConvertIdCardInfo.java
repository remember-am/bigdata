package com.remember.udf;

import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 若是15位身份证号则转为18位身份证号，并且提取出证件号码的行政区划、出生日期、性别等信息
 */
public class ConvertIdCardInfo extends GenericUDF {

    // 身份证号前17位对应的系数值
    static int[] co = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    // 与11取余数后0-10对应的校验码
    static char[] coCode = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    static Map<String, String> cache = new HashMap<>();

    static {
        try {
            InputStream is = ConvertIdCardInfo.class.getClassLoader().getResourceAsStream("administrative-division-code.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] splits = line.split(",");
                cache.put(splits[0], splits[1]);
            }
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        String idCard = deferredObjects[0].get().toString().toUpperCase().replaceAll("\\s+", "");
        if (idCard.matches("^\\d{15}$")) {
            idCard = convertIdCard(idCard);
        }

        JSONObject res = new JSONObject();
        res.put("id_number", idCard);
        if (!idCard.matches("^\\d{17}[\\dX]$")) {
            res.put("type", "nonstandard");
        } else {
            String division = "未知";
            String birthday = "未知";
            String gender = "未知";
            try {
                SimpleDateFormat sdfInput = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");
                String birthdayString = idCard.substring(6, 14);
                birthday = sdfOutput.format(sdfInput.parse(birthdayString));
            } catch (ParseException ignored) {
            }
            char genderCode = idCard.length() == 18 ? idCard.charAt(16) : ' ';
            if (Character.isDigit(genderCode)) {
                gender = genderCode % 2 == 0 ? "女" : "男";
            }
            division = cache.getOrDefault(idCard.substring(0, 6), "未知");
            res.put("type", "standard");
            res.put("birthday", birthday);
            res.put("gender", gender);
            res.put("code_name", division);
        }
        return res.toJSONString();
    }

    @Override
    public String getDisplayString(String[] strings) {
        return Arrays.toString(strings);
    }

    /**
     * 15位身份证号转18位身份证号
     * @param idCard 15位身份证号
     * @return 18位身份证号
     */
    public String convertIdCard(String idCard) {
        char[] ans = new char[18];
        int i = 0;
        ans[6] = idCard.charAt(6) == '0' ? '2' : '1';
        ans[7] = idCard.charAt(6) == '0' ? '0' : '9';
        for (char c : idCard.toCharArray()) {
            if (i == 6) {
                i = 8;
            }
            ans[i++] = c;
        }
        int sum = 0;
        for (i = 0; i < 17; i++) {
            sum += (ans[i] - '0') * co[i];
        }
        ans[17] = coCode[sum % 11];
        return new String(ans);
    }
}
