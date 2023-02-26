# Java 应用中的安全与加密：消息摘要算法篇

在 Java 平台中，应用服务安全的重要性不言而喻。其中密码学及相关的加密算法，便是解决应用安全的有效方法。本篇主要讨论非对称加密算法中的关键算法：消息摘要算法，其广泛用于数字签名、数据完整性校验等应用场景。

## Java 中的安全模块

- Java SDK 提供了用于安全和加密相关的各种 API，在 java.security 包中包含了许多成熟的算法实现，用于确保数据和电子签名的完整性。
- Java 中实现了 MD5、SHA-1、SHA-256、SHA-384，和SHA-512。
- 其中的 MessageDigest 类用于创建封装了摘要算法的对象的工厂，其静态方法 getInstance 返回继承了 MessageDigest类的某个类对象。即，MessageDigest 类既是工厂类，也是所有消息摘要算法的超类。
- 下文在介绍消息摘要的同时，给出 Java 中相关实现的代码做参考学习。

## 消息摘要算法（Message-Digest Algorithm）

- 消息摘要算法又称为哈希（Hash）算法或散列算法，是密码学算法的一个分支。此类算法通过散列函数获得的结果值，是单向、不可逆的，即不可反推出原始值。消息摘要有两个主要属性：
  - 如果数据的一位或多位有变化，消息摘要结果值也将变化；
  - 拥有给定消息的伪造者，不能（极低概率）创建出与原消息拥有相同摘要的假消息。
- 消息摘要算法主要分为三类：
  - MD（Message Digest，消息摘要算法）
  - SHA(Secure Hash Algorithm，安全散列算法)
  - MAC（Message Authentication Code，消息认证码算法）

### MD（Message Digest，消息摘要算法）

- 输入任意长度的信息，经过算法计算，输出均为 16 字节（128 bit）的校验值，一般用 32 位十六进制数表示。
- MD5（Message Digest Algorithm 5，消息摘要算法版本5）
  - MD5 由 MD4、MD3、MD2 改进而来，增强了算法复杂度和不可逆性。
  - 因 MD5 普遍、稳定、快速的特点，仍广泛应用于普通数据的加密保护领域。
- MD 算法系列的 Java 实现：

| 算法  | 摘要长度 |     Java 实现      |
| :---: | :------: | :---------------- |
|  MD2  |   128    |    Java 6 实现     |
|  MD5  |   128    |    Java 6 实现     |
|  MD4  |   128    | Bouncy Castle 实现 |

```java
public class MDCoder {
     /**
      * @description: MD5 消息摘要
      */
    public static byte[] encodeMD5(byte[] data) throws NoSuchAlgorithmException {
        //Init.
        MessageDigest md = MessageDigest.getInstance(AlgorithmEnum.MD5.getVal());
        //Execute.
        return md.digest(data);
    }

    public static String encodeMD5Hex(byte[] data) throws NoSuchAlgorithmException {
        byte[] res = encodeMD5(data);
        return new String(Hex.encode(res));
    }
}

public class MessageDigestTest {
    @Test
    public void testMD5() throws NoSuchAlgorithmException {
        String data = "test.md5.algorithm";
        String res1 = MDCoder.encodeMD5Str(data.getBytes());
        String res2 = MDCoder.encodeMD5Str(data.getBytes());
        System.out.println("res1(hex): " + res1);
        System.out.println("res2(hex): " + res2);
        Assert.assertEquals(res1, res2);
    }
}
/**
res1(hex): 137f8407a48a4c08890228ec69ca68db
res2(hex): 137f8407a48a4c08890228ec69ca68db
*/
```

### SHA（Secure Hash Algorithm，安全散列算法）

- 包括其代表算法 SHA-1 和 SHA-1 算法的变种 SHA-2 系列算法（包含 SHA-224、SHA-256、SHA-384 和 SHA-512），由美国国家安全局（NSA）设计，并由美国国家标准与技术研究院（NIST）发布；是美国的政府标准。
- MD 算法系列的 Java 实现：

|  算法   | 摘要长度 |     Java 实现      |
| :-----: | :------: | :---------------- |
|  SHA-1  |   160    |    Java 6 实现     |
| SHA-256 |   256    |    Java 6 实现     |
| SHA-384 |   384    |    Java 6 实现     |
| SHA-512 |   512    |    Java 6 实现     |
| SHA-224 |   224    | Bouncy Castle 实现 |

