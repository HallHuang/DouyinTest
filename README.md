# BasicDouyinTest
本人设计开发的用于学习交流的抖音基本布局和基本视频功能实现，项目来源于个人接的私活，本人负责关键功能的原型实现，可以通过AndroidStudio在模拟器或手机上直接运行.   
<img src="http://m.qpic.cn/psc?/V5221UAJ45VcQO0F0NZY2GGZQG183Wwv/45NBuzDIW489QBoVep5mcYRBBJOQxEX0RjDT6glgqaXanK4wZB96n4uDG36N1KT5s47qbdw39WbA2IxC5RjcJ6e0rpZxJDHLM*mHuL75JE0!/b&bo=OAQkCQAAAAABFyE!&rf=viewer_4" width="200">


## 1 编程语言:   
   Kotlin. 

## 2 数据源:   
   客户方暂时没有网络后端API数据，本人特意设置测试目的的固定数据源，所有资源文件都在本地res中. 

## 3 架构模式:   
   MVVM.  
   1.由于没有网络后台数据，本应用没有添加基类(BaseActivity/BaseFragment/BaseViewModel)进行功能类继承式创建；
   2.由于客户项目功能较少，应要求不尽兴组件化/模块化开发

## 4 基本布局框架:   
   底部全局导航栏(“首页”、“朋友”、“分享”、“消息”、“我”: Fragment * 5)，其中本人主要实现了“首页”视频播放界面:布局是顶部滑动导航栏（“杭州”、“关注”、“推荐”: Fragment * 3)，其他“朋友”、“分享”、“消息”、“我”四个界面没有具体实现，期待其他开发者参与补充和修正
                
              
        
                
   

