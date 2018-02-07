package com.sdot.yidai.utils;

import android.content.Context;
import android.view.WindowManager;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/11/1.
 */

public class ToolUtils {

    final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();

    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "宁夏");
        zoneNum.put(65, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "国外");
    }

    final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 身份证号是否基本有效
     *
     * @param s 号码内容
     * @return 是否有效，null和""都是false
     */
    public static boolean checkIcNumber(String s) {
        if (s == null || (s.length() != 15 && s.length() != 18))
            return false;
        final char[] cs = s.toUpperCase().toCharArray();
        // （1）校验位数
        int power = 0;
        for (int i = 0; i < cs.length; i++) {// 循环比正则表达式更快
            if (i == cs.length - 1 && cs[i] == 'X')
                break;// 最后一位可以是X或者x
            if (cs[i] < '0' || cs[i] > '9')
                return false;
            if (i < cs.length - 1)
                power += (cs[i] - '0') * POWER_LIST[i];
        }
        // （2）校验区位码
        if (!zoneNum.containsKey(Integer.valueOf(s.substring(0, 2)))) {
            return false;
        }
        // （3）校验年份
        String year = s.length() == 15 ? "19" + s.substring(6, 8) : s.substring(6, 10);
        final int iyear = Integer.parseInt(year);
        if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR)) {
            return false;// 1900年的PASS，超过今年的PASS
        }
        // （4）校验月份
        String month = s.length() == 15 ? s.substring(8, 10) : s.substring(10, 12);
        final int imonth = Integer.parseInt(month);
        if (imonth < 1 || imonth > 12)
            return false;
        // （5）校验天数
        String day = s.length() == 15 ? s.substring(10, 12) : s.substring(12, 14);
        final int iday = Integer.parseInt(day);
        if (iday < 1 || iday > 31)
            return false;
        // （6）校验一个合法的年月日
        if (!validate(iyear, imonth, iday))
            return false;
        // （7）校验“校验码”
        if (s.length() == 15)
            return true;
        return cs[cs.length - 1] == PARITYBIT[power % 11];
    }

    static boolean validate(int year, int month, int day) {
        // 比如考虑闰月，大小月等
        return true;
    }

    public static boolean checkPhone(String cellphone){
       // String regex = "^((16[0-9])|(17[0-9])|(13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9]))\\d{8}$";
        String regex = "^1[\\d]{10}$";
        return Pattern.matches(regex,cellphone);
    }
/*public static boolean checkIcNumber(String ic_number){
        String regex = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        return Pattern.matches(regex,ic_number);
    }*/


    public static boolean CheckEmpty(EditText editText){
        if(editText.getText().toString().trim().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public static void ClearCookie(){
        CookieStore cookieStore  = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.removeAllCookie();
    }

    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.
                getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return width;
    }

    public static String formatDoubleNumber(double number){
        DecimalFormat df = new DecimalFormat("0.00");
        String deNumber =  df.format(number);
        return deNumber;
    }

    public static String formatStringNumber(String number){
        DecimalFormat df = new DecimalFormat("0.00");
        String deNumber =  df.format(Double.valueOf(number));
        return deNumber;
    }

    public static String formatStringNumberZero(String number){
        DecimalFormat df = new DecimalFormat("0");
        String deNumber =  df.format(Double.valueOf(number));
        return deNumber;
    }

    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     * @param s
     * @return
     */
    public static String dateString2formatString(String s)
    {
        String str="";
        try
        {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            Date date=sd.parse(s);
            str=sdf.format(date);
        }
        catch(Exception e)
        {
            return str;
        }
        return str;
    }

    public static Boolean Checkpass(String pass){
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,14}$";
        return Pattern.matches(regex,pass);
    }

    /**
     * 千位分隔符,并且小数点后保留2位
     *
     * @param num
     * @return String
     */
    public static String qianweifenge(double num) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String ss = df.format(num);
        return ss;
    }
}
