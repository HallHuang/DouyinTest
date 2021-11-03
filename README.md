# DouyinTest
本人设计开发的用于学习交流的抖音基本布局和功能框架

#1 编程语言:   
   Kotlin. 

#2 数据源:   
   由于网络后台数据缺乏，本人特意设置测试目的的固定数据源，所有资源文件都在本地res中. 

#3 架构模式:   
   MVVM. 

#4 基本布局框架:   
   底部全局导航栏(“首页”、“朋友”、“分享”、“消息”、“我”: Fragment * 5)，本人主要实现了“首页”视频播放界面.   
    **“首页”界面：顶部滑动导航栏（“杭州”、“关注”、“推荐”: Fragment * 3.  
                 LocalFragment: 瀑布流布局（ RecyclerView(StaggeredLayout) ).  
                 AttentionFragment/RecommandFragment: 竖直方向滑动切换布局（RecyclerView(SnapPagerHelper)）. 
    **

