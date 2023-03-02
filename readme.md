## grpc 跨语言调用

### 启动 RPC 的 JAVA 服务端和 Golang 客户端

```shell
cd java && mvn compile exec:java -Dexec.mainClass="com.ammo.grpcdemo.hello.HelloServer"
```

```shell
cd golang && go run client.go
```

### 结果

![](result.png)

