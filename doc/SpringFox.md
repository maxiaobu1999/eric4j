# swagger
@ComponentScan
@ApiOperation(value = "接口说明", httpMethod = "接口请求方式", response = "接口返回参数类型", notes = "接口发布说明")
@ApiParam(required = "是否必须参数", name = "参数名称", value = "参数具体描述")

```java
@ApiModel(value="user对象",description="用户对象user")
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
     @ApiModelProperty(value="用户名",name="username",example="xingguo")
     private String username;
     @ApiModelProperty(value="状态",name="state",required=true)
      private Integer state;
      private String password;
      private String nickName;
      private Integer isDeleted;
 
      @ApiModelProperty(value="id数组",hidden=true)
      private String[] ids;
      private List<String> idList;
     //省略get/set
}

```

```text

@ApiImplicitParams：用在请求的方法上，表示一组参数说明
    @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
        name：参数名
        value：参数的汉字说明、解释
        required：参数是否必须传
        paramType：参数放在哪个地方
            · header --> 请求参数的获取：@RequestHeader
            · query --> 请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · body（不常用）
            · form（不常用）    
        dataType：参数类型，默认String，其它值dataType="Integer"       
        defaultValue：参数的默认值
        
         @ApiImplicitParams({
                    @ApiImplicitParam(name = "blogArticleBeen", value = "文档对象", required = true, paramType = "body", dataType = "BlogArticleBeen"),
                    @ApiImplicitParam(name = "path", value = "url上的数据", required = true, paramType = "path", dataType = "Long"),
                    @ApiImplicitParam(name = "query", value = "query类型参数", required = true, paramType = "query", dataType = "String"),
                    @ApiImplicitParam(name = "apiKey", value = "header中的数据", required = true, paramType = "header", dataType = "String")
            })

```

#使用swagger2展示API文档
下载[swagger-ui](https://github.com/swagger-api/swagger-ui)，
下载工程（不是README），解压后把里面的dist目录下的所有文件考入springMVC的静态资源下，
修改拷贝的index.html文件，替换url：

http://localhost:8088/swagger-ui.html
