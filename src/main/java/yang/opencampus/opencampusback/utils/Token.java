package yang.opencampus.opencampusback.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Token {
    @Value("${expiredDays}")
    static public int expiredDays;
    @Value("${securityCode}")
    static public String securityCode;
    static public String generateJWT(String email){
        
        LocalDateTime expiredTime=LocalDateTime.now();
        expiredTime.plusDays(expiredDays);
        Map<String, Object> payloadData = new HashMap<>();
        payloadData.put("email", email);
        payloadData.put("expiredTime", expiredTime.toString());
        payloadData.put("pleaseDontUseSpider", "ItWillTakeALotOf$");
        // 使用Jackson库将Map对象转换为JSON字符串
        ObjectMapper mapper = new ObjectMapper();
        String payloadJson=new String();
        try {
            payloadJson = mapper.writeValueAsString(payloadData);
            System.out.println("payloadjson:"+payloadJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            // 创建SHA-256的MessageDigest对象
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 将数据转换为字节数组并计算哈希值
            byte[] hashBytes = digest.digest((payloadJson+securityCode).getBytes());
            // 将哈希值转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                // 将每个字节转换为两位十六进制
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // 打印SHA-256哈希值
            System.out.println("payloadJSON" + payloadJson);
            String signature = Base64.getEncoder().encodeToString(hexString.toString().getBytes());
            String payload=Base64.getEncoder().encodeToString(payloadJson.getBytes());
            return (payload+"."+signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "error";
        }
    }



    static public boolean checkToken(String token){
        
        String[] parts = token.split("\\.");
        String payloadJson = parts[0];
        String sinature = parts[1];

        
        // 对Base64编码后的payload进行解码
        byte[] decodedBytes = Base64.getDecoder().decode(payloadJson);
        String decodedPayloadJson = new String(decodedBytes);
        System.out.println("Base64解码后的Payload JSON字符串: " + decodedPayloadJson);
        ObjectMapper decodemapper = new ObjectMapper();
        // 将解码后的JSON字符串转换回Map对象
        try {
            Map<String, Object> decodedPayload = decodemapper.readValue(decodedPayloadJson, Map.class);
            System.out.println("解码后的Payload Map: " + decodedPayload);
            System.out.println(decodedPayload.get("email"));

            String payloadEmail=(String) decodedPayload.get("email");
            LocalDateTime payloadExpiredTime=LocalDateTime.parse((String) decodedPayload.get("expiredTime"));

            System.out.println(payloadEmail+" get and get  " + payloadExpiredTime.toString());

            
        Map<String, Object> payloadData = new HashMap<>();
        payloadData.put("email", payloadEmail);
        payloadData.put("expiredTime", payloadExpiredTime.toString());
        payloadData.put("pleaseDontUseSpider", "ItWillTakeALotOf$");
        // 使用Jackson库将Map对象转换为JSON字符串
        ObjectMapper encodemapper = new ObjectMapper();
        String repayloadJson=new String();
        try {
            repayloadJson = encodemapper.writeValueAsString(payloadData);
            System.out.println("in try re"+repayloadJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest((repayloadJson+securityCode).getBytes());
            System.out.println("out try re"+repayloadJson);
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // 打印SHA-256哈希值
            System.out.println("remake SHA-256 哈希值: " + hexString.toString()+"sign:"+sinature);
            String remakeSignatrue = Base64.getEncoder().encodeToString(hexString.toString().getBytes());



            if(payloadExpiredTime.isAfter(LocalDateTime.now())){
                return false;//超时
            }else{
                return remakeSignatrue.equals(sinature);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        return true;
    }
    static public boolean checkTokenAndEmail(String token,String needCheckEmail){
        String[] parts = token.split("\\.");
        String payloadJson = parts[0];
        String sinature = parts[1];

        
        // 对Base64编码后的payload进行解码
        byte[] decodedBytes = Base64.getDecoder().decode(payloadJson);
        String decodedPayloadJson = new String(decodedBytes);
        System.out.println("Base64解码后的Payload JSON字符串: " + decodedPayloadJson);
        ObjectMapper decodemapper = new ObjectMapper();
        // 将解码后的JSON字符串转换回Map对象
        try {
            Map<String, Object> decodedPayload = decodemapper.readValue(decodedPayloadJson, Map.class);
            System.out.println("解码后的Payload Map: " + decodedPayload);
            System.out.println(decodedPayload.get("email"));

            String payloadEmail=(String) decodedPayload.get("email");
            LocalDateTime payloadExpiredTime=LocalDateTime.parse((String) decodedPayload.get("expiredTime"));

            System.out.println(payloadEmail+" get and get  " + payloadExpiredTime.toString());
            if(!payloadEmail.equals(needCheckEmail)){
                return false;
                // 此处与checkToken不同，如果email与解析出的不同就直接返回错误
            }
            
        Map<String, Object> payloadData = new HashMap<>();
        payloadData.put("email", payloadEmail);
        payloadData.put("expiredTime", payloadExpiredTime.toString());
        payloadData.put("pleaseDontUseSpider", "ItWillTakeALotOf$");
        // 使用Jackson库将Map对象转换为JSON字符串
        ObjectMapper encodemapper = new ObjectMapper();
        String repayloadJson=new String();
        try {
            repayloadJson = encodemapper.writeValueAsString(payloadData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest((repayloadJson+securityCode).getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // 打印SHA-256哈希值
            System.out.println("remake SHA-256 哈希值: " + hexString.toString()+"sign:"+sinature);
            String remakeSignatrue = Base64.getEncoder().encodeToString(hexString.toString().getBytes());



            if(payloadExpiredTime.isAfter(LocalDateTime.now())){
                return false;//超时
            }else{
                return remakeSignatrue.equals(sinature);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        return true;
    }
    static public String tokenGetEmail(String token){
        System.out.println(token);
        String[] parts = token.split("\\.");
        String payloadJson = parts[0];
        System.out.println(payloadJson);
        // 对Base64编码后的payload进行解码
        byte[] decodedBytes = Base64.getDecoder().decode(payloadJson);
        String decodedPayloadJson = new String(decodedBytes);
        System.out.println("Base64解码后的Payload JSON字符串: " + decodedPayloadJson);
        ObjectMapper decodemapper = new ObjectMapper();
        // 将解码后的JSON字符串转换回Map对象
        try {
            Map<String, Object> decodedPayload = decodemapper.readValue(decodedPayloadJson, Map.class);

            String payloadEmail=(String) decodedPayload.get("email");
            return payloadEmail;
        }
        catch (JsonProcessingException e) {
        e.printStackTrace();
        return("failed");
        }
    }
}
