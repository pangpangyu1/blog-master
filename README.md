# 个人博客管理系统

## 1.开发背景

基于SpringBoot + jwt + thymeleaf + jpa + mysql的个人博客

使用Idea集成Git开发

## 2. 功能

角色：普通访客、管理员

### 访客

- 分页查看所有博客
- 分类查看博客
- 按标签查看博客
- 查看年限归档
- 查看博主信息
- 根据标题或者内容搜索博客
- 可以评论和回复

### 管理员

- 博客管理
- 分类管理
- 打标签
- 根绝关键字和分类搜索博客

## 3.开发中遇到的问题和细节

### 表关系：

- blog-comment 一对多

  一篇博客下可以有多条评论

- blog-user 多对一

  一篇博客只能属于一个用户，但一个用户可以有多篇博客

- blog-type 多对一

  一篇博客只能有一种类型，一种类型下可以有多篇博客

- comment-parentComment 多对一

  多条评论可以拥有共同的父评论

### 集成md编辑器

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

### 异常处理

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

### 日志处理

用spring aop对controller进行拦截

```java
       ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        //String ip = request.getRemoteAddr(); //可能拿不到真实ip
        String ip = IpInfoUtils.getIpAddr(request);	//考虑ip经过处理的情况

        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);
```

### 关于评论还有子评论

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
  ```

在进行评论查询Debug调试过程中，发现无法获取回复的评论，最后发现是因为@OneToMany的fetch参数默认设置为FetchType.Lazy模式，即懒加载模式。也就是说，我们查询comments的时候，并没有把该评论的回复查出来，所以debug模式下报错。把@OneToMany的fetch参数改为Fetch.EAGER，即热加载，就可以正常看到回复评论的获取。

> ```
> 当表A和表B一对多的关系
> 
> 对于A和B的实体类，设置FetchType=EAGER时，取A表数据，对应B表的数据都会跟着一起加载，优点不用进行二次查询。缺点是严重影响数据查询的访问时间。
> 
> 解决办法FetchType=LAZY，此时查询的访问时间大大缩短，缺点是查询表A 的数据时，访问不到表B的数据。
> ```



### 登录采用JWT验证，MD5对密码进行加密

用户进行登录，检测用户名密码正确后，生成一个token，将token保存在cookie中，用户信息保存在session中，用户访问后台页面都会通过token来获取session中的用户数据，在用户退出时删除token。

```java
@PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        //为什么不用Model？它适用的域只是转发域，重定向会刷掉。用redirect重定向请求后，model的值被清空了，所以造成了model数据丢失的情况。

            User user = userService.findByUsername(username);
            CookieUtils.delete(request, response, "tokenInvalid");
            if (user == null) {
                attributes.addFlashAttribute("message", "用户不存在！");
                return "redirect:/admin";
            }

            if (!user.getPassword().equals(MD5Utils.code(password))) { //密码不正确
                attributes.addFlashAttribute("message", "密码错误！");
                return "redirect:/admin";
            }
            //验证通过
            //生成token
//        System.out.println(user);
            String token = JWTUtils.createToken(user);
            //将token存储在cookie中
            CookieUtils.set(response, "token", token, -1);
            user.setPassword(null);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            return "redirect:/admin/index";
    }
```

## 4.总结

本次项目虽然内容比较少，但是个人之前学到的技术栈，大部分都有涵盖到，通过这个项目，熟练了各个技术栈的使用。