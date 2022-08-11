# myblog

## 一、开发背景

基于spring boot + jwt + thymeleaf + mybatis + redis + mysql的个人博客

jwt登录验证，thymeleaf模板引擎渲染页面，mybatis框架简化数据库操作，redis和spring-boot-cache做中间缓存



## 二、功能

角色：普通访客、管理员

### 访客

- 分页查看所有博客

- 查看博客数最多的几个分类

- 查看所有分类

- 查看某个分类下的博客列表

- 根据年度时间线查看博客

- 查看最新的博客

- 关键字全局搜索博客

- 查看单个博客内容

- 对博客进行评论

- 赞赏博客

- 微信扫码阅读博客


### 管理员

- 用户名密码登录

- 管理博客
    - 发布博客
    - 分类
    - 打标签
    - 修改博客
    - 删除博客
    - 根据标题、分类、标签查看博客
    
- 管理博客分类 

    

## 三、开发规范

个人项目，所以代码直接推送至主分支。

### 3.1、文件tree

```
C:.
│  .gitattributes
│  .gitignore
│  pom.xml
│  README.md
│
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─yiqiandewo
│  │  │          │  BlogApplication.java
│  │  │          │
│  │  │          ├─aspect
│  │  │          │      LogAspect.java
│  │  │          │
│  │  │          ├─config
│  │  │          │      MVCConfig.java
│  │  │          │      RedisConfig.java
│  │  │          │
│  │  │          ├─controller
│  │  │          │  │  AboutController.java
│  │  │          │  │  ArchivesController.java
│  │  │          │  │  CommentController.java
│  │  │          │  │  IndexController.java
│  │  │          │  │  TypeShowController.java
│  │  │          │  │
│  │  │          │  └─admin
│  │  │          │          BlogController.java
│  │  │          │          LoginController.java
│  │  │          │          TypeController.java
│  │  │          │
│  │  │          ├─handler
│  │  │          │      ControllerExceptionHandler.java
│  │  │          │
│  │  │          ├─interceptor
│  │  │          │      JWTInterceptor.java
│  │  │          │
│  │  │          ├─mapper
│  │  │          │      BlogMapper.java
│  │  │          │      CommentMapper.java
│  │  │          │      TypeMapper.java
│  │  │          │      UserMapper.java
│  │  │          │
│  │  │          ├─pojo
│  │  │          │      Blog.java
│  │  │          │      Comment.java
│  │  │          │      Type.java
│  │  │          │      User.java
│  │  │          │
│  │  │          ├─service
│  │  │          │  │  BlogService.java
│  │  │          │  │  CommentService.java
│  │  │          │  │  TypeService.java
│  │  │          │  │  UserService.java
│  │  │          │  │
│  │  │          │  └─impl
│  │  │          │          BlogServiceImpl.java
│  │  │          │          CommentServiceImpl.java
│  │  │          │          TypeServiceImpl.java
│  │  │          │          UserServiceImpl.java
│  │  │          │
│  │  │          └─util
│  │  │                  CookieUtils.java
│  │  │                  JWTUtils.java
│  │  │                  MarkdownUtils.java
│  │  │                  MD5Utils.java
│  │  │
│  │  └─resources
│  │      │  application.yml
│  │      │
│  │      ├─com
│  │      │  └─yiqiandewo
│  │      │      └─mapper
│  │      │              BlogMapper.xml
│  │      │              CommentMapper.xml
│  │      │              TypeMapper.xml
│  │      │              UserMapper.xml
│  │      │
│  │      ├─i18n
│  │      │      messages.properties
│  │      │
│  │      ├─static
│  │      │  ├─css
│  │      │  │      animate.css
│  │      │  │      me.css
│  │      │  │      semantic.min.css
│  │      │  │      typo.css
│  │      │  │
│  │      │  ├─fonts
│  │      │  │      brand-icons.ttf
│  │      │  │      brand-icons.woff
│  │      │  │      brand-icons.woff2
│  │      │  │      font.css
│  │      │  │      icons.ttf
│  │      │  │      icons.woff
│  │      │  │      icons.woff2
│  │      │  │      S6u8w4BMUTPHjxsAXC-vNiXg7Q.ttf
│  │      │  │      S6u9w4BMUTPHh6UVSwiPHA3q5d0.ttf
│  │      │  │      S6uyw4BMUTPHjx4wWyWtFCc.ttf
│  │      │  │      S6u_w4BMUTPHjxsI5wq_Gwfox9897g.ttf
│  │      │  │
│  │      │  ├─images
│  │      │  │      avatar.png
│  │      │  │      bg.png
│  │      │  │      favicon.ico
│  │      │  │      MyWeChat.png
│  │      │  │      wx.png
│  │      │  │      zfb.jpg
│  │      │  │
│  │      │  ├─js
│  │      │  │      jquery.min.js
│  │      │  │      semantic.min.js
│  │      │  │
│  │      │  └─lib
│  │      │      ├─editormd
│  │      │      │  │  editormd.js
│  │      │      │  │  editormd.min.js
│  │      │      │  ├─css 
|  |      |      |  |      ...
│  │      │      │  ├─fonts
|  |      |      |  |       ...
│  │      │      │  ├─images
|  |      |      |  |        ...
│  │      │      │  ├─languages
|  |      |      |  |          ...
│  │      │      │  ├─lib
|  |      |      |  |      ...
│  │      │      │  └─plugins
|  |      |      |           ...
│  │      │      │
│  │      │      ├─prism
│  │      │      │      prism.css
│  │      │      │      prism.js
│  │      │      │
│  │      │      ├─qrcode
│  │      │      │      qrcode.js
│  │      │      │      qrcode.min.js
│  │      │      │
│  │      │      ├─scrollTo
│  │      │      │      jquery.scrollTo.js
│  │      │      │      jquery.scrollTo.min.js
│  │      │      │
│  │      │      ├─tocbot
│  │      │      │      tocbot.css
│  │      │      │      tocbot.min.js
│  │      │      │
│  │      │      └─waypoints
│  │      │              jquery.waypoints.js
│  │      │              jquery.waypoints.min.js
│  │      │
│  │      └─templates
│  │          │  about.html
│  │          │  archives.html
│  │          │  blog.html
│  │          │  index.html
│  │          │  search.html
│  │          │  types.html
│  │          │
│  │          ├─admin
│  │          │      blogs-input.html
│  │          │      blogs.html
│  │          │      index.html
│  │          │      login.html
│  │          │      types-input.html
│  │          │      types.html
│  │          │
│  │          ├─commons
│  │          │      bar.html
│  │          │
│  │          └─error
│  │                  404.html
│  │                  500.html
│  │                  error.html
│  │
│  └─test
│      └─java
│          └─com
│              └─yiqiandewo
│                      BlogApplicationTests.java
```

