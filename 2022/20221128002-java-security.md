# java.security

- MAC(Message Authentication Code)
    - 消息认证码（带密钥的Hash函数），通信实体双方使用的一种验证机制
- HMAC(Hash-based Message Authentication Code)
    - 基于密钥的消息认证码算法，是一种更安全的消息摘要算法，用来作加密、数字签名、报文验证等;
    - Hmac算法总是和某种哈希算法配合起来用,例如，使用MD5算法对应的就是 HmacMD5;
    - HmacMD5、HmacSHA1、HmacSHA128、HmacSHA256、HmacSHA384、Hmac512;


### Reference

- [加密与安全](https://www.liaoxuefeng.com/wiki/1252599548343744/1255943717668160)

- [Java加密体系（一）java.security包](https://www.jianshu.com/p/548ec3b91d20)

- [Java加密解密之MAC（消息认证码）](https://blog.csdn.net/x_san3/article/details/80613605)

- [Java Cryptography Architecture (JCA) Reference Guide](https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#ProviderArch)

- [让你彻底理解Base64算法（Base64是什么，Base64解决什么问题，Base64字符串末尾的=是什么）](https://zhuanlan.zhihu.com/p/384238870)