```java
public class SHACoder {
     /**
      * @description: SHA-256 消息摘要
      */
    public static byte[] encodeSHA256(byte[] data) throws NoSuchAlgorithmException {
        //Init.
        MessageDigest md = MessageDigest.getInstance(AlgorithmEnum.SHA256.getVal());
        //Execute.
        return md.digest(data);
    }

    public static String encodeSHA256Str(byte[] data) throws NoSuchAlgorithmException {
        byte[] res = encodeSHA256(data);
        //hex converter.
        return new String(Hex.encode(res));
    }

    /**
     * @description: SHA-224 消息摘要
     */
    public static byte[] encodeSHA224(byte[] data) throws NoSuchAlgorithmException {
        //Add BouncyCastlePQCProvider.
        Security.addProvider(new BouncyCastlePQCProvider());
        //Init.
        MessageDigest md = MessageDigest.getInstance(AlgorithmEnum.SHA224.getVal());
        //Execute.
        return md.digest(data);
    }

    public static String encodeSHA224Str(byte[] data) throws NoSuchAlgorithmException {
        byte[] res = encodeSHA224(data);
        //hex converter.
        return new String(Hex.encode(res));
    }
}
public class MessageDigestTest {
    @Test
    public void testSHA() throws NoSuchAlgorithmException {
        String data = "test.sha.algorithm";
        String res1 = SHACoder.encodeSHA256Str(data.getBytes());
        String res2 = SHACoder.encodeSHA256Str(data.getBytes());
        System.out.println("res1(hex): " + res1);
        System.out.println("res2(hex): " + res2);
        Assert.assertEquals(res1, res2);
    }

    @Test
    public void testSHA224() throws NoSuchAlgorithmException {
        String data = "test.sha.algorithm";
        String res1 = SHACoder.encodeSHA224Str(data.getBytes());
        String res2 = SHACoder.encodeSHA224Str(data.getBytes());
        System.out.println("res1(hex): " + res1);
        System.out.println("res2(hex): " + res2);
        Assert.assertEquals(res1, res2);
    }
}
/**
Connected to the target VM, address: '127.0.0.1:63359', transport: 'socket'
res1(hex): 3828ee6d671e2403d069887faf4b7dc6e845ee6227416fdde277fc3e
res2(hex): 3828ee6d671e2403d069887faf4b7dc6e845ee6227416fdde277fc3e
Disconnected from the target VM, address: '127.0.0.1:63359', transport: 'socket'
*/
```

- **SHA 算法与 MD 算法的不同**
  - SHA 算法的摘要长度更长，MD 系列只有 128位，从上表可以看出， SHA 系列从 160 位扩充到 512 位，安全性更高。

### MAC（Message Authentication Code，消息认证码算法）

- MAC 算法结合了 MD5/SHA 算法的优势，并加入密钥的支持，是一种更安全的消息摘要算法。MAC 算法是通信实体双方使用的一种验证机制。
- 由于在消息摘要算法的基础上，加了一个密钥（secret key），MAC 也经常称为 HMAC（散列消息认证码，Hash-Based Message Authentication Code）
  - 基于密钥的消息认证码算法，是一种更安全的消息摘要算法，用来作加密、数字签名、报文验证等;
  - Hmac算法总是和某种哈希算法配合起来用，例如，使用MD5算法对应的就是 HmacMD5,以此类推。
- MAC 算法系列的 Java 实现：

|  算法   | 摘要长度 |     Java 实现      |
| :----- | :------ | :---------------- |
| HmacMD5  | 128 |    Java 6 实现     |
| HmacSHA1  |   160    |    Java 6 实现     |
| HmacSHA256 |   256    |    Java 6 实现     |
| HmacSHA384 |   384    |    Java 6 实现     |
| HmacSHA512 |   512    |    Java 6 实现     |
| HmacMD2 |   128    | Bouncy Castle 实现 |
| HmacMD4 |   128    | Bouncy Castle 实现 |
| HmacSHA224 |   224    | Bouncy Castle 实现 |

