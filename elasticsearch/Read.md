# 安装ES

## brew安装

```shell
brew update
brew install elasticsearch
## 安装最新版本
brew tap elastic/tap
brew install elastic/tap/elasticsearch-full

```

默认安装路径：/usr/local/Cellar/elasticsearch 或 /usr/local/Cellar/elasticsearch-full

```shell
# 启动
elasticsearch
```

访问：http://localhost:9200

## 配置用户密码

```shell
# 修改es配置文件
http.cors.enabled: true
http.cors.allow-origin: "*"
http.cors.allow-headers: Authorization
xpack.security.enabled: true
xpack.security.transport.ssl.enabled: true
# bin路径下执行设置密码
elasticsearch-setup-passwords interactive
```

### 配置SSL

```shell
#生成ca证书
elasticsearch-certutil ca
# 生成证书和私钥
elasticsearch-certutil cert --ca elastic-stack-ca.p12
# 拷贝到cert目录
cp elastic-certificates.p12 config/cert
# bin路径下执行
elasticsearch-certgen
 #####################################
 Please enter the desired output file [certificate-bundle.zip]: cert.zip  （生成的压缩包名称，输入或者保持默认，直接回车）
 Enter instance name: my-application (实例名)
 Enter name for directories and files [my-application]: elasticsearch（存储实例证书的文件夹名，可以随意指定或保持默认）
 Enter IP Addresses for instance (comma-separated if more than one) []: 127.0.0.1(实例ip，多个ip用逗号隔开)
 Enter DNS names for instance (comma-separated if more than one) []: node-1（节点名，多个节点用逗号隔开）
 Would you like to specify another instance? Press 'y' to continue entering instance information: (到达这一步,不需要按y重新设置,按空格键就完成了)
 Certificates written to /usr/local/elasticsearch/bin/cert.zip（这个是生成的文件存放地址，不用填写）
 ## 将cert.zip 解压到config目录
 
 keytool -genkeypair -alias metrics -storepass "xxxx" -storetype JKS  -keystore d:\dev_tmp\tls\metrics.keystore

```

```shell
cluster.name: my-elasticsearch
node.name: node-1
path.data: /opt/var/elasticsearch/data
path.logs: /opt/var/elasticsearch/logs
network.host: 192.168.1.100
http.port: 9200
transport.tcp.port: 9300
transport.tcp.compress: true
discovery.zen.ping.unicast.hosts: ["192.168.1.100", "192.168.1.101", "192.168.1.102"]
discovery.zen.minimum_master_nodes: 2
http.cors.enabled: true
http.cors.allow-origin: "*"
http.cors.allow-headers: Authorization,X-Requested-With,Content-Type,Content-Length
xpack.http.ssl.key: cert/elasticsearch/elasticsearch.key
xpack.http.ssl.certificate: cert/elasticsearch/elasticsearch.crt
xpack.http.ssl.certificate_authorities: cert/ca/ca.crt

# 开启 X-PACK 认证
xpack.security.enabled: true
# 开启 X-PACK 认证
xpack.security.transport.ssl.enabled: true
# 设置认证方
xpack.security.transport.ssl.verification_mode: certificate 
# 证书文件
xpack.security.transport.ssl.keystore.path: cert/elastic-certificates.p12 
xpack.security.transport.ssl.truststore.path: cert/elastic-certificates.p12 
```



## 安装IK分词器

下载 https://github.com/medcl/elasticsearch-analysis-ik/releases

下载相同版本ik，拷贝到es安装路径/usr/local/Cellar/elasticsearch-full/7.14.1/plugins/analysis-ik/目录下

```shell
## 在/usr/local/Cellar/elasticsearch-full/7.14.1/bin 路径下执行：
elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.14.1/elasticsearch-analysis-ik-7.14.1.zip
## 查看插件
elasticsearch-plugin list
```

## 安装ICU分词器

```shell
## 在/usr/local/Cellar/elasticsearch-full/7.14.1/bin 路径下执行：
elasticsearch-plugin install analysis-icu
```

