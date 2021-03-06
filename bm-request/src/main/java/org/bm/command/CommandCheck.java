package org.bm.command;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bm.enums.MessageEnums;
import org.bm.util.StringUtils;

public class CommandCheck {
    public static boolean checkCommand(CommandTemlpate commandTemlpate) {
        if (StringUtils.isEmpty(commandTemlpate.url)) {
            System.out.println(MessageEnums.NOT_URL.value());
            return false;
        }

        if (!checkUrl(commandTemlpate.url.trim())) {
            System.out.println(MessageEnums.ERROR_URL.value());
            return false;
        }

        if (!checkHOST(commandTemlpate.url.trim())) {
            System.out.println(MessageEnums.ERROR_URL.value());
            return false;
        }
        return true;
    }

    /**
     * 校验url的正确性
     * 
     * @return
     */
    public static boolean checkUrl(String url) {
        String regex = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://|[fF][tT][pP]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(url).matches()) {
            return false;
        }
        return true;
    }

    /**
     * 校验域名的正确性
     * 
     * @param url
     * @return
     */
    public static boolean checkHOST(String url) {
        Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        String message = MessageEnums.NOT_HOST.value() + url;
        if (matcher.find()) {
            try {
                InetAddress[] inetAddresses = InetAddress.getAllByName(matcher.group());
                if (inetAddresses == null || inetAddresses.length == 0) {
                    System.out.println(message);
                    return false;
                }
                return true;
            } catch (UnknownHostException e) {
                System.out.println(message);
                return false;
            }
        }
        System.out.println(message);
        return false;
    }

}
