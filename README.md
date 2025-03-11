
# 简介
本组件是一个基于Spring Boot的Lua脚本执行和管理工具，支持动态加载、执行和管理Lua脚本。通过注解和接口代理的方式，开发者可以轻松调用Lua脚本，并支持内存化管理（LRU缓存）、动态装配Lua脚本等功能。

# 功能特性
1.自动装配Lua脚本代理对象：基于接口注解调用Lua脚本。

2.内存化管理：支持LRU缓存机制，提升脚本执行效率。

3.Mysql持久化：支持将Lua脚本持久化到Mysql数据库（待实现）。

4.动态装配Lua脚本：支持运行时动态加载和更新Lua脚本（待实现）。

5.集群管理：支持多节点集群环境下的Lua脚本管理（待实现）。


# 组件
![image](https://github.com/user-attachments/assets/17516334-e121-40bd-9486-a93f90db8368)

# 实现原理
![Uploading image.png…](![image](https://github.com/user-attachments/assets/05177cbd-7769-4cb2-a321-073ac69312b5)

# 基本使用
注意springboot的装配版本，如有冲突可自行拉取代码修改
```
<dependency>
            <groupId>lua.xgwd.me</groupId>
            <artifactId>lua-load</artifactId>
            <version>1.0-RELEASE</version>
        </dependency>
```

``` 
public static void main(String[] args) {
    ConfigurableApplicationContext run = SpringApplication.run(Sp.class, args);
    // 通过直接获取接口代理注入
    LuaMapper1 luaMapper1 = run.getBean(LuaMapper1.class);
    System.out.println(luaMapper1.luaScpt(new String[]{"k1", "k2"}, new String[]{"v1", "v2"}));

    // 通过工厂注入
    LuaMapperFactory luaMapperFactory = run.getBean(LuaMapperFactory.class);
    LuaMapper1 mapper = luaMapperFactory.getMapper(LuaMapper1.class);
    Long aLong = mapper.luaScpt(new String[]{"k1", "k2"}, new String[]{"v1", "v2"});
}
```