- MAC 算法是带有密钥的消息摘要算法，普通的消息摘要不能验证身份和防篡改，而 MAC 算法因为密钥的存在可以实现。其主要实现过程如下：
  - 在发送数据之前，发送方首先使用通信双方协商好的散列函数计算其摘要值。
  - 在双方共享的会话密钥作用下，由摘要值获得消息验证码。之后，它和数据一起被发送。
  - 接收方收到报文后，首先利用会话密钥还原摘要值，同时利用散列函数在本地计算所收到数据的摘要值，并将这两个数据进行比对。若两者相等，则报文通过认证。
  - 基于 MAC 算法的消息传递模型，见下图：
  ![基于 MAC 算法的消息传递模型](../pic/Mac%20model.png)
- MAC算法的应用场景：接口认证/JWT (JSON Web Token)

```java
public class MACCoder {
     /**
      * @description: 初始化 HmacMD5 密钥
      */
    public static byte[] initHmacMD5Key() throws NoSuchAlgorithmException {
        //Init KeyGenerator.
        KeyGenerator generator = KeyGenerator.getInstance(AlgorithmEnum.HmacMD5.getVal());
        //Generate key.
        SecretKey secretKey = generator.generateKey();
        return secretKey.getEncoded();
    }

     /**
      * @description: HmacMD5 消息摘要
      */
    public static byte[] encodeHmacMD5(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        //Restore key.
        SecretKey secretKey = new SecretKeySpec(key, AlgorithmEnum.HmacMD5.getVal());
        //Instantiate Mac.
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        //Init Mac.
        mac.init(secretKey);
        //Execute.
        return mac.doFinal(data);
    }
}
public class MessageDigestTest {
    @Test
    public void testHmacMD5() throws NoSuchAlgorithmException, InvalidKeyException {
        String data = "test.HmacMD5.algorithm.running";
        byte[] key = MACCoder.initHmacMD5Key();
        byte[] res1 = MACCoder.encodeHmacMD5(data.getBytes(), key);
        byte[] res2 = MACCoder.encodeHmacMD5(data.getBytes(), key);
        System.out.println("res1(hex): " + res1);
        System.out.println("res2(hex): " + res2);
        Assert.assertArrayEquals(res1, res2);
    }
}
/**
Connected to the target VM, address: '127.0.0.1:63016', transport: 'socket'
res1(hex): ca525f13108e401f3e681cadd296a88e
res2(hex): ca525f13108e401f3e681cadd296a88e
Disconnected from the target VM, address: '127.0.0.1:63016', transport: 'socket'
*/
```

## Java 中的算法实现

- 从 Java 6 开始，由 Sun 提供了基础的摘要算法实现，前文表格中的 Bouncy Castle 是对 Sun 的友好补充。
- **关于 Bouncy Castle**
  - 第三方开源组织 [Bouncy Castle](https://www.bouncycastle.org/index.html) 提供的加密组件，扩充了算法支持（MD4/SHA-224/HmacMD2/HmacMD4/HmacSHA224）。
  - 使用方式：
    - 方式一：代码中添加依赖包，以 Maven 项目为例：

    ```xml
        <!-- https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15on -->
        <dependency>
            <groupId>org.bouncycastle</groupId>
            <artifactId>bcprov-jdk15on</artifactId>
            <version>1.70</version>
        </dependency>
    ```

    - 方式二：作为 provider 配置在 JRE 中，配置方式（以 jdk1.8 为例）：
      - %JDK_HOME%\jre\lib\security\目录下，打开 Properties 配置文件 Java.security，以固定格式 `security.provider.<n>=<className>` 追加 provider 及生效顺序：

        ```properties
            #BouncyCastleProvider
            security.provider.10=org.bouncycastle.jce.provider.BouncyCastleProvider
        ```

## 关于摘要算法的实际应用场景

- 校验下载文件的数据完整性，以常用工具为例：
  - 下载 [jdk-19_windows-x64_bin.zip](https://www.oracle.com/java/technologies/downloads/#jdk19-windows) ，下图为官网显示值和本地下载文件的计算值，通过比较文件的sha256值可确认下载的完整性。
  ![文件完整性校验使用场景](../pic/certutil%20for%20jdk19%20sha256.png)
