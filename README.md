# spring-security-extension

## 项目简介

这是一个扩展Spring Security功能的开源项目，旨在增强身份验证和授权功能，包括验证码、Body缓存、多因素认证、账户锁定、CAS、一次性令牌、WebAuthn等功能。

## 功能列表

- **验证码功能**: 支持在登录过程中使用验证码以增强安全性。
- **Body缓存功能**: 可以缓存请求和响应的消息体，以便进行后续审计或分析。
- **多因素认证**: 支持使用多个身份验证因素以增加认证的安全性。
- **账户锁定**: 当出现多次登录失败时，可以锁定用户账户以保护安全。
- **CAS (Central Authentication Service)**: 增强Spring Security CAS单点登录功能，提供持久化能力。
- **一次性令牌**: 提供一次性令牌功能以增强身份验证。
- **WebAuthn**: 支持WebAuthn标准以提供更强大的身份验证。

## 安装和配置

要安装和配置此项目，请参阅 [安装和配置文档](docs/installation.md)。

## 使用示例

以下是一个简单的示例，演示如何在Spring Security配置中启用验证码功能：

```java
// Spring Security配置示例
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CaptchaAuthenticationFilter captchaAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 其他配置...
                .addFilterBefore(captchaAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

```

更多使用示例和配置详细信息，请参阅[使用文档](docs/usage.md)。

## 贡献指南

如果您希望为项目做出贡献，请查看贡献指南。

## 许可证

本项目采用[Apache 2.0 许可证](https://www.apache.org/licenses/LICENSE-2.0.html)。

## 致谢

我们要感谢所有为该项目做出贡献和提供支持的人。
