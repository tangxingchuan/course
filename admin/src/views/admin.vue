<template>
    <div>

        <!--头部栏-->
        <div id="navbar" class="navbar navbar-default          ace-save-state">
            <div class="navbar-container ace-save-state" id="navbar-container">
                <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
                    <span class="sr-only">Toggle sidebar</span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>
                </button>

                <div class="navbar-header pull-left">
                    <a href="index.html" class="navbar-brand">
                        <small>
                            <i class="fa fa-leaf"></i>
                            左眼 后台控制
                        </small>
                    </a>
                </div>

                <div class="navbar-buttons navbar-header pull-right" role="navigation">
                    <ul class="nav ace-nav">
                        <li class="grey dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-tasks"></i>
                                <span class="badge badge-grey">4</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-check"></i>
                                    4 待完成的任务
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar">
                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">软件更新</span>
                                                    <span class="pull-right">65%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:65%" class="progress-bar"></div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">硬件升级</span>
                                                    <span class="pull-right">35%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:35%" class="progress-bar progress-bar-danger"></div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">单元测试</span>
                                                    <span class="pull-right">15%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:15%" class="progress-bar progress-bar-warning"></div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Bug修复</span>
                                                    <span class="pull-right">90%</span>
                                                </div>

                                                <div class="progress progress-mini progress-striped active">
                                                    <div style="width:90%" class="progress-bar progress-bar-success"></div>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">
                                        查看包含详细信息的任务
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="purple dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-bell icon-animated-bell"></i>
                                <span class="badge badge-important">8</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-exclamation-triangle"></i>
                                    8 条通知
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar navbar-pink">
                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-pink fa fa-comment"></i>
														新评论
													</span>
                                                    <span class="pull-right badge badge-info">+12</span>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <i class="btn btn-xs btn-primary fa fa-user"></i>
                                                鲍勃刚刚注册为编辑...
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-success fa fa-shopping-cart"></i>
														新订单
													</span>
                                                    <span class="pull-right badge badge-success">+8</span>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-info fa fa-twitter"></i>
														追随者
													</span>
                                                    <span class="pull-right badge badge-info">+11</span>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">
                                        查看所有通知
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="green dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
                                <span class="badge badge-success">5</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-envelope-o"></i>
                                    5 Messages
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar">
                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" />
                                                <span class="msg-body">
													<span class="msg-title">
														<span class="blue">亚历克斯:</span>
														 我想玩剑魔了，毕竟他叫亚托克斯
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>a moment ago</span>
													</span>
												</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar3.png" class="msg-photo" alt="Susan's Avatar" />
                                                <span class="msg-body">
													<span class="msg-title">
														<span class="blue">苏珊:</span>
														《一片橘皮能够带来多少思考》 ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>20 minutes ago</span>
													</span>
												</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar4.png" class="msg-photo" alt="Bob's Avatar" />
                                                <span class="msg-body">
													<span class="msg-title">
														<span class="blue">鲍勃:</span>
														章潇月给我听的钟摆 ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>3:15 pm</span>
													</span>
												</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar2.png" class="msg-photo" alt="Kate's Avatar" />
                                                <span class="msg-body">
													<span class="msg-title">
														<span class="blue">凯特:</span>
														我觉得，springCloud微服务技术....
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>1:33 pm</span>
													</span>
												</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar5.png" class="msg-photo" alt="Fred's Avatar" />
                                                <span class="msg-body">
													<span class="msg-title">
														<span class="blue">弗雷德:</span>
														月刊家园的作者  ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>10:09 am</span>
													</span>
												</span>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="inbox.html">
                                        查看所有消息
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="light-blue dropdown-modal">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="../../public/ace/assets/images/avatars/user.jpg" alt="Jason's Photo" />
                                <span class="user-info">
									<small>{{loginUser.name}}</small>

								</span>

                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>

                            <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-cog"></i>
                                        设置
                                    </a>
                                </li>

                                <li>
                                    <a href="profile.html">
                                        <i class="ace-icon fa fa-user"></i>
                                        用户信息
                                    </a>
                                </li>

                                <li class="divider"></li>

                                <li>
                                    <a href="#" @click="loginOut">
                                        <i class="ace-icon fa fa-power-off"></i>
                                        登出
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div><!-- /.navbar-container -->
        </div>


        <!--侧边栏-->
        <div class="main-container ace-save-state" id="main-container">


            <div id="sidebar" class="sidebar      responsive        ace-save-state">


                <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                        <button class="btn btn-success">
                            <i class="ace-icon fa fa-signal"></i>
                        </button>

                        <button class="btn btn-info">
                            <i class="ace-icon fa fa-pencil"></i>
                        </button>

                        <button class="btn btn-warning">
                            <i class="ace-icon fa fa-users"></i>
                        </button>

                        <button class="btn btn-danger">
                            <i class="ace-icon fa fa-cogs"></i>
                        </button>
                    </div>

                    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                        <span class="btn btn-success"></span>

                        <span class="btn btn-info"></span>

                        <span class="btn btn-warning"></span>

                        <span class="btn btn-danger"></span>
                    </div>
                </div><!-- /.sidebar-shortcuts -->

                <ul class="nav nav-list" id="welcome-sidebar">
                    <li class="">
                        <router-link to="/welcome">
                            <i class="menu-icon fa fa-tachometer"></i>
                            <span class="menu-text"> 欢迎：{{loginUser.name}} </span>
                        </router-link>

                        <b class="arrow"></b>
                    </li>


                    <li v-show="hasResource('01')" class=" ">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text"> 系统管理 </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li v-show="hasResource('0101')" class="" id="system-userCourse-sidebar">

                                <router-link to="/system/userCourse">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    用户管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>

                            <li v-show="hasResource('0102')"  class="" id="system-resource-sidebar">
                                <router-link to="/system/resource">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    资源管理
                                </router-link>
                                <b class="arrow"></b>
                            </li>

                            <li v-show="hasResource('0103')"  class="" id="system-role-sidebar">
                                <router-link to="/system/role">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                   角色管理
                                </router-link>
                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>

                    <li v-show="hasResource('02')" class=" ">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text"> 业务管理 </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">

                            <li v-show="hasResource('0201')" class="" id="business-category-sidebar">
                                <router-link to="/business/category">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    分类管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>

                            <li v-show="hasResource('0202')" class="" id="business-course-sidebar">
                                <router-link to="/business/course">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    课程管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>

                            <li v-show="hasResource('0203')" class="" id="business-teacher-sidebar">
                                <router-link to="/business/teacher">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    讲师管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>

