# ES安装

采用brew安装

```
brew update
brew install elasticsearch
```

默认安装路径：/usr/local/Cellar/elasticsearch/7.10.2

```
# 启动
elasticsearch
# 或
brew services start elasticsearch
# 关闭
brew services stop elasticsearch
```

访问：http://localhost:9200

# IK分词器安装

下载相同版本ik，拷贝到es安装路径/usr/local/Cellar/elasticsearch/7.10.2/plugins/ik/目录下

在/usr/local/Cellar/elasticsearch/7.10.2/bin 路径下执行：

```
elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.10.2/elasticsearch-analysis-ik-7.10.2.zip
```

# kibana安装

```
brew install kibana
# 启动
kibana
# 或
brew services start kibana
# 停止
brew services stop kibana
```

访问：http://localhost:5601





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