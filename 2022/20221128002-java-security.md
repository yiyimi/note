# java.security

- MAC(Message Authentication Code)
    - 消息认证码（带密钥的Hash函数），通信实体双方使用的一种验证机制
- HMAC(Hash-based Message Authentication Code)
    - 基于密钥的消息认证码算法，是一种更安全的消息摘要算法，用来作加密、数字签名、报文验证等;
    - Hmac算法总是和某种哈希算法配合起来用,例如，使用MD5算法对应的就是 HmacMD5;
    - HmacMD5、HmacSHA1、HmacSHA128、HmacSHA256、HmacSHA384、Hmac512;

- Java Cryptography Architecture (JCA) 
    - 用于访问和开发 Java 平台密码功能的构架;
    - 包括用于数字签名和报文摘要的 API
- JCE（Java Cryptography Extension）
    - 提供用于加密、密钥生成和协商以及 Message Authentication Code（MAC）算法的框架和实现。
    - 对对称、不对称、块和流密码的加密支持，它还支持安全流和密封的对象。它不对外出口，用它开发完成封装后将无法调用。



### Reference

- [加密与安全](https://www.liaoxuefeng.com/wiki/1252599548343744/1255943717668160)

- [Java加密体系（一）java.security包](https://www.jianshu.com/p/548ec3b91d20)

- [Java加密解密之MAC（消息认证码）](https://blog.csdn.net/x_san3/article/details/80613605)

- [Java Cryptography Architecture (JCA) Reference Guide](https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#ProviderArch)

- [让你彻底理解Base64算法（Base64是什么，Base64解决什么问题，Base64字符串末尾的=是什么）](https://zhuanlan.zhihu.com/p/384238870)

- [java security浅谈](https://blog.csdn.net/hyEnA_Tiger/article/details/78407138?locationNum=9&fps=1)

- [Java Cryptography Architecture (JCA) Reference Guide](https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#ProviderArch)

- [Java Cryptography Architecture
Standard Algorithm Name Documentation for JDK 8](https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html)

- [云服务AppId或AppKey和AppSecret生成与使用](https://houxian1103.blog.csdn.net/article/details/111662511)

- [Hutool-crypto加密工具](https://blog.csdn.net/qq_45246098/article/details/123065390)

- [java随机生成字符串的方法（三种）](https://blog.csdn.net/cpa_821/article/details/85054198)