<!--                            <li class="active" id="business-chapter-sidebar">-->
<!--                                <router-link to="/business/chapter">-->
<!--                                    <i class="menu-icon fa fa-caret-right"></i>-->
<!--                                    大章管理-->
<!--                                </router-link>-->

<!--                                <b class="arrow"></b>-->
<!--                            </li>-->

<!--                            <li class="active" id="business-section-sidebar">-->
<!--                                <router-link to="/business/section">-->
<!--                                    <i class="menu-icon fa fa-caret-right"></i>-->
<!--                                    小节管理-->
<!--                                </router-link>-->

<!--                                <b class="arrow"></b>-->
<!--                            </li>-->
                        </ul>
                    </li>

                    <li v-show="hasResource('03')"  class="active open">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text"> 文件管理 </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">

                            <li v-show="hasResource('0301')"  class="   " id="file-file-sidebar">
                                <router-link to="/file/file">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    文件管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>


                </ul><!-- /.nav-list -->

                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>
            </div>

            <div class="main-content">
                <div class="main-content-inner">

                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                 <router-view></router-view>
                                <!-- PAGE CONTENT ENDS -->
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.page-content -->
                </div>
            </div><!-- /.main-content -->

            <div class="footer">
                <div class="footer-inner">
                    <div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">Ace</span>
							  左眼 视频在线视频 &copy; 2022-2023
						</span>

                        &nbsp; &nbsp;
                        <span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
                    </div>
                </div>
            </div>

            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div>
    </div>
</template>

<script>


    //$('body').attr('class', 'login-layout light-login');

    //$('body').attr('class', 'login-layout blur-login');
    import axios from "axios";

    export default {
        name: "admin",
        data(){
            return{
                loginUser: {},
            }
        },
        mounted() {
            $('body').removeClass('login-layout light-login');
            $('body').attr('class', 'no-skin');
            /*养成好习惯，将this变成本地变量_this,可以预防之后一直用this的坑*/

            this.activeSidebar(this.$route.name.replace("/","-")+"-sidebar");

            //关闭ace的js文件的自动刷新
            $.getScript('/ace/assets/js/ace.min.js')

            //接收传过来的用户数据
            this.loginUser =Tool.getLoginUser();
        },

        /*这个监听，只对admin下面的子路由有用，其他跳转过来的，就无效了*/
        watch:{
            $route:{
                handler:function (val,oldVal) {
                    console.log("------->页面跳转：",val,oldVal)
                    this.$nextTick(function () {//页面加载完成后执行
                        this.activeSidebar(this.$route.name.replace("/","-")+"-sidebar");
                    })

                }
            }
        },

        methods:{

            /**
             * 查找是否有权限
             * @param id
             */
            hasResource(id) {
                return Tool.hasResource(id);
            },

            login () {
                this.$router.push("/admin")
            },


            /**
             * 菜单激活样式，id是当前点击的菜单的id
             * @param id
             */
            activeSidebar: function (id) {
                // 兄弟菜单去掉active样式，自身增加active样式
                $("#" + id).siblings().removeClass("active");
                $("#" + id).siblings().find("li").removeClass("active");
                $("#" + id).addClass("active");

                // 如果有父菜单，父菜单的兄弟菜单去掉open active，父菜单增加open active
                let parentLi = $("#" + id).parents("li");
                if (parentLi) {
                    parentLi.siblings().removeClass("open active");
                    parentLi.siblings().find("li").removeClass("active");
                    parentLi.addClass("open active");
                }
            },

            loginOut(){

                Loading.show();
                axios.get(process.env.VUE_APP_SERVER + '/system/admin/userCourse/loginOut/' + this.loginUser.token).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        console.log("退出成功 ：",resp.content);
                        Tool.setLoginUser(null);
                        this.$router.push('/login')
                    } else {
                        Toast.warning(resp.message)
                    }
                })

            }

        }
    }
</script>

<style scoped>

</style>