> windows生成文件树的方法

在powershell中打开项目目录

命令：

```
tree /f
```

------



## 四、开发中遇到的问题以及一些细节

### 开发细节：

- mysql表的设计

    er图

    ![ER图](https://i.loli.net/2021/03/24/79hCOtSdgEPDr32.png)

    表关系：

    - blog-comment 一对多

        一篇博客下可以有多条评论

    - blog-user 多对一

        一篇博客下只能属于一个用户，但一个用户可以有多篇博客

    - blog-type 多对一

        一篇博客只能有一种类型，一种类型下可以有多篇博客

    - comment-childComment 一对多

        一条评论下可以有多个子评论

        

- 页面冗余部分

    使用thymeleaf的`th:fragmenthe`和`th:replace`进行简化

    将冗余的标签加上`th:fragment`属性并命名，全部加载新的一个html文件中

    将原本的部分用`th:replace`替换

    > **导航栏的高亮**
    >
    > 点击某个页面的时候，加上一个属性activeUri，然后在目标页面中通过该属性的值判断哪个需要高亮

    ```html
    <nav class="ui inverted attached segment m-padded-tb-mini m-shadow-small" th:fragment="topbar">
        <div class="ui container">
            <div class="ui inverted secondary stackable menu"> <!--stackable手机移动端相应，屏幕小时会将内容堆叠到一起-->
                <h2 class="ui teal header item">Blog</h2>
                <a href="#" th:href="@{/index}" class="m-item item m-mobile-hide" th:class="${activeUri=='index'?'active m-item item m-mobile-hide':'m-item item m-mobile-hide'}"><i class="home icon"></i>首页</a>
                <a href="#" th:href="@{/types/-1}" class="m-item item m-mobile-hide" th:class="${activeUri=='types'?'active m-item item m-mobile-hide':'m-item item m-mobile-hide'}"><i class="tags icon"></i>分类</a>
                <a href="#" th:href="@{/archives}" class="m-item item m-mobile-hide" th:class="${activeUri=='archives'?'active m-item item m-mobile-hide':'m-item item m-mobile-hide'}"><i class="clone icon"></i>归档</a>
                <a href="#" th:href="@{/about}" class="m-item item m-mobile-hide" th:class="${activeUri=='about'?'active m-item item m-mobile-hide':'m-item item m-mobile-hide'}"><i class="info icon"></i>关于我</a>
                <div class="right m-item item m-mobile-hide">
                    <form name="search" action="#" th:action="@{/search}" method="post" target="_blank">
                        <div class="ui icon inverted input m-margin-tb-tiny">
                            <input type="text" name="query" placeholder="Search..." th:value="${query}">
                            <i onclick="document.forms['search'].submit()" class="search icon link"></i>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <a href="#" class="ui menu toggle black icon button m-right-top m-mobile-show">
            <i class="sidebar icon"></i>
        </a>
    </nav>
    ```

    然后在需要用的页面中直接`th:replace`调用就可以了

    ```html
    <div th:replace="commons/bar::topbar(activeUri='blog')"></div>
    ```

    

- 集成md编辑器

    使用的是editormd

    ```js
    //初始化Markdown编辑器
        var contentEditor;
        $(function () {
            contentEditor = editormd("md-content",{
                width:"100%",
                height:640,
                syncScrolling:"single",
                path:"../lib/editormd/lib/"  //路径一定要正确
            });
        });
    ```

    ```html
    <div class="field">
        <div id="md-content" style="z-index: 1 !important;"><!--将markdown文本框放到最上方以免全屏布局错乱-->
            <textarea name="content" placeholder="博客内容" style="display: none;">
                ### Disabled options
                - TeX (Based on KaTeX);
                -Emoji;
                -Task lists;
                -HTML tags decode;f
            </textarea>
        </div>
    </div>
    ```

- 异常处理

    > 在templates目录下的error目录，springboot会自动根据错误状态码找到对应的错误页面（html页面以状态码命名）

    同时配置异常处理器

    ```java
    @ControllerAdvice //拦截所有Controller的控制器
    public class ControllerExceptionHandler {
    
        private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class); //log4j
    
        @ExceptionHandler(Exception.class) //异常处理
        public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
            logger.error("Request Url : {}, exception : {}", request.getRequestURL(), e); //打印访问的url和异常信息
            ModelAndView mv = new ModelAndView();
            mv.addObject("url", request.getRequestURL());
            mv.addObject("exception", e);
            mv.setViewName("error/error");  //跳转到error.html
            return mv;
        }
    }
    ```

- 日志处理

    用spring aop对controller进行拦截

    用环绕通知

    ```java
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String url = request.getRequestURL().toString();  //访问的url
    String ip = request.getRemoteAddr();   //ip
    String classMethod = pcj.getSignature().getDeclaringTypeName() + "." + pcj.getSignature().getName();  //方法
    Object[] args = pcj.getArgs();  //参数
    RequestLog requestLog = new RequestLog(url, ip, classMethod, args);  //封装
    logger.info("Request : {}" ,requestLog);
    ```



- pageHelper的细节

    采用的是mybatis的xml方式实现分页查询

    > 在xml中写sql时不应在最后加上分号，因为pageHelper在帮我们分页时，会根据参数pageNo和pageSize给sql语句加limit，如果加上分号会报sql语法错误。



- 后端的数据校验

    采用`validation`

    首先`pom.xml`

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
        <version>2.4.3</version>
    </dependency>
    ```

    首先在要校验的字段上叫注解

    ```java
    @NotBlank(message = "分类名称不能为空")
    private String name;
    ```

    给需要校验的数据加上`@Valid`，BindingResult result是校验结果

    ```java
    @PostMapping("/types")
    public String addType(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        if (typeService.queryByName(type.getName()) != null) {
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.addType(type);
        if (t == null) {
            attributes.addFlashAttribute("errMsg","新增失败");
        } else {
            attributes.addFlashAttribute("successMsg","新增成功");
        }
        return "redirect:/admin/types";  //返回到 /admin/type 请求 再去查询
    }
    ```

    

    ```html
    <form action="#" method="post" th:if="*{id}==null" th:object="${type}" th:action="@{/admin/types}" class="ui form">
        <div class="required field">
            <div class="ui left labeled input">
                <label class="ui teal basic label">名称</label>
                <input type="text" name="name" placeholder="分类名称" th:value="*{name}">
            </div>
        </div>
    
        <div class="ui error message"></div>
        <!-- 这里的*{id} 其实就相当于${type.id}，就是从对象中拿属性的值 -->
    
        <!-- 
    		前端页面接收  下面这种注释thymeleaf仍然可以识别
    		th:if里的是要验证的name域是否有错误
    		th:errors="*{name}" 会从@NotBlank中的message拿
    		如果这样写,会报错，需要在上面加一个th:object="${type}"，就是从后端拿到一个Type对象，然后th:value="*{name}"，就是拿到type里的             value值,然后注意还需要从后端传一个type对象
        -->
        <!--/*/
        <div class="ui negative message" th:if="${#fields.hasErrors('name')}">
        <i class="close icon"></i>
        <div class="header">操作失败</div>
        <p th:errors="*{name}">提交信息不符合规则</p>
        </div>
        /*/-->
        <!--操作按钮-->
        <div class="ui right aligned container">
            <button type="button" class="ui button" onclick="window.history.go(-1)">返回</button>
            <button class="ui teal submit button">提交</button>
        </div>
    </form>
    ```

    

    

- 新增和编辑用同一个页面

    用了两个form表单是因为使用了restful api，然后新增是post请求，修改是put请求（put请求需要配置`<input type="hidden" name="_method" value="put">`），然后不让两个表单同时生效，使用了th:if，由于修改请求会使用id将需要修改的name输入在页面上，而新增不需要，所以两者的区别就是 数据检验使用的type对象的id是否为null。

    >**springboot web配置put，delete请求**
    >
    >SpringBoot 2.2.X默认不支持put，delete等请求方式的。
    >
    >1.SpringMVC配置HiddenHttpMethodFilter (SpringBoot自动配置) 2.页面创建post表单 3.创建input name="_method" 值就是指定的请求方式
    >
    >```
    ><form id="delForm" method="post">
    >    <input type="hidden" name="_method" value="delete"/>
    ></form>
    >```
    >
    >同时还要在`application.yml`中配置
    >
    >```
    >spring:
    >  mvc:
    >    hiddenmethod:
    >      filter:
    >        enabled: true # Spring Boot 的 META-INF/spring-configuration-metadata.json 配置文件中默认是关闭 Spring 的 hiddenmethod 过滤器的。
    >        # 这时候需要通Springboot配置文件application.yml/properties 中将 hiddenmethod 过滤器设置为启用即可。 # 可以配置delete put等请求
    >```
    >
    >
    
    ```html
    <form action="#" method="post" th:if="*{id}==null" th:object="${type}" th:action="@{/admin/types}" class="ui form">
        <div class="required field">
            <div class="ui left labeled input">
                <label class="ui teal basic label">名称</label>
                    <input type="text" name="name" placeholder="分类名称" th:value="*{name}">
            </div>
         </div>
    
         <div class="ui error message"></div>
         <!--/*/
         <div class="ui negative message" th:if="${#fields.hasErrors('name')}">
             <i class="close icon"></i>
             <div class="header">操作失败</div>
             <p th:errors="*{name}">提交信息不符合规则</p>
         </div>
         /*/-->
         <!--操作按钮-->
         <div class="ui right aligned container">
             <button type="button" class="ui button" onclick="window.history.go(-1)">返回</button>
             <button class="ui teal submit button">提交</button>
         </div>
    </form>
    
    <form action="#" method="post" th:if="*{id}!=null" th:object="${type}" th:action="@{/admin/types/{id}(id=*{id})}" class="ui form">
         <input type="hidden" name="id" th:value="*{id}">
         <input type="hidden" name="_method" value="put">
         <div class="required field">
             <div class="ui left labeled input">
                 <label class="ui teal basic label">名称</label>
                 <input type="text" name="name" placeholder="分类名称" th:value="*{name}">
             </div>
         </div>
         <div class="ui error message"></div>
         <!--/*/
         <div class="ui negative message" th:if="${#fields.hasErrors('name')}">
             <i class="close icon"></i>
             <div class="header">操作失败</div>
             <p th:errors="*{name}">提交信息不符合规则</p>
         </div>
         /*/-->
         <!--操作按钮-->
         <div class="ui right aligned container">
             <button type="button" class="ui button" onclick="window.history.go(-1)">返回</button>
             <button class="ui teal submit button">提交</button>
         </div>
    </form>
    ```




- Ajax异步请求

    表单的多项查询，查询的时候需要带查询框里的参数过去，所有直接用 ajax刷新页面的form表单里的内容

    ```html
    <div id="table-container">
        <!--表格-->
        <table class="ui compact teal table" th:fragment="blogList">
            ...
            <tfoot>
                <tr>
                    <th colspan="7">
                        <div class="ui mini pagination menu" th:if="${pageInfo.pages}>1">
                            <a onclick="page(this)" th:attr="data-page=${pageInfo.pageNum-1}" class="item" th:unless="${pageInfo.isFirstPage}">上一页</a>
                            <a onclick="page(this)" th:attr="data-page=${pageInfo.pageNum+1}" class="item" th:unless="${pageInfo.isLastPage}">下一页</a>
                        </div>
                        <a href="#" th:href="@{/admin/blogs/input}" class="ui mini right floated teal basic button">新增</a>
                    </th>
                </tr>
            </tfoot>
        </table>
        ...
    </div>
    ```

    js

    ```javascript
    function page(obj) {
        $("[name='page']").val($(obj).attr("data-page")); //点击上一页 下一页 也是使用ajax 同时传递参数page
        loadData();
    }
    
    $("#search-btn").click(function () {
        $("[name='page']").val(0);
        loadData();
    });
    
    function loadData() {
        $("#table-container").load(/*[[@{/admin/blogs/search}]]*/"/admin/blogs/search", {
            title : $("[name='title']").val(),
            typeId : $("[name='type']").val(),//typeId会封装进controller中的与它参数名一致的参数 name='type' 然后找到对应name的value值
            recommend : $("[name='recommend']").prop('checked'),
            page : $("[name='page']").val(),
        });
    }
    ```

    controller

    ```java
    @PostMapping("/blogs/search")
    public String search(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                         @RequestParam(name = "size", required = true, defaultValue = "3") Integer size,
                         Model model, String title, Long typeId, boolean recommend) {
        PageInfo<Blog> pageInfo = blogService.queryConditional(page, size, title, typeId, recommend);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs :: blogList";  //这里只刷新页面中 fragment 里的内容
    }
    ```



- mybatis一对多的细节

    ```xml
    <resultMap id="allBlogMap" type="com.yiqiandewo.pojo.Type">
        <id property="id" column="id"></id>
        <result property="name" column="name"></result>
    
        <collection property="blogs" ofType="com.yiqiandewo.pojo.Blog">
            <!-- 如果有一个字段是主键，则标签只能写result，不能写id，否则只能查出一条数据 -->
            <result property="title" column="title"></result>
            <result property="type.id" column="type_id"></result>
        </collection>
    </resultMap>
    ```



- 关于评论还有子评论

    首先评论是会有子回复的，然后子回复也是可能会有子回复的，所以就采用了递归的写法

    然后再写递归的过程中，遇到了问题，就是无论如何都不能找出所有的子评论

    在进行了几次debug后，发现了recReply()中找出的子评论的回复，是无法被返回的，所以设置了一个全局的List

    ```java
    private List<Comment> tempList = new ArrayList<>();
    
    @Override
    public List<Comment> queryAllByBlogId(Long blogId) {
        //首先查询出所有没有parent的comment
        List<Comment> list = commentMapper.queryAllByBlogId(blogId);
    
        for (Comment comment : list) {
            List<Comment> replyList = commentMapper.queryAllReplyById(comment.getId());
            for (Comment reply : replyList) {  //回复1...
                tempList.add(reply);  //这里也需要将reply子回复加进去
                //回复1的回复2...
                recReply(reply);
            }
            comment.setReplyComment(tempList);  //这里设置子评论的时候参数就是tempList
            tempList = new ArrayList<>();  //由于有多个评论，将一个评论的子回复设置好，就需将该list重置，否则之前的数据也在里面
        }
    
        return list;
    }
    
    private void recReply(Comment comment) {
        List<Comment> replyList = commentMapper.queryAllReplyById(comment.getId());
        if (replyList.size() > 0) {
            for (Comment reply : replyList) {
                tempList.add(reply);  //如果不用全局变量  这个reply将无法返回
                if (commentMapper.queryAllReplyById(reply.getId()).size() > 0) {
                    //回复1的回复2的回复...
                    recReply(reply);
                }
            }
        }
    }
    ```

    

- 登录使用JWT完成验证



- 使用redis缓存

    (1) 速度快，因为数据存在内存中，类似于HashMap，HashMap的优势就是查找和操作的时间复杂度都是O(1)

    (2) 支持丰富数据类型，支持string，list，set，sorted set，hash

    (3) 支持事务，操作都是原子性，所谓的原子性就是对数据的更改要么全部执行，要么全部不执行

    (4) 丰富的特性：可用于缓存，消息，按key设置过期时间，过期后将会自动删除

type



blog

- zset完成排行榜
- zset+hash 缓存分页





- spring boot error页面配置

    将错误页面放在templates/error文件下，并将文件名改为 错误状态码.html（404.html、403.html），如果出现404，就会自动跳转404.html

- 使用restful

    `controller`中全部使用restful风格的请求方式，根据前端发送的不同请求

    GET用来获取资源，POST用来新建资源（也可以用于更新资源），PUT用来更新资源，DELETE用来删除资源；

- spring boot 2.2.x 项目名设置

    ```yaml
    server:
      servlet:
        context-path: /firstDemo # 项目名 # ContextPath must start with '/' and not end with '/'
    ```

    

- 3.3、redis快照出错

    redis再次启动时，发现redis中的缓存的key全部变为backup

    百度过后，是因为没有设置密码。

    

### 遇到的问题:

- 页面加载的很慢 需要10多s才能加载完成

    问题本质，通过F12可以看出，访问页面时间都浪费在https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic&subset=latin（这个页面是senamtic.min.css请求的）上了，这是谷歌字体，服务器在外国，请求半天最终结果是无法访问；解决办法，将字体下载到本地，将senamtic.min.css也下载到本地（https://files-cdn.cnblogs.com/files/tekikesyo/LocalGoogleFont.zip），并进行替换，在第11行f
    速度问题已经解决了，但是F12发现有3个请求报错，分别是icons.ttf、icons.woff、icons.woff2；解决方法，将相关文件导入（文件在senamtic官网下载的压缩包里面）

- 存在的问题

    ```html
    <td>
        <div class="gridContainer">
            <form th:action="@{/admin/blogs/}+${blog.id}" style="float: left" class="bid-floor-input" method="get">
                <button type="submit" class="ui mini teal basic button">编辑</button>
            </form>
            <form th:action="@{/admin/blogs/}+${blog.id}" class="bid-floor-save" style="float: right" method="post">
                <input type="hidden" name="_method" value="delete">
                <button type="submit" class="ui mini red basic button">删除</button>
            </form>
        </div>
    </td>
    ```

    由于使用的是springmvc配置的delete请求去删除，如果设置a标签，虽然样式没问题，但是在sciprt标签中的绑定的click事件会因为ajax请求提交、渲染页面后失效，导致删除不能用，所以就直接改成了两个form表单

## 五、写在最后

本次项目虽然内容比较少，但是个人之前学到的技术栈，大部分都有涵盖到，通过这个项目，熟练了各个技术栈的使用，在遇到问题时，优先去源码中或者官方文档中寻找答案而不是直接百度。