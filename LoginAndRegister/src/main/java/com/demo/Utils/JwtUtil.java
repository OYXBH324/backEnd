package com.demo.Utils;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.po.system.User;
import org.springframework.lang.Nullable;

import java.util.Base64;

public class JwtUtil {
    @Nullable
    public static User parseUser(String jwt) {
        jwt = jwt.replace("\"","");
        try {
            String[] jwtParts = jwt.split("\\.");
            String jwtHeader = jwtParts[0];
            String jwtPayload = jwtParts[1];
            String jwtSignature = jwtParts[2];
            String header = new String(Base64.getDecoder().decode(jwtHeader));
            String payload = new String(Base64.getDecoder().decode(jwtPayload));
            JSONObject json = JSONObject.parseObject(payload);
            String email = json.getString("email");
            User user = new User();
            user.setEmail(email);
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
