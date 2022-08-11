# myblog

## 一、开发背景

基于spring boot + jwt + thymeleaf + jpa + mysql的个人博客

jwt登录验证，thymeleaf模板引擎渲染页面，jpa框架简化数据库操作



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








- 关于评论还有子评论

    首先评论是会有子回复的，然后子回复也是可能会有子回复的，所以就采用了递归的写法

    ```java
  @Override
    public List<Comment> listCommentByBlogId(Long blogId) {

        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,Sort.by(Sort.Direction.ASC, "createTime"));
        return eachComment(comments);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }
    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }
    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.getCommentById(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
    ```

    

- 登录使用JWT完成验证

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

    


## 五、写在最后

本次项目虽然内容比较少，但是个人之前学到的技术栈，大部分都有涵盖到，通过这个项目，熟练了各个技术栈的使用，在遇到问题时，优先去源码中或者官方文档中寻找答案而不是直接百度。