# 安装Metricbeat

```shell
curl -L -O https://artifacts.elastic.co/downloads/beats/metricbeat/metricbeat-7.14.1-darwin-x86_64.tar.gz
tar xzvf metricbeat-7.14.1-darwin-x86_64.tar.gz
```



# 安装kibana

```shell
brew install kibana
# 或
brew tap elastic/tap
brew install elastic/tap/kibana-full
```

### 配置文件

```shell
i18n.locale: "zh-CN"
```

### 配置es

```shell
elasticsearch.hosts: ["http://localhost:9200"]
elasticsearch.username: "elastic"
elasticsearch.password: "es888888"
```



访问：http://localhost:5601

# 安装Logstash

```shell
brew install elastic/tap/logstash-full
```



| Keyword             | Sample                             | Elasticsearch Query String                                   |
| :------------------ | :--------------------------------- | :----------------------------------------------------------- |
| And                 | findByNameAndPrice                 | {“bool” : {“must” : [ {“field” : {“name” : “?”}}, {“field” : {“price” : “?”}} ]}} |
| Or                  | findByNameOrPrice                  | {“bool” : {“should” : [ {“field” : {“name” : “?”}}, {“field” : {“price” : “?”}} ]}} |
| Is                  | findByName                         | {“bool” : {“must” : {“field” : {“name” : “?”}}}}             |
| Not                 | findByNameNot                      | {“bool” : {“must_not” : {“field” : {“name” : “?”}}}}         |
| Between             | findByPriceBetween                 | {“bool” : {“must” : {“range” : {“price” : {“from” : ?,“to” : ?,“include_lower” : true,“include_upper” : true}}}}} |
| LessThanEqual       | findByPriceLessThan                | {“bool” : {“must” : {“range” : {“price” : {“from” : null,“to” : ?,“include_lower” : true,“include_upper” : true}}}}} |
| GreaterThanEqual    | findByPriceGreaterThan             | “bool” : {“must” : {“range” : {“price” : {“from” : ?,“to” : null,“include_lower” : true,“include_upper” : true}}}}} |
| Before              | findByPriceBefore                  | {“bool” : {“must” : {“range” : {“price” : {“from” : null,“to” : ?,“include_lower” : true,“include_upper” : true}}}}} |
| After               | findByPriceAfter                   | {“bool” : {“must” : {“range” : {“price” : {“from” : ?,“to” : null,“include_lower” : true,“include_upper” : true}}}}} |
| Like                | findByNameLike                     | {“bool” : {“must” : {“field” : {“name” : {“query” : “?*”,“analyze_wildcard” : true}}}}} |
| StartingWith        | findByNameStartingWith             | {“bool” : {“must” : {“field” : {“name” : {“query” : “?*”,“analyze_wildcard” : true}}}}} |
| EndingWith          | findByNameEndingWith               | {“bool” : {“must” : {“field” : {“name” : {“query” : “*?”,“analyze_wildcard” : true}}}}} |
| Contains/Containing | findByNameContaining               | {“bool” : {“must” : {“field” : {“name” : {“query” : “?”,“analyze_wildcard” : true}}}}} |
| In                  | findByNameIn(Collectionnames)      | {“bool” : {“must” : {“bool” : {“should” : [ {“field” : {“name” : “?”}}, {“field” : {“name” : “?”}} ]}}}} |
| NotIn               | findByNameNotIn(Collectionnames)   | {“bool” : {“must_not” : {“bool” : {“should” : {“field” : {“name” : “?”}}}}}} |
| Near                | findByStoreNear                    | Not Supported Yet !                                          |
| True                | findByAvailableTrue                | {“bool” : {“must” : {“field” : {“available” : true}}}}       |
| False               | findByAvailableFalse               | {“bool” : {“must” : {“field” : {“available” : false}}}}      |
| OrderBy             | findByAvailableTrueOrderByNameDesc | {“sort” : [{ “name” : {“order” : “desc”} }],“bool” : {“must” : {“field” : {“available” : true}}}} |